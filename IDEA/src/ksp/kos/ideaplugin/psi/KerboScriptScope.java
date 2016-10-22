package ksp.kos.ideaplugin.psi;

import ksp.kos.ideaplugin.reference.context.Context;
import ksp.kos.ideaplugin.reference.Reference;

/**
 * Created on 07/01/16.
 *
 * @author ptasha
 */
public interface KerboScriptScope extends KerboScriptBase { // TODO remove me
    Context<KerboScriptNamedElement> getCachedScope();

    default KerboScriptNamedElement resolve(Reference reference) {
        return getCachedScope().resolve(reference);
    }
}
