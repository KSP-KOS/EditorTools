package ksp.kos.ideaplugin.psi

import com.intellij.psi.PsiElement
import ksp.kos.ideaplugin.reference.OccurrenceType
import ksp.kos.ideaplugin.reference.ReferableType
import ksp.kos.ideaplugin.reference.ReferenceType

/**
 * Created on 07/01/16.
 *
 * @author ptasha
 */
class KerboScriptPsiWalker : KerboScriptVisitor() {
    override fun visitScope(o: KerboScriptScope) {
        o.cachedScope.clear()
        super.visitScope(o)
    }

    override fun visitRunStmt(o: KerboScriptRunStmt) {
        o.type = ReferenceType(ReferableType.FILE, OccurrenceType.REFERENCE)
        super.visitRunStmt(o)
    }

    override fun visitDeclareFunctionClause(o: KerboScriptDeclareFunctionClause) {
        o.type = ReferenceType(ReferableType.FUNCTION, OccurrenceType.LOCAL)
        super.visitDeclareFunctionClause(o)
    }

    override fun visitDeclareIdentifierClause(o: KerboScriptDeclareIdentifierClause) {
        o.type = ReferenceType(ReferableType.VARIABLE, OccurrenceType.LOCAL)
        super.visitDeclareIdentifierClause(o)
    }

    override fun visitDeclareParameterClause(o: KerboScriptDeclareParameterClause) {
        o.type = ReferenceType(ReferableType.VARIABLE, OccurrenceType.LOCAL)
        super.visitDeclareParameterClause(o)
    }

    override fun visitAtom(o: KerboScriptAtom) {
        if (o.node.findChildByType(KerboScriptTypes.IDENTIFIER) == null) {
            o.type = ReferenceType(ReferableType.OTHER, OccurrenceType.NONE)
        } else {
            val parent = o.parent
            // Series of nested if's to determine the referable type. It's always a reference (hard coded below).
            val referableType = if (parent is KerboScriptSuffixterm) { // function, array, method
                if (parent.getParent() is KerboScriptSuffixTrailer) { // method or array-field
                    if (isFunction(parent)) ReferableType.METHOD else ReferableType.FIELD
                } else { // function or array
                    if (isFunction(parent)) ReferableType.FUNCTION else ReferableType.VARIABLE
                }
            } else { // variable or field
                if (parent is KerboScriptSuffixTrailer) { // field
                    ReferableType.FIELD
                } else { // variable
                    ReferableType.VARIABLE
                }
            }

            o.type = ReferenceType(referableType, OccurrenceType.REFERENCE)
        }
        super.visitAtom(o)
    }

    private fun isFunction(suffixterm: KerboScriptSuffixterm): Boolean =
        suffixterm.suffixtermTrailerList.firstOrNull() is KerboScriptFunctionTrailer

    override fun visitElement(element: PsiElement) {
        super.visitElement(element)
        element.acceptChildren(this)
    }
}
