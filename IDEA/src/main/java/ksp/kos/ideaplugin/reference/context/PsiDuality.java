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
public class PsiDuality implements Duality {
    private final KerboScriptNamedElement psi;

    public PsiDuality(KerboScriptNamedElement psi) {
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
    public KerboScriptNamedElement getSyntax() {
        return psi;
    }

    @Override
    public ReferenceFlow<?> getSemantics() {
        return psi.getCachedFlow();
    }
}
