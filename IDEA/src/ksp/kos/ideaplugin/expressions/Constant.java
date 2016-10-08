package ksp.kos.ideaplugin.expressions;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import ksp.kos.ideaplugin.dataflow.ReferenceFlow;
import ksp.kos.ideaplugin.psi.*;
import ksp.kos.ideaplugin.reference.Context;

import java.util.ArrayList;
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
    private final Collection<Expression> expressions = new ArrayList<>();
    private final PsiElement psi;

    public Constant(KerboScriptSuffix psi) throws SyntaxException {
        this.psi = psi;
        this.key = getKey(psi);
        expressions.add(Expression.parse(psi.getSuffixterm()));
        for (KerboScriptSuffixTrailer trailer : psi.getSuffixTrailerList()) {
            parseSuffixtermTrailer(trailer.getSuffixterm());
        }
    }

    public Constant(KerboScriptSuffixterm psi) throws SyntaxException {
        this.psi = psi;
        this.key = getKey(psi);
        expressions.add(Atom.parse(psi.getAtom()));
        parseSuffixtermTrailer(psi);
    }

    private void parseSuffixtermTrailer(KerboScriptSuffixterm psi) throws SyntaxException {
        for (KerboScriptSuffixtermTrailer trailer : psi.getSuffixtermTrailerList()) {
            if (trailer instanceof KerboScriptFunctionTrailer) {
                KerboScriptArglist arglist = ((KerboScriptFunctionTrailer) trailer).getArglist();
                if (arglist!=null) {
                    for (KerboScriptExpr expr : arglist.getExprList()) {
                        expressions.add(Expression.parse(expr));
                    }
                }
            } else if (trailer instanceof KerboScriptArrayTrailer) {
                expressions.add(Expression.parse(((KerboScriptArrayTrailer) trailer).getExpr()));
            }
        }
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
    public Expression differentiate(Context<ReferenceFlow> context) {
        return Number.ZERO;
    }

    @Override
    public Expression inline(HashMap<String, Expression> args) {
        return this;
    }

    @Override
    public void acceptChildren(ExpressionVisitor visitor) {
        for (Expression expression : expressions) {
            expression.accept(visitor);
        }
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
