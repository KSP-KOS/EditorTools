package ksp.kos.ideaplugin.actions.differentiate;

import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.dataflow.FunctionFlow;
import ksp.kos.ideaplugin.dataflow.FunctionFlowImporter;
import ksp.kos.ideaplugin.dataflow.ImportFlow;
import ksp.kos.ideaplugin.dataflow.ImportFlowImporter;
import ksp.kos.ideaplugin.reference.FlowSelfResolvable;
import ksp.kos.ideaplugin.reference.OccurrenceType;
import ksp.kos.ideaplugin.reference.ReferableType;
import ksp.kos.ideaplugin.reference.Reference;
import ksp.kos.ideaplugin.reference.context.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created on 23/10/16.
 *
 * @author ptasha
 */
public class DiffContext extends FileContext {
    private final FileDuality file;
    private final FileContextResolver fileResolver;// TODO get rid of me

    public DiffContext(FileContext origFile, FileDuality diffFile, FileContextResolver fileResolver) {
        super(null, origFile.getName() + "_", createDiffResolvers(fileResolver));
        this.file = diffFile;
        this.fileResolver = fileResolver;
        for (String name : origFile.getImports().keySet()) {
            registerFile(name);
            registerFile(name+"_");
        }
        registerFile(origFile.getName());
    }

    public static List<ReferenceResolver<LocalContext>> createDiffResolvers(FileContextResolver fileResolver) {
        List<ReferenceResolver<LocalContext>> resolvers = FileContext.createResolvers(fileResolver);
        resolvers.add((context, reference, createAllowed) -> {
            if (createAllowed && reference.getName().endsWith("_")) {
                String name1 = reference.getName();
                name1 = name1.substring(0, name1.length()-1);
                FunctionFlow original = (FunctionFlow) FlowSelfResolvable.function(context, name1).findDeclaration();
                if (original!=null) {
                    String fileName = original.getKingdom().getFileContext().getName();
                    FileDuality diffFile = fileResolver.resolveFile(fileName + "_");
                    return original.differentiate(diffFile.getSemantics());
                }
            }
            return null;
        });
        return resolvers;
    }

    @Nullable
    @Override
    public Duality findLocalDeclaration(@NotNull Reference reference, @Nullable OccurrenceType occurrenceTypeFilter) {
        Duality declaration = super.findLocalDeclaration(reference, occurrenceTypeFilter);
        if (declaration==null && file!=null) {
            return file.getSemantics().findLocalDeclaration(reference, occurrenceTypeFilter);
        }
        return declaration;
    }

    public void importFlows() {
        for (Duality duality : getDeclarations(ReferableType.FUNCTION).values()) {
            importFlow((FunctionFlow) duality.getSemantics());
        }
        ensureImports();
    }

    private KerboScriptFile ensureFile() {
        FileDuality diffFile = fileResolver.ensureFile(getName());
        return diffFile.getSyntax();
    }

    private void importFlow(FunctionFlow flow) {
        if (flow.hasDependees()) {
            FunctionFlowImporter.INSTANCE.importFlow(ensureFile(), flow);
        }
    }

    private void ensureImports() {
        ImportFlowImporter importImporter = ImportFlowImporter.INSTANCE;
        for (Duality importDuality : getDeclarations(ReferableType.FILE).values()) {
            ImportFlow importFlow = (ImportFlow) importDuality.getSemantics();
            if (importFlow.hasDependees()) {
                importImporter.importFlow(ensureFile(), importFlow);
            }
        }
    }

    private void registerFile(String name) {
        register(new ImportFlow(name));
    }

    @Override
    public @NotNull KerboScriptFile getSyntax() {
        return ensureFile();
    }

    public void checkUsage() {
        for (Duality duality : getDeclarations(ReferableType.FUNCTION).values()) {
            ((FunctionFlow) duality.getSemantics()).checkUsages();
        }
    }
}
