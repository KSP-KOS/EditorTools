package ksp.kos.ideaplugin.reference;

import java.util.Objects;

/**
 * Created on 10/04/16.
 *
 * @author ptasha
 */
public class ReferenceImpl<T extends Reference> implements Reference<T> {
    private final Context<T> kingdom;
    private final ReferableType referableType;
    private final String name;

    public ReferenceImpl(Context<T> kingdom,  ReferableType referableType, String name) {
        this.kingdom = kingdom;
        this.referableType = referableType;
        this.name = name;
    }

    @Override
    public Context<T> getKingdom() {
        return kingdom;
    }

    @Override
    public ReferableType getReferableType() {
        return referableType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReferenceImpl reference = (ReferenceImpl) o;
        return referableType == reference.referableType &&
                Objects.equals(name, reference.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(referableType, name);
    }
}
