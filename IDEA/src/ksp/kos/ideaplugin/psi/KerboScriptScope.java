package ksp.kos.ideaplugin.psi;

import ksp.kos.ideaplugin.reference.LocalScope;
import ksp.kos.ideaplugin.reference.Reference;

/**
 * Created on 07/01/16.
 *
 * @author ptasha
 */
public interface KerboScriptScope extends KerboScriptBase {
    LocalScope getCachedScope();

    default KerboScriptNamedElement resolve(Reference reference) {
        return getCachedScope().resolve(reference);
    }
}
