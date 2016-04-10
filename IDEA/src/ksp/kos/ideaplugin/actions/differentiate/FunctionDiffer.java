package ksp.kos.ideaplugin.actions.differentiate;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFileFactory;
import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.KerboScriptLanguage;
import ksp.kos.ideaplugin.actions.ActionFailedException;
import ksp.kos.ideaplugin.dataflow.FunctionFlow;
import ksp.kos.ideaplugin.dataflow.FunctionFlowImporter;
import ksp.kos.ideaplugin.expressions.SyntaxException;
import ksp.kos.ideaplugin.psi.KerboScriptDeclareStmt;
import ksp.kos.ideaplugin.psi.KerboScriptInstruction;

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
            FunctionFlow function = FunctionFlow.parse(declare.getDeclareFunctionClause()).differentiate();
            // TODO check for dependencies and diff them as well: just refuse recursion for a while
            KerboScriptFile file = instruction.getKerboScriptFile();
            KerboScriptFile diffFile = ensureDiffDependency(file);
            FunctionFlowImporter.INSTANCE.importFlow(diffFile, function);
        } catch (SyntaxException e) {
            throw new ActionFailedException(e);
        }
    }

    private KerboScriptFile ensureDiffDependency(KerboScriptFile file) {
        Project project = file.getProject();
        PsiDirectory directory = file.getContainingDirectory();
        String name = file.getPureName();
        if (name.endsWith("_")) {
            return file;
        }
        name += "_";
        file = file.findFile(name);
        if (file == null) {
            file = (KerboScriptFile) PsiFileFactory.getInstance(project).createFileFromText(
                    name + ".ks", KerboScriptLanguage.INSTANCE, "@lazyglobal off.");
            file = (KerboScriptFile)directory.add(file);
        }
        return file;
    }
}
