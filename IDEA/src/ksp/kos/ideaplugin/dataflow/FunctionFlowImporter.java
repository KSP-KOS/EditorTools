package ksp.kos.ideaplugin.dataflow;

import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.psi.KerboScriptBase;
import ksp.kos.ideaplugin.reference.context.Duality;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Created on 05/04/16.
 *
 * @author ptasha
 */
public class FunctionFlowImporter extends FlowImporter<FunctionFlow> {
    public static final FunctionFlowImporter INSTANCE = new FunctionFlowImporter();

    @Override
    @NotNull
    protected Map<String, Duality> getMap(KerboScriptFile file) {
        return file.getCachedScope().getFunctions();
    }

    @Override
    protected void separator(KerboScriptBase element) {
        super.separator(element);
        super.separator(element);
    }
}
