package ksp.kos.ideaplugin.reference.context;

import ksp.kos.ideaplugin.dataflow.ReferenceFlow;
import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
import ksp.kos.ideaplugin.reference.Reference;

/**
 * Created on 22/10/16.
 *
 * @author ptasha
 */
public interface Duality<P extends KerboScriptNamedElement, F extends ReferenceFlow> extends Reference {
    // TODO simplify duality tree

    static KerboScriptNamedElement getSyntax(Duality duality) {
        if (duality!=null) {
            return duality.getSyntax();
        }
        return null;
    }

    P getSyntax();
    F getSemantics();
}
