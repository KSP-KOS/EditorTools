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
import ksp.kos.ideaplugin.psi.KerboScriptDeclareFunctionClause;
import ksp.kos.ideaplugin.psi.KerboScriptDeclareStmt;
import ksp.kos.ideaplugin.psi.KerboScriptInstruction;
import ksp.kos.ideaplugin.reference.Reference;
import ksp.kos.utils.MapUnion;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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
            LinkedList<Reference> stack = new LinkedList<>();
            Map<Reference, FunctionFlow> processing = new HashMap<>();
            Map<Reference, FunctionFlow> processed = new HashMap<>();
            MapUnion<Reference, FunctionFlow> context = new MapUnion<>(processing, processed);

            Reference ref = declare.getDeclareFunctionClause();
            while (ref != null) {
                FunctionFlow function = FunctionFlow.parse((KerboScriptDeclareFunctionClause) ref.findDeclaration()).differentiate();
                while (function != null) {
                    Reference next = function.getNextToDiff(context);
                    if (next != null) {
                        processing.put(ref, function);
                        stack.push(ref);
                        function = null;
                    } else {
                        processed.put(ref, function);
                        next = stack.poll();
                        function = processing.remove(next);
                    }
                    ref = next;
                }
            }

            for (Map.Entry<Reference, FunctionFlow> entry : processed.entrySet()) {
                KerboScriptFile file = entry.getKey().getKingdom().getKerboScriptFile();
                KerboScriptFile diffFile = ensureDiffDependency(file);
                FunctionFlowImporter.INSTANCE.importFlow(diffFile, entry.getValue());
            }
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
            file = (KerboScriptFile) directory.add(file);
        }
        return file;
    }
}
