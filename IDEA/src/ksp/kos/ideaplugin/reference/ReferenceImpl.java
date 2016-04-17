package ksp.kos.ideaplugin.reference;

import ksp.kos.ideaplugin.psi.KerboScriptScope;

/**
 * Created on 10/04/16.
 *
 * @author ptasha
 */
public class ReferenceImpl implements Reference {
    private final KerboScriptScope kingdom;
    private final ReferableType referableType;
    private final String name;

    public ReferenceImpl(KerboScriptScope kingdom,  ReferableType referableType, String name) {
        this.kingdom = kingdom;
        this.referableType = referableType;
        this.name = name;
    }

    @Override
    public KerboScriptScope getKingdom() {
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
}
