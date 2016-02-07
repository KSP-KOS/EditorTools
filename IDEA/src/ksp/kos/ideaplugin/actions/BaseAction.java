package ksp.kos.ideaplugin.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.annotations.Nullable;

/**
 * Created on 24/01/16.
 *
 * @author ptasha
 */
public abstract class BaseAction extends AnAction {
    @Nullable
    protected PsiElement getPsiElement(AnActionEvent event) {
        Editor editor = event.getData(CommonDataKeys.EDITOR);
        PsiFile file = event.getData(CommonDataKeys.PSI_FILE);
        if (editor != null && file != null) {
            int offset = editor.getCaretModel().getOffset();
            return file.findElementAt(offset);
        }
        return null;
    }

    protected PsiElement prevNonSpaceSibling(PsiElement element) {
        PsiElement sibling = element.getPrevSibling();
        if (sibling==null) {
            return null;
        }
        if (sibling instanceof PsiWhiteSpace) {
            return prevNonSpaceSibling(sibling);
        }
        return sibling;
    }
}
