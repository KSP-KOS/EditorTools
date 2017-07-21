package ksp.kos.ideaplugin.actions.differentiate;

import com.intellij.openapi.project.Project;
import ksp.kos.ideaplugin.actions.ActionFailedException;
import ksp.kos.ideaplugin.dataflow.FunctionFlow;
import ksp.kos.ideaplugin.expressions.SyntaxException;
import ksp.kos.ideaplugin.psi.KerboScriptDeclareFunctionClause;
import ksp.kos.ideaplugin.psi.KerboScriptDeclareStmt;
import ksp.kos.ideaplugin.psi.KerboScriptInstruction;
import ksp.kos.ideaplugin.reference.PsiFileResolver;
import ksp.kos.ideaplugin.reference.PsiSelfResolvable;
import ksp.kos.ideaplugin.reference.context.FileContext;

/**
 * Created on 27/03/16.
 *
 * @author ptasha
 */
public class FunctionDiffer implements Differ {

    @Override
    public boolean canDo(KerboScriptInstruction instruction) {
        return instruction instanceof KerboScriptDeclareStmt && ((KerboScriptDeclareStmt) instruction).getDeclareFunctionClause() != null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void doIt(Project project, KerboScriptInstruction instruction) throws ActionFailedException {
        KerboScriptDeclareStmt declare = (KerboScriptDeclareStmt) instruction;
        try {
            DiffContextResolver contextResolver = new DiffContextResolver(new PsiFileResolver(instruction.getKerboScriptFile()));

            PsiSelfResolvable ref = declare.getDeclareFunctionClause();
            FileContext diffContext = contextResolver.resolveFile(ref.getKingdom().getFileContext().getName()+"_").getSemantics();
            FunctionFlow diff = FunctionFlow.parse((KerboScriptDeclareFunctionClause) ref.findDeclaration()).differentiate(diffContext);
            diff.addDependee(diff);
            contextResolver.importFlows();
        } catch (SyntaxException e) {
            throw new ActionFailedException(e);
        }
    }

}
