package ksp.kos.ideaplugin.actions.differentiate;

import com.intellij.openapi.project.Project;
import ksp.kos.ideaplugin.actions.ActionFailedException;
import ksp.kos.ideaplugin.expressions.Expression;
import ksp.kos.ideaplugin.expressions.SyntaxException;
import ksp.kos.ideaplugin.psi.KerboScriptElementFactory;
import ksp.kos.ideaplugin.psi.KerboScriptExpr;
import ksp.kos.ideaplugin.psi.KerboScriptInstruction;
import ksp.kos.ideaplugin.psi.KerboScriptReturnStmt;

/**
 * Created on 27/03/16.
 *
 * @author ptasha
 */
public class ReturnDiffer implements Differ {
    @Override
    public boolean canDo(KerboScriptInstruction instruction) {
        return instruction instanceof KerboScriptReturnStmt;
    }

    @Override
    public void doIt(Project project, KerboScriptInstruction instruction) throws ActionFailedException {
        if (instruction instanceof KerboScriptReturnStmt) {
            KerboScriptReturnStmt returnStmt = (KerboScriptReturnStmt) instruction;
            KerboScriptExpr expr = returnStmt.getExpr();
            expr.replace(KerboScriptElementFactory.expression(project, diff(expr)));
        }
    }

    protected String diff(KerboScriptExpr expr) throws ActionFailedException {
        try {
            return Expression.parse(expr).differentiate(expr.getScope().getCachedScope()).getText();
        } catch (SyntaxException e) {
            throw new ActionFailedException(e);
        }
    }
}
