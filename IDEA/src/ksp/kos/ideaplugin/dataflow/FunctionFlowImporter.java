package ksp.kos.ideaplugin.dataflow;

import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.psi.KerboScriptBase;
import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

/**
 * Created on 05/04/16.
 *
 * @author ptasha
 */
public class FunctionFlowImporter extends FlowImporter<FunctionFlow> {
    public static final FunctionFlowImporter INSTANCE = new FunctionFlowImporter();

    @Override
    public void importFlow(KerboScriptFile file, FunctionFlow flow) {
        Set<ImportFlow> imports = flow.getImports(file);
        super.importFlow(file, flow);
        ensureImports(file, imports);
    }

    @Override
    @NotNull
    protected Map<String, KerboScriptNamedElement> getMap(KerboScriptFile file) {
        return file.getCachedScope().getFunctions();
    }

    @Override
    protected void separator(KerboScriptBase element) {
        super.separator(element);
        super.separator(element);
    }

    private void ensureImports(KerboScriptFile file, Set<ImportFlow> imports) {
        ImportFlowImporter importImporter = ImportFlowImporter.INSTANCE;
        for (ImportFlow flow : imports) {
            importImporter.importFlow(file, flow);
        }
    }
}
