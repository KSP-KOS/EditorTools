package ksp.kos.ideaplugin.reference;

import org.jetbrains.annotations.NotNull;

/**
 * Created on 10/04/16.
 *
 * @author ptasha
 */
public interface Reference {
    ReferableType getReferableType();

    String getName();

    static Reference variable(String name) {
        return reference(ReferableType.VARIABLE, name);
    }

    static Reference function(String name) {
        return reference(ReferableType.FUNCTION, name);
    }

    @NotNull
    static Reference reference(ReferableType type, String name) {
        return new ReferenceImpl(type, name);
    }
}
