package ksp.kos.ideaplugin.actions.differentiate;

import ksp.kos.ideaplugin.dataflow.VariableFlow;
import ksp.kos.ideaplugin.expressions.SyntaxException;
import ksp.kos.ideaplugin.psi.KerboScriptInstruction;
import ksp.kos.ideaplugin.psi.KerboScriptSetStmt;

/**
 * Created on 27/03/16.
 *
 * @author ptasha
 */
public class SetDiffer extends DuplicateDiffer<KerboScriptSetStmt> {
    public SetDiffer() {
        super(KerboScriptSetStmt.class);
    }

    @Override
    public boolean canDo(KerboScriptInstruction instruction) {
        try {
            return super.canDo(instruction) && (parse((KerboScriptSetStmt) instruction)!=null);
        } catch (SyntaxException e) {
            return true;
        }
    }

    @Override
    protected VariableFlow parse(KerboScriptSetStmt variable) throws SyntaxException {
        return VariableFlow.parse(variable);
    }
}
