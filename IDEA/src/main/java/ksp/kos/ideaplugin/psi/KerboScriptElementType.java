package ksp.kos.ideaplugin.psi;

import com.intellij.psi.tree.IElementType;
import ksp.kos.ideaplugin.KerboScriptLanguage;

/**
 * Created on 26/12/15.
 *
 * @author ptasha
 */
public class KerboScriptElementType extends IElementType {
    public KerboScriptElementType(String debugName) {
        super(debugName, KerboScriptLanguage.INSTANCE);
    }
}
