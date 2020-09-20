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
        // If it's not even a named element or is one from a different kingdom, just go ahead and say it's good
        return declaration !is KerboScriptNamedElement || declaration.kingdom !== this.kingdom
                // If it *is* a named element from this kingdom, make sure the declaration is before the use...
                || declaration.textOffset <= this.textOffset
    }

    companion object {
        val FLOW_KEY = Key<CachedValue<ReferenceFlow<*>>>("ksp.kos.ideaplugin.semantics")
    }
}
