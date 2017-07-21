package ksp.kos.ideaplugin.reference;

import ksp.kos.ideaplugin.reference.context.LocalContext;

import java.util.Objects;

/**
 * Created on 22/10/16.
 *
 * @author ptasha
 */
public class ReferenceImpl {
    protected final LocalContext kingdom;
    protected final ReferableType referableType;
    protected final String name;

    public ReferenceImpl(LocalContext kingdom, ReferableType referableType, String name) {
        this.name = name;
        this.kingdom = kingdom;
        this.referableType = referableType;
    }

    public LocalContext getKingdom() {
        return kingdom;
    }

    public ReferableType getReferableType() {
        return referableType;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Reference)) return false;
        Reference reference = (Reference) o;
        return referableType == reference.getReferableType() &&
                Objects.equals(name, reference.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(referableType, name);
    }
}
