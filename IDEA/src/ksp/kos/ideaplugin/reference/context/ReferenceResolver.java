package ksp.kos.ideaplugin.reference.context;

import ksp.kos.ideaplugin.reference.Reference;

/**
 * Created on 10/10/16.
 *
 * @author ptasha
 */
public interface ReferenceResolver<B extends Reference, C extends Context<B>> {
    B resolve(C context, Reference reference, boolean createAllowed);
}
