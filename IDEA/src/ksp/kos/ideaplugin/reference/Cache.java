package ksp.kos.ideaplugin.reference;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import ksp.kos.ideaplugin.KerboScriptFile;

/**
 * Created on 08/01/16.
 *
 * @author ptasha
 */
public class Cache<T> {
    private final PsiElement element;
    private final T scope;

    public Cache(PsiElement element, T scope) {
        this.element = element;
        this.scope = scope;
    }

    public T getScope() {
        PsiFile file = element.getContainingFile();
        if (file instanceof KerboScriptFile) {
            ((KerboScriptFile) file).checkVersion();
        }
        return scope;
    }
}
