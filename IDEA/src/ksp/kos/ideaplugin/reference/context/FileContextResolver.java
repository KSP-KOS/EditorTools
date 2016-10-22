package ksp.kos.ideaplugin.reference.context;

import ksp.kos.ideaplugin.reference.Reference;

/**
 * Created on 08/10/16.
 *
 * @author ptasha
 */
public interface FileContextResolver<B extends Reference, C extends FileContext<B>> {
    C resolveFile(String name); // TODO make it return reference instead
}
