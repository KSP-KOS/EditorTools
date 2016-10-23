package ksp.kos.ideaplugin.reference;

import ksp.kos.ideaplugin.dataflow.ReferenceFlow;
import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
import ksp.kos.ideaplugin.reference.context.LocalContext;

/**
 * Created on 22/10/16.
 *
 * @author ptasha
 */
public interface Reference<P extends KerboScriptNamedElement, F extends ReferenceFlow> {

    LocalContext getKingdom();

    ReferableType getReferableType();

    String getName();

    default boolean matches(Reference declaration) {
        return true;
    }
}
