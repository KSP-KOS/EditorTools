package ksp.kos.ideaplugin.dataflow;

import com.intellij.psi.codeStyle.CodeStyleManager;
import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.psi.KerboScriptBase;
import ksp.kos.ideaplugin.psi.KerboScriptElementFactory;
import ksp.kos.ideaplugin.psi.KerboScriptInstruction;
import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
import ksp.kos.ideaplugin.reference.LocalScope;
import org.jetbrains.annotations.NotNull;

/**
 * Created on 05/04/16.
 *
 * @author ptasha
 */
public abstract class FlowImporter<F extends NamedFlow<F>> {
    @NotNull
    protected abstract LocalScope.ScopeMap getMap(KerboScriptFile file);

    public void importFlow(KerboScriptFile file, F flow) {
        LocalScope.ScopeMap map = getMap(file);
        KerboScriptInstruction instruction = importFlow(file, flow, map);
        CodeStyleManager.getInstance(file.getProject()).reformatNewlyAddedElement(instruction.getParent().getNode(), instruction.getNode());
    }

    protected KerboScriptInstruction importFlow(KerboScriptFile file, F flow, LocalScope.ScopeMap map) {
        KerboScriptInstruction instruction = KerboScriptElementFactory.instruction(file.getProject(), flow.getText());
        KerboScriptInstruction existing = getInstruction(map, flow);
        if (existing != null) {
            instruction = replaceExisting(instruction, existing);
        } else {
            instruction = insertInstruction(file, instruction, flow.getName(), map);
        }
        return instruction;
    }

    private KerboScriptInstruction getInstruction(LocalScope.ScopeMap map, F flow) {
        KerboScriptNamedElement element = map.get(flow.getName());
        if (element==null) {
            return null;
        }
        return element.upTill(KerboScriptInstruction.class);
    }

    protected KerboScriptInstruction replaceExisting(KerboScriptInstruction instruction, KerboScriptInstruction existing) {
        return (KerboScriptInstruction) existing.replace(instruction);
    }

    private KerboScriptInstruction insertInstruction(KerboScriptFile file, KerboScriptInstruction instruction, String name, LocalScope.ScopeMap map) {
        KerboScriptInstruction before = null;
        for (KerboScriptNamedElement element : map.values()) {
            if (element.getName().compareToIgnoreCase(name)>0) {
                before = element.upTill(KerboScriptInstruction.class);
                break;
            }
        }
        if (before==null) {
            instruction = addNewInstruction(file, instruction);
        } else {
            separator(instruction);
            instruction = (KerboScriptInstruction) file.addBefore(instruction, before);
        }
        return instruction;
    }

    protected KerboScriptInstruction addNewInstruction(KerboScriptFile file, KerboScriptInstruction instruction) {
        separator(file);
        return (KerboScriptInstruction) file.add(instruction);
    }

    protected void separator(KerboScriptBase element) {
        element.newLine();
    }
}
