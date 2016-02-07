package ksp.kos.ideaplugin.psi;

import com.intellij.psi.PsiElement;

/**
 * Created on 07/01/16.
 *
 * @author ptasha
 */
public interface KerboScriptScope extends KerboScriptBaseElement {
    void register(KerboScriptNamedElement element);

    PsiElement resolveFunction(KerboScriptNamedElement element);

    PsiElement resolveVariable(KerboScriptNamedElement element);

    void clearCache();
}
