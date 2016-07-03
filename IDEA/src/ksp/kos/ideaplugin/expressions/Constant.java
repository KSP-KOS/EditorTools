package ksp.kos.ideaplugin.expressions;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

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
        Collection<LeafPsiElement> children = PsiTreeUtil.findChildrenOfType(psi, LeafPsiElement.class);
        for (LeafPsiElement child : children) {
            if (!(child instanceof PsiWhiteSpace)) {
                key += child.getText();
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
    public Expression inline(HashMap<String, Expression> args) {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Constant constant = (Constant) o;
        return Objects.equals(key, constant.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
