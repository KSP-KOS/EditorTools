package ksp.kos.ideaplugin.reference;

import ksp.kos.ideaplugin.dataflow.FunctionFlow;
import ksp.kos.ideaplugin.dataflow.ReferenceFlow;
import ksp.kos.ideaplugin.reference.context.Duality;
import ksp.kos.ideaplugin.reference.context.LocalContext;
import org.jetbrains.annotations.NotNull;

/**
 * Created on 22/10/16.
 *
 * @author ptasha
 */
public interface FlowSelfResolvable<F extends ReferenceFlow<F>> extends Reference {
    static FlowSelfResolvable<FunctionFlow> function(LocalContext kingdom, String name) {
        return reference(kingdom, ReferableType.FUNCTION, name);
    }

    @NotNull
    static FlowSelfResolvable reference(LocalContext kingdom, ReferableType type, String name) {
        return new FlowReferenceImpl(kingdom, type, name);
    }

    default F resolve() {
        return getSemantics(getKingdom().resolve(this));
    }

    default F findDeclaration() {
        return getSemantics(getKingdom().findDeclaration(this));
    }

    static <F extends ReferenceFlow> F getSemantics(Duality<?, F> duality) {
        if (duality==null) {
            return null;
        }
        return duality.getSemantics();
    }
}
