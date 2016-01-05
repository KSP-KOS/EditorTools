package ksp.kos.ideaplugin.psi;

import com.intellij.util.containers.HashMap;

/**
 * Created on 03/01/16.
 *
 * @author ptasha
 */
public class LocalScope {
    private final HashMap<String, KerboScriptNamedElement> variables = new HashMap<>();
    private final HashMap<String, KerboScriptDeclareFunctionClause> functions = new HashMap<>();

    private void addVariable(KerboScriptNamedElement declareVariable) {
        if (declareVariable != null && declareVariable.isLocal()) {
            String name = declareVariable.getName();
            if (name != null && !variables.containsKey(name)) {
                variables.put(name, declareVariable);
            }
        }
    }

    public void addInstruction(KerboScriptInstruction instruction) {
        KerboScriptDeclareStmt declareStmt = instruction.getDeclareStmt();
        if (declareStmt != null) {
            KerboScriptDeclareFunctionClause declareFunctionClause = declareStmt.getDeclareFunctionClause();
            if (declareFunctionClause != null) {
                String name = declareFunctionClause.getName();
                if (name != null && !functions.containsKey(name)) {
                    functions.put(name, declareFunctionClause);
                }
            }
            addVariable(declareStmt.getDeclareParameterClause());
            addVariable(declareStmt.getDeclareIdentifierClause());
        }
    }

    public KerboScriptNamedElement getVariable(String name) {
        return variables.get(name);
    }

    public KerboScriptDeclareFunctionClause getFunction(String name) {
        return functions.get(name);
    }
}
