package ksp.kos.ideaplugin.reference;

import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.dataflow.ReferenceFlow;
import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
import ksp.kos.ideaplugin.reference.context.Duality;
import ksp.kos.ideaplugin.reference.context.FileContext;
import ksp.kos.ideaplugin.reference.context.LocalContext;
import org.jetbrains.annotations.NotNull;

/**
 * Created on 23/10/16.
 *
 * @author ptasha
 */
public interface DualitySelfResolvable<P extends KerboScriptNamedElement, F extends ReferenceFlow> extends Reference<P, F> {
    static DualitySelfResolvable variable(LocalContext kingdom, String name) {
        return reference(kingdom, ReferableType.VARIABLE, name);
    }

    static DualitySelfResolvable function(LocalContext kingdom, String name) {
        return reference(kingdom, ReferableType.FUNCTION, name);
    }

    static DualitySelfResolvable<KerboScriptFile, FileContext> file(LocalContext kingdom, String name) {
        return reference(kingdom, ReferableType.FILE, name);
    }

    @NotNull
    static DualitySelfResolvable reference(LocalContext kingdom, ReferableType type, String name) {
        return new DualityReferenceImpl(kingdom, type, name);
    }

    default Duality<P, F> resolve() {
        return getKingdom().resolve(this);
    }

    default Duality<P, F> findDeclaration() {
        return getKingdom().findDeclaration(this);
    }
}
