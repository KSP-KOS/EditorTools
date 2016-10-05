package ksp.kos.ideaplugin.psi;

import ksp.kos.ideaplugin.reference.LocalContext;
import ksp.kos.ideaplugin.reference.Reference;

/**
 * Created on 07/01/16.
 *
 * @author ptasha
 */
public interface KerboScriptScope extends KerboScriptBase {
    LocalContext getCachedScope();

    default KerboScriptNamedElement resolve(Reference reference) {
        return getCachedScope().resolve(reference);
    }
}
