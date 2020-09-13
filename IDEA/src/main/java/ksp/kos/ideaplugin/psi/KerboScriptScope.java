package ksp.kos.ideaplugin.psi;

import ksp.kos.ideaplugin.reference.context.LocalContext;
import ksp.kos.ideaplugin.reference.Reference;

/**
 * Created on 07/01/16.
 *
 * @author ptasha
 */
public interface KerboScriptScope extends KerboScriptBase { // TODO remove me
    LocalContext getCachedScope();

    default KerboScriptNamedElement resolve(Reference reference) {
        return getCachedScope().resolve(reference).getSyntax();
    }
}
