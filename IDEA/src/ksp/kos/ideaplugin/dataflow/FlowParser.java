package ksp.kos.ideaplugin.dataflow;

import ksp.kos.ideaplugin.expressions.SyntaxException;
import ksp.kos.ideaplugin.psi.*;

import java.util.List;

/**
 * Created on 27/08/16.
 *
 * @author ptasha
 */
public class FlowParser {
    private Context context;

    public FlowParser() {
        this(null);
    }

    public FlowParser(Context parent) {
        context = new Context(parent);
    }

    public boolean parseInstructions(List<KerboScriptInstruction> instructions) throws SyntaxException {
        for (KerboScriptInstruction instruction : instructions) {
            if (!parseInstruction(instruction)) return false;
        }
        return true;
    }

    public boolean parseInstruction(KerboScriptInstruction instruction) throws SyntaxException {
        if (instruction instanceof KerboScriptSetStmt) {
            VariableFlow variableFlow = VariableFlow.parse((KerboScriptSetStmt) instruction);
            if (variableFlow != null) {
                addFlow(variableFlow);
            }
        } else if (instruction instanceof KerboScriptDeclareStmt) {
            KerboScriptDeclareStmt declareStmt = (KerboScriptDeclareStmt) instruction;
            KerboScriptDeclareParameterClause declareParameterClause = declareStmt.getDeclareParameterClause();
            if (declareParameterClause != null) {
                addFlow(ParameterFlow.parse(declareParameterClause));
            } else if (declareStmt.getDeclareIdentifierClause() != null) {
                addFlow(VariableFlow.parse(declareStmt.getDeclareIdentifierClause()));
            }
        } else if (instruction instanceof KerboScriptReturnStmt) {
            addFlow(ReturnFlow.parse((KerboScriptReturnStmt) instruction));
            return false;
        } else if (instruction instanceof KerboScriptIfStmt) {
            KerboScriptIfStmt ifStmt = (KerboScriptIfStmt) instruction;
            addFlow(IfFlow.parse(ifStmt, context));
        } else if (instruction.getInstructionBlock()!=null) {
            KerboScriptInstructionBlock instructionBlock = instruction.getInstructionBlock();
            return parseInstructions(instructionBlock.getInstructionList());
        }
        // TODO throw exception on unknown instructions?
        return true;
    }

    protected void addFlow(Flow<?> flow) {
        context.add(flow);
    }

    public Context getContext() {
        return context;
    }
}
