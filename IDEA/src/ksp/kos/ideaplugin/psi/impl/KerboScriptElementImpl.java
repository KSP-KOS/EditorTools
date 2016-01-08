package ksp.kos.ideaplugin.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.psi.*;
import org.jetbrains.annotations.NotNull;

/**
 * Created on 02/01/16.
 *
 * @author ptasha
 */
public class KerboScriptElementImpl extends ASTWrapperPsiElement implements KerboScriptElement {
    public KerboScriptElementImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public KerboScriptScope getScope() {
        PsiElement parent = getParent();
        while (!(parent instanceof KerboScriptScope)) {
            parent = parent.getParent();
        }
        return (KerboScriptScope) parent;
    }

    @Override
    public KerboScriptFile getContainingFile() {
        return (KerboScriptFile) super.getContainingFile();
    }
}
