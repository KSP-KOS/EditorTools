package ksp.kos.ideaplugin.psi;

import com.intellij.psi.tree.IElementType;
import ksp.kos.ideaplugin.KerboScriptLanguage;

/**
 * Created on 26/12/15.
 *
 * @author ptasha
 */
public class KerboScriptTokenType extends IElementType {
    public KerboScriptTokenType(String debugName) {
        super(debugName, KerboScriptLanguage.INSTANCE);
    }
}
