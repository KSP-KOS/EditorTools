package ksp.kos.ideaplugin.refactoring;

import com.intellij.codeInsight.TargetElementUtil;
import com.intellij.lang.refactoring.InlineHandler;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.refactoring.util.CommonRefactoringUtil;
import com.intellij.usageView.UsageInfo;
import com.intellij.util.containers.MultiMap;
import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created on 06/03/16.
 *
 * @author ptasha
 */
public class FunctionInlineHandler implements InlineHandler {
    @Nullable
    @Override
    public Settings prepareInlineElement(@NotNull PsiElement element, @Nullable Editor editor, boolean invokedOnReference) {
        final PsiReference invocationReference = editor != null ? TargetElementUtil.findReference(editor) : null;
        if (element instanceof KerboScriptDeclareFunctionClause) {
            FunctionSettings settings = new FunctionSettings(invocationReference!=null && invocationReference.getElement()!=element);
            List<KerboScriptInstruction> instructions = ((KerboScriptDeclareFunctionClause) element).getInstructionBlock().getInstructionList();
            for (KerboScriptInstruction instruction : instructions) {
                if (instruction instanceof KerboScriptDeclareStmt) {
                    KerboScriptDeclareStmt declareStmt = (KerboScriptDeclareStmt) instruction;
                    KerboScriptDeclareParameterClause declareParameter = declareStmt.getDeclareParameterClause();
                    if (declareParameter == null) {
                        return toHardToInline(element, editor);
                    }
                    Collection<PsiReference> references = ReferencesSearch.search(declareParameter).findAll();
                    ArrayList<Integer> usageIndexes = new ArrayList<>();
                    for (PsiReference reference : references) {
                        usageIndexes.add(reference.getElement().getTextOffset());
                    }
                    settings.paramUsages.add(usageIndexes);
                } else if (instruction instanceof KerboScriptReturnStmt) {
                    settings.returnStmt = (KerboScriptReturnStmt) instruction;
                } else {
                    return toHardToInline(element, editor);
                }
            }
            if (settings.returnStmt == null) {
                CommonRefactoringUtil.showErrorHint(element.getProject(), editor, "Cannot inline: return statement is not found", InlineSettings.REFACTORING_NAME, null);
                return Settings.CANNOT_INLINE_SETTINGS;
            }
            int offset = settings.returnStmt.getTextOffset();
            for (ArrayList<Integer> paramIndexes : settings.paramUsages) {
                for (int i = 0; i < paramIndexes.size(); i++) {
                    paramIndexes.set(i, paramIndexes.get(i) - offset);
                }
            }
            return settings;
        }
        return null;
    }

    private Settings toHardToInline(PsiElement element, Editor editor) {
        CommonRefactoringUtil.showErrorHint(element.getProject(), editor, "Cannot inline: only simple functions are supported", InlineSettings.REFACTORING_NAME, null);
        return Settings.CANNOT_INLINE_SETTINGS;
    }

    @Override
    public void removeDefinition(@NotNull PsiElement element, @NotNull Settings settings) {
        element.delete();
    }

    @Nullable
    @Override
    public Inliner createInliner(@NotNull PsiElement element, @NotNull Settings settings) {
        if (settings instanceof FunctionSettings) {
            FunctionSettings functionSettings = (FunctionSettings) settings;
            return new Inliner() {
                @Nullable
                @Override
                public MultiMap<PsiElement, String> getConflicts(@NotNull PsiReference reference, @NotNull PsiElement referenced) {
                    return null;
                }

                @Override
                public void inlineUsage(@NotNull UsageInfo usage, @NotNull PsiElement referenced) {
                    KerboScriptFile file = KerboScriptElementFactory.file(referenced.getProject(), functionSettings.returnStmt.getText());
                    ArrayList<ArrayList<PsiElement>> paramsUsages = new ArrayList<>();
                    for (ArrayList<Integer> paramIndexes : functionSettings.paramUsages) {
                        ArrayList<PsiElement> paramUsages = new ArrayList<>();
                        for (Integer index : paramIndexes) {
                            if (index > 0) {
                                PsiElement paramUsage = PsiTreeUtil.getParentOfType(file.findElementAt(index), KerboScriptExpr.class, false);
                                if (paramUsage != null) {
                                    paramUsages.add(paramUsage);
                                }
                            }
                        }
                        paramsUsages.add(paramUsages);
                    }

                    KerboScriptSuffixterm functionCall = PsiTreeUtil.getParentOfType(usage.getElement(), KerboScriptSuffixterm.class, false);
                    if (functionCall != null) {
                        List<KerboScriptSuffixtermTrailer> trailers = functionCall.getSuffixtermTrailerList();
                        if (trailers.size() > 0) {
                            KerboScriptSuffixtermTrailer trailer = trailers.get(0);
                            if (trailer instanceof KerboScriptFunctionTrailer) {
                                KerboScriptArglist arglist = ((KerboScriptFunctionTrailer) trailer).getArglist();
                                if (arglist != null) {
                                    List<KerboScriptExpr> arguments = arglist.getExprList();
                                    for (int i = 0; i < Math.min(arguments.size(), paramsUsages.size()); i++) {
                                        KerboScriptExpr argument = arguments.get(i);
                                        ArrayList<PsiElement> paramUsages = paramsUsages.get(i);
                                        for (PsiElement paramUsage : paramUsages) {
                                            paramUsage.replace(KerboScriptElementFactory.expression(functionCall.getProject(), "(" + argument.getText() + ")"));
                                        }
                                    }
                                }
                            }
                        }
                        KerboScriptReturnStmt returnStmt = (KerboScriptReturnStmt) file.getFirstChild();
                        KerboScriptExpr expression = returnStmt.getExpr();
                        if (expression != null) {
                            functionCall.replace(KerboScriptElementFactory.expression(functionCall.getProject(), "(" + expression.getText() + ")"));
                        }
                    }
                }
            };
        }
        return null;
    }

    private static class FunctionSettings extends InlineSettings {
        private final ArrayList<ArrayList<Integer>> paramUsages = new ArrayList<>();
        private KerboScriptReturnStmt returnStmt;

        public FunctionSettings(boolean onlyReference) {
            super(onlyReference);
        }
    }
}
