package ksp.kos.ideaplugin.reference;

/**
 * Created on 10/04/16.
 *
 * @author ptasha
 */
public class ReferenceImpl implements Reference {
    private final ReferableType referableType;
    private final String name;

    public ReferenceImpl(ReferableType referableType, String name) {
        this.referableType = referableType;
        this.name = name;
    }

    @Override
    public ReferableType getReferableType() {
        return referableType;
    }

    @Override
    public String getName() {
        return name;
    }
}
