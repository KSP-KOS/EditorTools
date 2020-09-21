package ksp.kos.ideaplugin.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.util.parentOfType
import ksp.kos.ideaplugin.KerboScriptFile
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
        // Defaults to global at top-level (if not inside a scope), anywhere else defaults to local
        val isTopLevel = o.parentOfType<KerboScriptScope>() is KerboScriptFile
        o.type = ReferenceType(ReferableType.FUNCTION, calculateOccurrenceType(o, defaultLocal = !isTopLevel))
        super.visitDeclareFunctionClause(o)
    }

    override fun visitDeclareIdentifierClause(o: KerboScriptDeclareIdentifierClause) {
        o.type = ReferenceType(ReferableType.VARIABLE, calculateOccurrenceType(o, defaultLocal = true))
        super.visitDeclareIdentifierClause(o)
    }

    override fun visitDeclareParameterClause(o: KerboScriptDeclareParameterClause) {
        o.type = ReferenceType(ReferableType.VARIABLE, OccurrenceType.LOCAL) // Must be local
        super.visitDeclareParameterClause(o)
    }

    override fun visitDeclareLockClause(o: KerboScriptDeclareLockClause) {
        o.type = ReferenceType(ReferableType.VARIABLE, calculateOccurrenceType(o, defaultLocal = false))
        super.visitDeclareLockClause(o)
    }

    override fun visitDeclareForClause(o: KerboScriptDeclareForClause) {
        o.type = ReferenceType(ReferableType.VARIABLE, OccurrenceType.LOCAL) // Must be local
        super.visitDeclareForClause(o)
    }

    private fun calculateOccurrenceType(o: KerboScriptNamedElement, defaultLocal: Boolean): OccurrenceType {
        val parentDeclare = o.parent as KerboScriptDeclareStmt
        val isLocal = if (defaultLocal) {
            // As long as not explicitly global (eg global is null) then we're local
            parentDeclare.node.findChildByType(KerboScriptTypes.GLOBAL) == null
        } else {
            // Only local if explicitly so (eg local is not null)
            parentDeclare.node.findChildByType(KerboScriptTypes.LOCAL) != null
        }

        return if (isLocal) OccurrenceType.LOCAL else OccurrenceType.GLOBAL
    }

    override fun visitAtom(o: KerboScriptAtom) {
        if (o.name == null) {
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

    override fun visitFromloopStmt(o: KerboScriptFromloopStmt) {
        super.visitFromloopStmt(o)
        // Once children have their types set, go ahead and copy locals into the parent scope (eg this from loop)
        // so the blocks can see declarations in earlier blocks.
        o.instructionBlockList
            .flatMap { instructionBlock -> instructionBlock.cachedScope.getDeclarations(ReferableType.VARIABLE).values }
            .forEach { variableDuality -> o.cachedScope.register(variableDuality) }
    }

    override fun visitElement(element: PsiElement) {
        super.visitElement(element)
        element.acceptChildren(this)
    }
}
