package ksp.kos.ideaplugin.reference;

import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
import ksp.kos.ideaplugin.psi.KerboScriptScope;
import org.jetbrains.annotations.NotNull;

/**
 * Created on 10/04/16.
 *
 * @author ptasha
 */
public interface Reference {
    KerboScriptScope getKingdom();

    ReferableType getReferableType();

    String getName();

    default KerboScriptNamedElement resolve() {
        return getKingdom().resolve(this);
    }

    default KerboScriptNamedElement findDeclaration() {
        return getKingdom().getCachedScope().findDeclaration(this);
    }

    static Reference variable(KerboScriptScope kingdom, String name) {
        return reference(kingdom, ReferableType.VARIABLE, name);
    }

    static Reference function(KerboScriptScope kingdom, String name) {
        return reference(kingdom, ReferableType.FUNCTION, name);
    }

    @NotNull
    static Reference reference(KerboScriptScope kingdom, ReferableType type, String name) {
        return new ReferenceImpl(kingdom, type, name);
    }
}
