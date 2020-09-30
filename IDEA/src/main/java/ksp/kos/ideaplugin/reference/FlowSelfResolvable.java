package ksp.kos.ideaplugin.reference;

import ksp.kos.ideaplugin.dataflow.ReferenceFlow;
import ksp.kos.ideaplugin.reference.context.Duality;
import ksp.kos.ideaplugin.reference.context.LocalContext;
import org.jetbrains.annotations.NotNull;

/**
 * Created on 22/10/16.
 *
 * @author ptasha
 */
public interface FlowSelfResolvable extends Reference {
    static FlowSelfResolvable function(LocalContext kingdom, String name) {
        return reference(kingdom, ReferableType.FUNCTION, name);
    }

    @NotNull
    static FlowSelfResolvable reference(LocalContext kingdom, ReferableType type, String name) {
        return new FlowReferenceImpl(kingdom, type, name);
    }

    default ReferenceFlow<?> resolve() {
        return getSemantics(getKingdom().resolve(this));
    }

    default ReferenceFlow<?> findDeclaration() {
        return getSemantics(getKingdom().findDeclaration(this));
    }

    static ReferenceFlow<?> getSemantics(Duality duality) {
        if (duality==null) {
            return null;
        }
        return duality.getSemantics();
    }
}
