package ksp.kos.ideaplugin.refactoring;

import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.refactoring.util.CommonRefactoringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created on 06/03/16.
 *
 * @author ptasha
 */
public class VirtualCheckInlineHandler extends DoNothingInlineHandler {
    @Nullable
    @Override
    public Settings prepareInlineElement(@NotNull PsiElement element, @Nullable Editor editor, boolean invokedOnReference) {
        if (element.getContainingFile().getVirtualFile()==null) {
            CommonRefactoringUtil.showErrorHint(element.getProject(), editor, "Cannot inline: definition is not found", InlineSettings.REFACTORING_NAME, null);
            return Settings.CANNOT_INLINE_SETTINGS;
        }
        return null;
    }
}
