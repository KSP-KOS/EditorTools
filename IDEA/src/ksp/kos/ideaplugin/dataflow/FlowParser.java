package ksp.kos.ideaplugin.dataflow;

import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.expressions.SyntaxException;
import ksp.kos.ideaplugin.psi.KerboScriptDeclareFunctionClause;
import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;

import java.util.function.Function;

/**
 * Created on 23/10/16.
 *
 * @author ptasha
 */
public class FlowParser implements Function<KerboScriptNamedElement, ReferenceFlow> {
    public static final FlowParser INSTANCE = new FlowParser();

    @Override
    public ReferenceFlow apply(KerboScriptNamedElement psi) {
        if (psi instanceof KerboScriptDeclareFunctionClause) {
            try {
                return FunctionFlow.parse((KerboScriptDeclareFunctionClause) psi);
            } catch (SyntaxException e) {
                e.printStackTrace(); // TODO
            }
        } else if (psi instanceof KerboScriptFile) {
            return ((KerboScriptFile) psi).getCachedScope();
        }
        return null;
    }
}
