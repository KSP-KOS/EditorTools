package ksp.kos.ideaplugin.actions.differentiate;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFileFactory;
import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.KerboScriptLanguage;
import ksp.kos.ideaplugin.actions.ActionFailedException;
import ksp.kos.ideaplugin.dataflow.FunctionFlow;
import ksp.kos.ideaplugin.dataflow.FunctionFlowImporter;
import ksp.kos.ideaplugin.dataflow.ImportFlow;
import ksp.kos.ideaplugin.dataflow.ImportFlowImporter;
import ksp.kos.ideaplugin.expressions.SyntaxException;
import ksp.kos.ideaplugin.psi.KerboScriptDeclareFunctionClause;
import ksp.kos.ideaplugin.psi.KerboScriptDeclareStmt;
import ksp.kos.ideaplugin.psi.KerboScriptInstruction;
import ksp.kos.ideaplugin.reference.PsiFileResolver;
import ksp.kos.ideaplugin.reference.PsiSelfResolvable;
import ksp.kos.ideaplugin.reference.context.Duality;
import ksp.kos.utils.MapUnion;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

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
        // TODO create DiffContext on it's base
        KerboScriptDeclareStmt declare = (KerboScriptDeclareStmt) instruction;
        try {
            LinkedList<PsiSelfResolvable> stack = new LinkedList<>();
            Map<PsiSelfResolvable, FunctionFlow> processing = new HashMap<>();
            Map<PsiSelfResolvable, FunctionFlow> processed = new HashMap<>();
            MapUnion<PsiSelfResolvable, FunctionFlow> context = new MapUnion<>(processing, processed);

            PsiSelfResolvable ref = declare.getDeclareFunctionClause();
            while (ref != null) {
                // TODO create diff resolver
                DiffContext diffContext = new DiffContext(ref.getKingdom().getFileContext(), new PsiFileResolver(declare.getKerboScriptFile()));
                FunctionFlow function = FunctionFlow.parse((KerboScriptDeclareFunctionClause) ref.findDeclaration()).differentiate(diffContext);
                while (function != null) {
                    Duality next = function.getNextToDiff(context);
                    PsiSelfResolvable nextRef;
                    if (next != null) {
                        processing.put(PsiSelfResolvable.copy(ref), function);
                        stack.push(ref);
                        function = null;
                        nextRef = next.getSyntax();
                    } else {
                        processed.put(PsiSelfResolvable.copy(ref), function);
                        nextRef = stack.poll();
                        function = processing.remove(nextRef);
                    }
                    ref = nextRef;
                }
            }

            for (Map.Entry<PsiSelfResolvable, FunctionFlow> entry : processed.entrySet()) {
                KerboScriptFile file = entry.getKey().findDeclaration().getKerboScriptFile();
                KerboScriptFile diffFile = ensureDiffDependency(file);
                FunctionFlow flow = entry.getValue();
                FunctionFlowImporter.INSTANCE.importFlow(diffFile, flow);

                ensureImports(file, flow.getImports(file.getCachedScope()));
                // TODO import flows without \n
            }
        } catch (SyntaxException e) {
            throw new ActionFailedException(e);
        }
    }

    private static void ensureImports(KerboScriptFile file, Set<ImportFlow> imports) {
        ImportFlowImporter importImporter = ImportFlowImporter.INSTANCE;
        for (ImportFlow flow : imports) {
            importImporter.importFlow(file, flow);
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
