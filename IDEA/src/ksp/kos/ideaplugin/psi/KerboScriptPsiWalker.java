package ksp.kos.ideaplugin.psi;

import com.intellij.psi.PsiElement;
import ksp.kos.ideaplugin.reference.NamedType;
import ksp.kos.ideaplugin.reference.ReferenceType;
import ksp.kos.ideaplugin.reference.SuffixtermType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created on 07/01/16.
 *
 * @author ptasha
 */
public class KerboScriptPsiWalker extends KerboScriptVisitor {
    @Override
    public void visitScope(@NotNull KerboScriptScope o) {
        o.clearCache();
        super.visitScope(o);
    }

    @Override
    public void visitRunStmt(@NotNull KerboScriptRunStmt o) {
        o.setType(new NamedType(SuffixtermType.FILE, ReferenceType.REFERENCE));
        super.visitRunStmt(o);
    }

    @Override
    public void visitDeclareFunctionClause(@NotNull KerboScriptDeclareFunctionClause o) {
        o.setType(new NamedType(SuffixtermType.FUNCTION, ReferenceType.LOCAL));
        super.visitDeclareFunctionClause(o);
    }

    @Override
    public void visitDeclareIdentifierClause(@NotNull KerboScriptDeclareIdentifierClause o) {
        o.setType(new NamedType(SuffixtermType.VARIABLE, ReferenceType.LOCAL));
        super.visitDeclareIdentifierClause(o);
    }

    @Override
    public void visitDeclareParameterClause(@NotNull KerboScriptDeclareParameterClause o) {
        o.setType(new NamedType(SuffixtermType.VARIABLE, ReferenceType.LOCAL));
        super.visitDeclareParameterClause(o);
    }

    @Override
    public void visitAtom(@NotNull KerboScriptAtom o) {
        if (o.getNode().findChildByType(KerboScriptTypes.IDENTIFIER) == null) {
            o.setType(new NamedType(SuffixtermType.OTHER, ReferenceType.NONE));
        } else {
            PsiElement parent = o.getParent();
            if (parent instanceof KerboScriptSuffixterm) { // function, array, method
                if (parent.getParent() instanceof KerboScriptSuffixTrailer) { // method or array-field
                    o.setType(new NamedType(
                            isFunction((KerboScriptSuffixterm) parent) ? SuffixtermType.METHOD : SuffixtermType.FIELD,
                            ReferenceType.REFERENCE));
                } else { // function or array
                    o.setType(new NamedType(
                            isFunction((KerboScriptSuffixterm) parent) ? SuffixtermType.FUNCTION : SuffixtermType.VARIABLE,
                            ReferenceType.REFERENCE));
                }
            } else { // variable or field
                if (parent instanceof KerboScriptSuffixTrailer) { // field
                    o.setType(new NamedType(SuffixtermType.FIELD, ReferenceType.REFERENCE));
                } else { // variable
                    o.setType(new NamedType(SuffixtermType.VARIABLE, ReferenceType.REFERENCE));
                }
            }
        }
        super.visitAtom(o);
    }

    private boolean isFunction(KerboScriptSuffixterm suffixterm) {
        List<KerboScriptSuffixtermTrailer> list = suffixterm.getSuffixtermTrailerList();
        if (list.isEmpty()) { // impossible, but it must be variable
            return false;
        } else {
            KerboScriptSuffixtermTrailer first = list.get(0);
            if (first instanceof KerboScriptFunctionTrailer) { // function
                return true;
            } else { // array
                return false;
            }
        }
    }

    @Override
    public void visitElement(PsiElement element) {
        super.visitElement(element);
        element.acceptChildren(this);
    }
}
