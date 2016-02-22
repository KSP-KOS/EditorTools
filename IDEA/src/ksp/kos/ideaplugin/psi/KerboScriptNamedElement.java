package ksp.kos.ideaplugin.psi;

import com.intellij.psi.PsiNameIdentifierOwner;
import ksp.kos.ideaplugin.reference.NamedType;

/**
 * Created on 02/01/16.
 *
 * @author ptasha
 */
public interface KerboScriptNamedElement extends PsiNameIdentifierOwner, KerboScriptElement {
    NamedType getType();

    void setType(NamedType type);
}
