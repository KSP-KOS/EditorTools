package ksp.kos.ideaplugin.psi;

import com.intellij.psi.PsiElement;

/**
 * Created on 06/02/16.
 *
 * @author ptasha
 */
public interface ExpressionHolder extends PsiElement {
    KerboScriptExpr getExpr();
}
