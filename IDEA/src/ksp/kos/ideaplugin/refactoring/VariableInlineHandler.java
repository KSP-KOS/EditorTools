package ksp.kos.ideaplugin.refactoring;

import com.intellij.lang.refactoring.InlineHandler;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.refactoring.RefactoringBundle;
import com.intellij.refactoring.util.CommonRefactoringUtil;
import com.intellij.usageView.UsageInfo;
import com.intellij.util.containers.MultiMap;
import ksp.kos.ideaplugin.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created on 24/01/16.
 *
 * @author ptasha
 */
public class VariableInlineHandler implements InlineHandler {
    private static final String REFACTORING_NAME = RefactoringBundle.message("inline.title");
    private static final Settings CAN_INLINE_SETTINGS = () -> false;

    @Nullable
    @Override
    public Settings prepareInlineElement(@NotNull PsiElement element, @Nullable Editor editor, boolean invokedOnReference) {
        if (element instanceof KerboScriptDeclareIdentifierClause) {
            return CAN_INLINE_SETTINGS;
        }
        CommonRefactoringUtil.showErrorHint(element.getProject(), editor, "Cannot inline "+element, REFACTORING_NAME, null);
        return Settings.CANNOT_INLINE_SETTINGS;
    }

    @Override
    public void removeDefinition(@NotNull PsiElement element, @NotNull Settings settings) {
        PsiElement parent = element.getParent();
        if (parent instanceof KerboScriptDeclareStmt) {
            parent.delete();
        }
    }

    @Nullable
    @Override
    public Inliner createInliner(@NotNull PsiElement element, @NotNull Settings settings) {
        return new Inliner() {
            @Nullable
            @Override
            public MultiMap<PsiElement, String> getConflicts(@NotNull PsiReference reference, @NotNull PsiElement referenced) {
                return null;
            }

            @Override
            public void inlineUsage(@NotNull UsageInfo usage, @NotNull PsiElement referenced) {
                if (referenced instanceof KerboScriptDeclareIdentifierClause) {
                    KerboScriptExpr expr = ((KerboScriptDeclareIdentifierClause) referenced).getExpr();
                    PsiElement element = usage.getElement();
                    if (element!=null) {
                        PsiElement parent = element.getParent();
                        if (parent instanceof KerboScriptAtom) {
                            parent.getParent().replace(KerboScriptElementFactory.expression(expr.getProject(), "("+expr.getText()+")"));
                        }
                    }
                }
            }
        };
    }
}
