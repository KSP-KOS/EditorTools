package ksp.kos.ideaplugin.expressions;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.impl.source.tree.LeafPsiElement;

/**
 * Created on 30/01/16.
 *
 * @author ptasha
 */
public class Constant extends Atom {
    private final String key;
    private final PsiElement psi;

    public Constant(PsiElement psi) {
        this.psi = psi;
        this.key = getKey(psi);
    }

    private static String getKey(PsiElement psi) {
        String key = "";
        if (psi instanceof LeafPsiElement) {
            if (!(psi instanceof PsiWhiteSpace)) {
                key = psi.getText();
            }
        } else {
            for (PsiElement child : psi.getChildren()) {
                key += getKey(child);
            }
        }
        return key;
    }

    @Override
    public String getText() {
        return psi.getText();
    }

    @Override
    public Expression differentiate() {
        return Number.ZERO;
    }

    @Override
    public Expression copy() {
        return new Constant(psi);
    }
}
