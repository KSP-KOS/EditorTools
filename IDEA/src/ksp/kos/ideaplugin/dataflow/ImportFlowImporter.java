package ksp.kos.ideaplugin.dataflow;

import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.psi.KerboScriptDirective;
import ksp.kos.ideaplugin.psi.KerboScriptInstruction;
import ksp.kos.ideaplugin.psi.KerboScriptRunStmt;
import ksp.kos.ideaplugin.reference.context.Duality;
import ksp.kos.ideaplugin.reference.context.PsiDuality;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Created on 09/04/16.
 *
 * @author ptasha
 */
public class ImportFlowImporter extends FlowImporter<ImportFlow> {
    public static final ImportFlowImporter INSTANCE = new ImportFlowImporter();

    @NotNull
    @Override
    protected Map<String, Duality> getMap(KerboScriptFile file) {
        return file.getCachedScope().getImports();
    }

    @Override
    protected KerboScriptInstruction addNewInstruction(KerboScriptFile file, KerboScriptInstruction instruction) {
        for (KerboScriptInstruction before : file.getChildren(KerboScriptInstruction.class)) {
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
    protected KerboScriptInstruction importFlow(KerboScriptFile file, ImportFlow flow, Map<String, Duality> map) {
        KerboScriptInstruction instruction = super.importFlow(file, flow, map);
        KerboScriptRunStmt runStmt = instruction.getRunStmt();
        map.put(runStmt.getName(), new PsiDuality<>(runStmt));
        return instruction;
    }
}
