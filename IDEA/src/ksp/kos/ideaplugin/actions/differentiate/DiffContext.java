package ksp.kos.ideaplugin.actions.differentiate;

import ksp.kos.ideaplugin.dataflow.ReferenceFlow;
import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
import ksp.kos.ideaplugin.reference.ReferableType;
import ksp.kos.ideaplugin.reference.context.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Created on 09/10/16.
 *
 * @author ptasha
 */
public class DiffContext extends FileContextAdapter {// TODO make it whole and efficient
    private final FileContext<KerboScriptNamedElement> origFileContext;

    // TODO let's decide later which of contexts is main
    public DiffContext(FileContextResolver<KerboScriptNamedElement, FileContext<KerboScriptNamedElement>> resolver,
                       FileContext<KerboScriptNamedElement> diffFileContext) {
        this(resolver, undiffContext(diffFileContext, resolver), diffFileContext);
    }

    private static FileContext<KerboScriptNamedElement> undiffContext(FileContext<KerboScriptNamedElement> fileContext,
                                                                    FileContextResolver<KerboScriptNamedElement, FileContext<KerboScriptNamedElement>> resolver) {
        String name = fileContext.getName();
        if (!name.endsWith("_")) {
            throw new IllegalArgumentException("FileContext must have name ending with '_'");
        }
        name = name.substring(0, name.length()-1);
        return resolver.resolveFile(name +"_");
    }

    private DiffContext(FileContextResolver<KerboScriptNamedElement, FileContext<KerboScriptNamedElement>> resolver,
                       FileContext<KerboScriptNamedElement> origFileContext,
                        FileContext<KerboScriptNamedElement> diffFileContext) {
        super(diffFileContext, createDiffResolvers(new DiffFileResolver(resolver), diffFileContext));
        this.origFileContext = origFileContext;
    }

    public static List<ReferenceResolver<ReferenceFlow, Context<ReferenceFlow>>> createDiffResolvers(
            FileContextResolver<ReferenceFlow, FileContext<ReferenceFlow>> fileResolver, Context<KerboScriptNamedElement> psiContext) {

        ArrayList<ReferenceResolver<ReferenceFlow, Context<ReferenceFlow>>> resolvers = new ArrayList<>();
        resolvers.addAll(FileContext.createResolvers(fileResolver));
        if (psiContext!=null) {
            resolvers.add(new PsiFlowContextAdapter.TransformResolver(psiContext));
        }
        return resolvers;
    }

    @NotNull
    @Override
    public Map<String, ReferenceFlow> createMap(ReferableType type) {
        return super.createMap(type); // TODO imports
    }

    private static class DiffFileResolver implements FileContextResolver<ReferenceFlow, FileContext<ReferenceFlow>> {
        private final FileContextResolver<KerboScriptNamedElement, FileContext<KerboScriptNamedElement>> resolver;
        private final HashMap<String, FileContext<ReferenceFlow>> resolved = new HashMap<>();

        private DiffFileResolver(FileContextResolver<KerboScriptNamedElement, FileContext<KerboScriptNamedElement>> resolver) {
            this.resolver = resolver;
        }

        @Override
        public FileContext<ReferenceFlow> resolveFile(String name) {
            FileContext<ReferenceFlow> context = resolved.get(name);
            if (context==null) {
                FileContext<KerboScriptNamedElement> psiContext = resolver.resolveFile(name);
                name = psiContext.getName();
                if (name.endsWith("_")) {
                    context = new DiffContext(resolver, psiContext);
                } else {
                    context = new FileContextAdapter(psiContext);
                }
                resolved.put(name, context);
            }
            return context;
        }
    }
}
