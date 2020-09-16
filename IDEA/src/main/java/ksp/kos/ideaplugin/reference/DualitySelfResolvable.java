package ksp.kos.ideaplugin.reference;

import ksp.kos.ideaplugin.reference.context.Duality;
import ksp.kos.ideaplugin.reference.context.LocalContext;
import org.jetbrains.annotations.NotNull;

/**
 * Created on 23/10/16.
 *
 * @author ptasha
 */
public interface DualitySelfResolvable extends Reference {
    static DualitySelfResolvable variable(LocalContext kingdom, String name) {
        return reference(kingdom, ReferableType.VARIABLE, name);
    }

    static DualitySelfResolvable function(LocalContext kingdom, String name) {
        return reference(kingdom, ReferableType.FUNCTION, name);
    }

    static DualitySelfResolvable file(LocalContext kingdom, String name) {
        return reference(kingdom, ReferableType.FILE, name);
    }

    @NotNull
    static DualitySelfResolvable reference(LocalContext kingdom, ReferableType type, String name) {
        return new DualityReferenceImpl(kingdom, type, name);
    }

    default Duality resolve() {
        return getKingdom().resolve(this);
    }

    default Duality findDeclaration() {
        return getKingdom().findDeclaration(this);
    }
}
