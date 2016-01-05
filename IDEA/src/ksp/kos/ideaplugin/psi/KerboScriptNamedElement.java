package ksp.kos.ideaplugin.psi;

import com.intellij.psi.PsiNameIdentifierOwner;

/**
 * Created on 02/01/16.
 *
 * @author ptasha
 */
public interface KerboScriptNamedElement extends PsiNameIdentifierOwner, KerboScriptElement {
    boolean isLocal();
}
