package ksp.kos.ideaplugin.reference.context;

import ksp.kos.ideaplugin.reference.Reference;

/**
 * Created on 10/10/16.
 *
 * @author ptasha
 */
public interface ReferenceResolver<C extends LocalContext> {
    Duality resolve(C context, Reference reference, boolean createAllowed);
}
