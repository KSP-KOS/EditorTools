package ksp.kos.ideaplugin.reference.context;

import ksp.kos.ideaplugin.dataflow.ReferenceFlow;
import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
import ksp.kos.ideaplugin.reference.ReferableType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * Created on 22/10/16.
 *
 * @author ptasha
 */
public class FileContextAdapter extends FileContext<ReferenceFlow> implements ReferenceFlow {
    protected final FileContext<KerboScriptNamedElement> psiFileContext;

    public FileContextAdapter(FileContext<KerboScriptNamedElement> fileContext) {
        this(fileContext, PsiFlowContextAdapter.createResolvers());
    }

    protected FileContextAdapter(FileContext<KerboScriptNamedElement> fileContext,
                              List<ReferenceResolver<ReferenceFlow, Context<ReferenceFlow>>> resolvers) {
        super(null, fileContext.getName(), resolvers);
        this.psiFileContext = fileContext;
    }

    @Override
    public ReferenceFlow getFile() {
        return this;
    }

    @NotNull
    @Override
    public Map<String, ReferenceFlow> createMap(ReferableType type) {
        return new PsiFlowContextAdapter.AdapterMap(psiFileContext.createMap(type));
    }
}
