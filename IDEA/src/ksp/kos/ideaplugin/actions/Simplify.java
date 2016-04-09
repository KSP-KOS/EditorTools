package ksp.kos.ideaplugin.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.ui.Messages;
import ksp.kos.ideaplugin.expressions.Expression;
import ksp.kos.ideaplugin.expressions.SyntaxException;
import ksp.kos.ideaplugin.psi.*;

/**
 * Created on 24/01/16.
 *
 * @author ptasha
 */
public class Simplify extends BaseAction {
    @Override
    public void actionPerformed(AnActionEvent event) {
        KerboScriptExpr expr = getSimplifiable(event);
        if (expr != null) {
            WriteCommandAction.runWriteCommandAction(getEventProject(event), () -> {
                try {
                    expr.replace(simplify(expr));
                } catch (ActionFailedException e) {
                    e.printStackTrace();
                    Messages.showDialog(e.getMessage(), "Failed:", new String[]{"OK"}, -1, null);
                }
            });
        }
    }

    private KerboScriptExpr simplify(KerboScriptExpr expr) throws ActionFailedException {
        try {
            Expression parse = Expression.parse(expr);
            String simple = parse.simplify().getText();
            return KerboScriptElementFactory.expression(expr.getProject(), simple);
        } catch (SyntaxException e) {
            throw new ActionFailedException(e);
        }
    }
    @Override
    public void update(AnActionEvent event) {
        event.getPresentation().setEnabled(getSimplifiable(event) != null);
    }

    private KerboScriptExpr getSimplifiable(AnActionEvent event) {
        ExpressionHolder holder = KerboScriptElement.walkUpTill(getPsiElement(event), ExpressionHolder.class);
        if (holder == null) {
            return null;
        }
        return holder.getExpr();
    }
}
