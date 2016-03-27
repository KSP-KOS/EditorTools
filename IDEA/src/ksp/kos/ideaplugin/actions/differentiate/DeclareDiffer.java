package ksp.kos.ideaplugin.actions.differentiate;

import ksp.kos.ideaplugin.dataflow.VariableFlow;
import ksp.kos.ideaplugin.expressions.SyntaxException;
import ksp.kos.ideaplugin.psi.KerboScriptDeclareStmt;
import ksp.kos.ideaplugin.psi.KerboScriptInstruction;
import org.jetbrains.annotations.NotNull;

/**
 * Created on 27/03/16.
 *
 * @author ptasha
 */
public class DeclareDiffer extends DuplicateDiffer<KerboScriptDeclareStmt> {

    public DeclareDiffer() {
        super(KerboScriptDeclareStmt.class);
    }

    @Override
    public boolean canDo(KerboScriptInstruction instruction) {
        return super.canDo(instruction) && ((KerboScriptDeclareStmt) instruction).getDeclareIdentifierClause() !=null;
    }

    @Override
    @NotNull
    protected VariableFlow parse(KerboScriptDeclareStmt variable) throws SyntaxException {
        return VariableFlow.parse(variable.getDeclareIdentifierClause());
    }
}
