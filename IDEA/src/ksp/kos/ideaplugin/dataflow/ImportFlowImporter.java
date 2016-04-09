package ksp.kos.ideaplugin.dataflow;

import com.intellij.psi.util.PsiTreeUtil;
import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.psi.KerboScriptDirective;
import ksp.kos.ideaplugin.psi.KerboScriptInstruction;
import ksp.kos.ideaplugin.psi.KerboScriptRunStmt;
import ksp.kos.ideaplugin.reference.LocalScope;
import org.jetbrains.annotations.NotNull;

/**
 * Created on 09/04/16.
 *
 * @author ptasha
 */
public class ImportFlowImporter extends FlowImporter<ImportFlow> {
    public static final ImportFlowImporter INSTANCE = new ImportFlowImporter();

    @NotNull
    @Override
    protected LocalScope.ScopeMap getMap(KerboScriptFile file) {
        return file.getFileScope().getImports();
    }

    @Override
    protected KerboScriptInstruction addNewInstruction(KerboScriptFile file, KerboScriptInstruction instruction) {
        for (KerboScriptInstruction before : PsiTreeUtil.findChildrenOfType(file, KerboScriptInstruction.class)) {
            if (!(before instanceof KerboScriptDirective)) {
                instruction = (KerboScriptInstruction) file.addBefore(instruction, before);
                separator(instruction);
                separator(instruction);
                return instruction;
            }
        }
        return super.addNewInstruction(file, instruction);
    }

    @Override
    protected KerboScriptInstruction replaceExisting(KerboScriptInstruction instruction, KerboScriptInstruction existing) {
        return existing;
    }

    @Override
    protected KerboScriptInstruction importFlow(KerboScriptFile file, ImportFlow flow, LocalScope.ScopeMap map) {
        KerboScriptInstruction instruction = super.importFlow(file, flow, map);
        KerboScriptRunStmt runStmt = instruction.getRunStmt();
        map.put(runStmt.getName(), runStmt);
        return instruction;
    }
}
