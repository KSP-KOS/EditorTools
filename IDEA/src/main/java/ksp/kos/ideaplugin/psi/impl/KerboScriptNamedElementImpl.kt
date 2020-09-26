package ksp.kos.ideaplugin.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.PsiNamedElement
import ksp.kos.ideaplugin.psi.*
import ksp.kos.ideaplugin.reference.*
import org.jetbrains.annotations.NonNls

/**
 * Created on 07/01/16.
 *
 * @author ptasha
 */
open class KerboScriptNamedElementImpl(node: ASTNode) : ASTWrapperPsiElement(node),
    KerboScriptNamedElement,
    PsiNameIdentifierOwner {

    private val cache = Cache(this, NamedScope())

    override fun getNameIdentifier(): PsiElement? =
        children.firstOrNull { it is KerboScriptIdent || it is KerboScriptFileIdent }

    override var type: ReferenceType
        get() = cache.scope.type
        set(type) {
            cache.scope.type = type
            cache.scope.reference = createReference(type)
            register()
        }

    override fun setName(@NonNls name: String): PsiNamedElement {
        val nameIdentifier = nameIdentifier
        if (nameIdentifier != null) {
            this.node.replaceChild(
                nameIdentifier.node,
                KerboScriptElementFactory.leaf(KerboScriptTypes.IDENTIFIER, name)
            )
        }
        return this
    }

    override fun getName(): String? = nameIdentifier?.text

    private fun register() {
        val type = cache.scope.type
        if (type.occurrenceType.isDeclaration || type.type == ReferableType.FILE) {
            scope.cachedScope.register(this)
        }
    }

    private fun createReference(type: ReferenceType): KerboScriptReference? =
        when (type.type) {
            ReferableType.FILE -> KerboScriptFileReference(this)
            ReferableType.FUNCTION, ReferableType.VARIABLE -> KerboScriptReference(this)
            else -> null
        }

    override fun getReference(): KerboScriptReference? = cache.scope.reference

    override fun getNavigationElement(): PsiElement = nameIdentifier ?: super.getNavigationElement()

    private inner class NamedScope {
        var type = ReferenceType(ReferableType.OTHER, OccurrenceType.NONE)
        var reference: KerboScriptReference? = null
    }
}
