package ksp.kos.ideaplugin.reference.context;

import ksp.kos.ideaplugin.KerboScriptFile;

/**
 * Created on 22/10/16.
 *
 * @author ptasha
 */
public interface FileDuality extends Duality {
    KerboScriptFile getSyntax();
    FileContext getSemantics();
}
