package ksp.kos.ideaplugin.reference.context;

import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.dataflow.FunctionFlow;
import ksp.kos.ideaplugin.dataflow.ReferenceFlow;
import ksp.kos.ideaplugin.expressions.SyntaxException;
import ksp.kos.ideaplugin.psi.KerboScriptDeclareFunctionClause;
import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
import ksp.kos.ideaplugin.reference.ReferableType;

/**
 * Created on 22/10/16.
 *
 * @author ptasha
 */
public class PsiDuality<P extends KerboScriptNamedElement, F extends ReferenceFlow> implements Duality<P, F> {
    private final P psi;

    public PsiDuality(P psi) {
        this.psi = psi;
    }

    @Override
    public LocalContext getKingdom() {
        return psi.getKingdom();
    }

    @Override
    public ReferableType getReferableType() {
        return psi.getReferableType();
    }

    @Override
    public String getName() {
        return psi.getName();
    }

    @Override
    public P getSyntax() {
        return psi;
    }

    @SuppressWarnings("unchecked")
    @Override
    public F getSemantics() {
        return (F) psi.getCachedFlow();
    }
}
