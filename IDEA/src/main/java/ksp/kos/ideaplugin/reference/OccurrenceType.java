package ksp.kos.ideaplugin.reference;

/**
 * Created on 03/01/16.
 *
 * @author ptasha
 */
public enum OccurrenceType {
    LOCAL(true),
    GLOBAL(true),
    REFERENCE(false),
    NONE(false);

    private final boolean declaration;

    OccurrenceType(boolean declaration) {
        this.declaration = declaration;
    }

    public boolean isDeclaration() {
        return declaration;
    }
}
