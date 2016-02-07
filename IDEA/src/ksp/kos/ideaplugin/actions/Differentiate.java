package ksp.kos.ideaplugin.actions;

import com.intellij.lang.ASTFactory;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiElement;
import ksp.kos.ideaplugin.expressions.Expression;
import ksp.kos.ideaplugin.expressions.SyntaxException;
import ksp.kos.ideaplugin.psi.*;

/**
 * Created on 19/01/16.
 *
 * @author ptasha
 */
public class Differentiate extends BaseAction {
    @Override
    public void actionPerformed(AnActionEvent event) {
        KerboScriptExpr expr = getDifferentiable(event);
        if (expr != null) {
            WriteCommandAction.runWriteCommandAction(getEventProject(event), () -> {
                try {
                    KerboScriptInstruction parent = expr.walkUpTill(KerboScriptInstruction.class);
                    if (!(parent instanceof KerboScriptReturnStmt)) {
                        PsiElement copy = parent.copy();
                        copy.getNode().addChild(ASTFactory.whitespace("\n"));
                        parent.getParent().addBefore(copy, parent);
                        if (parent instanceof KerboScriptDeclareStmt) {
                            KerboScriptDeclareIdentifierClause identifier = ((KerboScriptDeclareStmt) parent).getDeclareIdentifierClause();
                            if (identifier!=null) {
                                identifier.rawRename(identifier.getRawName()+"_");
                            }
                        }
                    }
                    expr.replace(KerboScriptElementFactory.expression(getEventProject(event), diff(expr)));
                } catch (ActionFailedException e) {
                    Messages.showDialog(e.getMessage(), "Failed:", new String[]{"OK"}, -1, null);
                }
            });
        }
    }

    private String diff(KerboScriptExpr expr) throws ActionFailedException {
        try {
            return Expression.parse(expr).differentiate().getText();
        } catch (SyntaxException e) {
            throw new ActionFailedException(e);
        }
    }

    @Override
    public void update(AnActionEvent event) {
        event.getPresentation().setEnabled(getDifferentiable(event) != null);
    }

    private KerboScriptExpr getDifferentiable(AnActionEvent event) {
        ExpressionHolder holder = KerboScriptBaseElement.walkUpTill(getPsiElement(event), ExpressionHolder.class);
        if (holder == null) {
            return null;
        }
        return holder.getExpr();
    }
}
