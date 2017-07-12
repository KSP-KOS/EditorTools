package ksp.kos.ideaplugin.refactoring;

import com.intellij.lang.refactoring.InlineHandler;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created on 06/03/16.
 *
 * @author ptasha
 */
public abstract class DoNothingInlineHandler implements InlineHandler{
    @Nullable
    @Override
    public Settings prepareInlineElement(@NotNull PsiElement element, @Nullable Editor editor, boolean invokedOnReference) {
        return null;
    }

    @Override
    public void removeDefinition(@NotNull PsiElement element, @NotNull InlineHandler.Settings settings) {

    }

    @Override
    @Nullable
    public InlineHandler.Inliner createInliner(@NotNull PsiElement element, @NotNull InlineHandler.Settings settings) {
        return null;
    }
}
