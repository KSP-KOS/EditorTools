package ksp.kos.ideaplugin.psi;

/**
 * Created on 03/01/16.
 *
 * @author ptasha
 */
public class NamedType {
    private final SuffixtermType type;
    private final ReferenceType referenceType;

    public NamedType(SuffixtermType type, ReferenceType referenceType) {
        this.type = type;
        this.referenceType = referenceType;
    }

    public SuffixtermType getType() {
        return type;
    }

    public ReferenceType getReferenceType() {
        return referenceType;
    }
}
