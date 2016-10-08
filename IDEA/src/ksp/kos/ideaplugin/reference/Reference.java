package ksp.kos.ideaplugin.reference;

import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
import ksp.kos.ideaplugin.psi.KerboScriptScope;
import org.jetbrains.annotations.NotNull;

/**
 * Created on 10/04/16.
 *
 * @author ptasha
 */
public interface Reference<T extends Reference> {
    Context<T> getKingdom();

    ReferableType getReferableType();

    String getName();

    default T resolve() {
        return getKingdom().resolve(this);
    }

    default T findDeclaration() {
        return getKingdom().findDeclaration(this);
    }

    static Reference variable(KerboScriptScope kingdom, String name) {
        return reference(kingdom.getCachedScope(), ReferableType.VARIABLE, name);
    }

    static Reference<KerboScriptNamedElement> function(KerboScriptScope kingdom, String name) {
        return function(kingdom.getCachedScope(), name);
    }

    static <T extends Reference> Reference<T> function(Context<T> kingdom, String name) {
        return reference(kingdom, ReferableType.FUNCTION, name);
    }

    static <T extends Reference> Reference<T> copy(Reference<T> reference) {
        return reference(reference.getKingdom(), reference.getReferableType(), reference.getName());
    }

    @NotNull
    static <T extends Reference> Reference<T> reference(Context<T> kingdom, ReferableType type, String name) {
        return new ReferenceImpl<T>(kingdom, type, name);
    }
}
