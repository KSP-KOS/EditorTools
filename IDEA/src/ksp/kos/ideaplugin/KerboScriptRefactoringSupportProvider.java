package ksp.kos.ideaplugin;

import com.intellij.lang.refactoring.RefactoringSupportProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;

/**
 * Created on 04/01/16.
 *
 * @author ptasha
 */
public class KerboScriptRefactoringSupportProvider extends RefactoringSupportProvider {
    @Override
    public boolean isMemberInplaceRenameAvailable(@NotNull PsiElement element, PsiElement context) {
        return false;
//        return (element instanceof PsiNameIdentifierOwner) && ((PsiNameIdentifierOwner) element).getNameIdentifier()!=null;
    }
}
