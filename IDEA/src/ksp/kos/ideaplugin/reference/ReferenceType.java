package ksp.kos.ideaplugin.reference;

/**
 * Created on 03/01/16.
 *
 * @author ptasha
 */
public enum ReferenceType {
    LOCAL(true),
    GLOBAL(true),
    REFERENCE(false),
    NONE(false);

    private final boolean decaration;

    ReferenceType(boolean decaration) {
        this.decaration = decaration;
    }

    public boolean isDecaration() {
        return decaration;
    }
}
