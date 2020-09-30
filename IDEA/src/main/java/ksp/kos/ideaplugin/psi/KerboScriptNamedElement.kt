package ksp.kos.ideaplugin.psi

import com.intellij.openapi.util.Key
import com.intellij.psi.util.CachedValue
import ksp.kos.ideaplugin.dataflow.FlowParser
import ksp.kos.ideaplugin.dataflow.ReferenceFlow
import ksp.kos.ideaplugin.reference.PsiSelfResolvable
import ksp.kos.ideaplugin.reference.ReferableType
import ksp.kos.ideaplugin.reference.Reference
import ksp.kos.ideaplugin.reference.ReferenceType
import ksp.kos.ideaplugin.reference.context.LocalContext
import com.intellij.psi.PsiElement
import ksp.kos.ideaplugin.reference.*
import ksp.kos.ideaplugin.reference.context.PsiDuality
import java.util.function.Supplier

/**
 * Created on 02/01/16.
 *
 * @author ptasha
 */
interface KerboScriptNamedElement : KerboScriptBase, PsiSelfResolvable {
    var type: ReferenceType

    // TODO duality can be pure virtual
    @JvmDefault
    val cachedFlow: ReferenceFlow<*>
        get() {
            var cached = getUserData(FLOW_KEY)
            if (cached == null) {
                cached = createCachedValue(Supplier { FlowParser.INSTANCE.apply(this) })
                putUserData(FLOW_KEY, cached)
            }
            return cached.value
        }

    @JvmDefault
    override fun getKingdom(): LocalContext = scope.cachedScope

    @JvmDefault
    override fun getReferableType(): ReferableType = type.type

    @JvmDefault
    val isDeclaration: Boolean
        get() = type.occurrenceType.isDeclaration

    @JvmDefault
    override fun resolve(): KerboScriptNamedElement = if (isDeclaration) this else super.resolve()

    @JvmDefault
    override fun matches(declaration: Reference): Boolean {
        val declarationElement = when (declaration) {
            is PsiDuality -> declaration.syntax
            is KerboScriptNamedElement -> declaration
            else -> null
        }
        return (
                // If we're not even dealing with another named element, just kinda ignore it, call it good
                declarationElement !is KerboScriptNamedElement
                        // Otherwise verify it's either global, or a local that's used after the declaration
                        || isDeclarationVisibleToUsage(this, declarationElement)
                )
    }

    companion object {
        val FLOW_KEY = Key<CachedValue<ReferenceFlow<*>>>("ksp.kos.ideaplugin.semantics")
    }
}

private fun isDeclarationVisibleToUsage(usage: PsiElement, declaration: KerboScriptNamedElement?): Boolean {
    if (declaration?.type?.occurrenceType != OccurrenceType.LOCAL) {
        // Consider globals to always be visible
        return true
    }

    // If the declaration is in a generated file (always named generated.ks, see KerboScriptElementFactory) but the
    // usage is in another file, assume that the declaration is not visible.
    if (declaration.containingFile.name == "generated.ks" && usage.containingFile.name != "generated.ks") return false

    // If usage is inside a function declaration (relative to the common ancestor) then all bets on ordering are
    // off, and we'll let this fly.
    val hasFunctionBetweenUsageAndDeclaration =
        doesTypeExistBetweenCurrentAndClosestCommonAncestor<KerboScriptDeclareFunctionClause>(
            current = usage,
            other = declaration
        )
    if (hasFunctionBetweenUsageAndDeclaration) {
        return true
    }

    // Otherwise, make sure the declaration is before the usage.
    return declaration.textOffset <= usage.textOffset
}

/**
 * Finds the common ancestor of the two elements, then returns whether an element of the given type exists between
 * the "current" node and that common ancestor.
 */
private inline fun <reified T : PsiElement> doesTypeExistBetweenCurrentAndClosestCommonAncestor(
    current: PsiElement,
    other: PsiElement,
): Boolean {
    val currentAncestors = getAncestors(current)
    val otherAncestors = getAncestors(other)
    val closestCommonAncestorIndex =
        otherAncestors.zip(currentAncestors).indexOfLast { (a, b) -> a == b }

    if (closestCommonAncestorIndex == -1) {
        throw IllegalStateException(
            "Unable to find common ancestor: " +
                    " current=$current@${current.containingFile.name}:${current.textRange} and " +
                    " other=$other@${other.containingFile.name}:${other.textRange}"
        )
    }

    val closestTypeIndex = currentAncestors.indexOfLast { it is T }
    return closestCommonAncestorIndex < closestTypeIndex
}

/**
 * Returns all ancestors of the element, with the oldest ancestor first and the element itself last.
 */
private fun getAncestors(element: PsiElement): List<PsiElement> {
    val ancestors = mutableListOf(element)
    var next = element.parent
    while (next != null) {
        ancestors.add(next)
        next = next.parent
    }
    return ancestors.reversed()
}
