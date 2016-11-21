package ksp.kos.ideaplugin.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.util.PsiTreeUtil;
import ksp.kos.ideaplugin.expressions.Expression;
import ksp.kos.ideaplugin.expressions.SyntaxException;
import ksp.kos.ideaplugin.psi.ExpressionHolder;
import ksp.kos.ideaplugin.psi.KerboScriptElementFactory;
import ksp.kos.ideaplugin.psi.KerboScriptExpr;

/**
 * Created on 13/11/16.
 *
 * @author ptasha
 */
public class Normalize extends BaseAction {
    @Override
    public void actionPerformed(AnActionEvent event) {
        KerboScriptExpr expr = getSimplifiable(event);
        if (expr != null) {
            WriteCommandAction.runWriteCommandAction(getEventProject(event), () -> {
                try {
                    expr.replace(normalize(expr));
                } catch (ActionFailedException e) {
                    e.printStackTrace();
                    Messages.showDialog(e.getMessage(), "Failed:", new String[]{"OK"}, -1, null);
                }
            });
        }
    }

    private KerboScriptExpr normalize(KerboScriptExpr expr) throws ActionFailedException {
        try {
            Expression parse = Expression.parse(expr);
            String simple = parse.normalize().simplify().getText();
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
        ExpressionHolder holder = PsiTreeUtil.getParentOfType(getPsiElement(event), ExpressionHolder.class, false);
        if (holder == null) {
            return null;
        }
        return holder.getExpr();
    }
}
