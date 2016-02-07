package ksp.kos.ideaplugin.psi;

import com.intellij.psi.PsiNameIdentifierOwner;
import ksp.kos.ideaplugin.reference.NamedType;

/**
 * Created on 02/01/16.
 *
 * @author ptasha
 */
public interface KerboScriptNamedElement extends PsiNameIdentifierOwner, KerboScriptBaseElement {
    NamedType getType();

    String getRawName();

    void setType(NamedType type);
    void rawRename(String name);
}
