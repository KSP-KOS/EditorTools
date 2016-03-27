package ksp.kos.ideaplugin.actions.differentiate;

import com.intellij.psi.PsiElement;
import ksp.kos.ideaplugin.dataflow.Flow;
import ksp.kos.ideaplugin.dataflow.FunctionFlow;
import ksp.kos.ideaplugin.expressions.SyntaxException;
import ksp.kos.ideaplugin.psi.KerboScriptDeclareStmt;
import ksp.kos.ideaplugin.psi.KerboScriptInstruction;

/**
 * Created on 27/03/16.
 *
 * @author ptasha
 */
public class FunctionDiffer extends DuplicateDiffer<KerboScriptDeclareStmt> {
    public FunctionDiffer() {
        super(KerboScriptDeclareStmt.class);
    }
    @Override
    public boolean canDo(KerboScriptInstruction instruction) {
        return super.canDo(instruction) && ((KerboScriptDeclareStmt)instruction).getDeclareFunctionClause()!=null;
    }

    @Override
    protected void separator(PsiElement copy) {
        newLine(copy);
        newLine(copy);
    }

    @Override
    protected Flow parse(KerboScriptDeclareStmt variable) throws SyntaxException {
        return FunctionFlow.parse(variable.getDeclareFunctionClause());
    }
}
