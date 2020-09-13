package ksp.kos.ideaplugin.reference;

/**
 * Created on 03/01/16.
 *
 * @author ptasha
 */
public class ReferenceType {
    private final ReferableType type;
    private final OccurrenceType occurrenceType;

    public ReferenceType(ReferableType type, OccurrenceType occurrenceType) {
        this.type = type;
        this.occurrenceType = occurrenceType;
    }

    public ReferableType getType() {
        return type;
    }

    public OccurrenceType getOccurrenceType() {
        return occurrenceType;
    }
}
