package ksp.kos.ideaplugin.reference.context;

import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.dataflow.ReferenceFlow;
import ksp.kos.ideaplugin.reference.ReferableType;
import ksp.kos.ideaplugin.reference.Reference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created on 08/10/16.
 *
 * @author ptasha
 */
public abstract class FileContext extends LocalContext implements ReferenceFlow, FileDuality {
    private final String name;

    public FileContext(LocalContext parent, String name, FileContextResolver fileResolver) {
        this(parent, name, createResolvers(fileResolver));
    }

    protected FileContext(LocalContext parent, String name, List<ReferenceResolver<LocalContext>> resolvers) {
        super(parent, resolvers);
        this.name = name;
    }

    public static List<ReferenceResolver<LocalContext>> createResolvers(FileContextResolver fileResolver) {
        ArrayList<ReferenceResolver<LocalContext>> resolvers = new ArrayList<>();
        resolvers.add(new FileResolver(fileResolver));
        resolvers.add(new LocalResolver());
        resolvers.add(new VirtualResolver());
        resolvers.add(new ImportsResolver(fileResolver));
        return resolvers;
    }

    @Override
    public LocalContext getKingdom() {
        return this;
    }

    @Override
    public ReferableType getReferableType() {
        return ReferableType.FILE;
    }

    @Override
    public String getName() {
        return name;
    }

    public Map<String, Duality> getImports() {
        return getDeclarations(ReferableType.FILE);
    }

    @Override
    protected void registerUnknown(ReferableType type, String name, Duality element) {
        if (type==ReferableType.FILE) {
            addDefinition(type, KerboScriptFile.stripExtension(name), element);
        } else {
            super.registerUnknown(type, name, element);
        }
    }

    public static class FileResolver implements ReferenceResolver<LocalContext> {

        private final FileContextResolver fileContextResolver;

        public FileResolver(FileContextResolver fileContextResolver) {
            this.fileContextResolver = fileContextResolver;
        }

        @Override
        public Duality resolve(LocalContext context, Reference reference, boolean createAllowed) {
            if (reference.getReferableType()==ReferableType.FILE) {
                return fileContextResolver.resolveFile(reference.getName());
            }
            return null;
        }
    }

    @Override
    public FileContext getSemantics() {
        return this;
    }

    public static class ImportsResolver implements ReferenceResolver<LocalContext> {

        private final FileContextResolver fileContextResolver;

        public ImportsResolver(FileContextResolver fileContextResolver) {
            this.fileContextResolver = fileContextResolver;
        }

        @Override
        public Duality resolve(LocalContext context, Reference reference, boolean createAllowed) {
            for (Duality run : context.getDeclarations(ReferableType.FILE).values()) {
                FileDuality dependency = fileContextResolver.resolveFile(run.getName());
                if (dependency != null) {
                    Duality resolved = dependency.getSemantics().findLocalDeclaration(reference);
                    if (resolved != null) {
                        return resolved;
                    }
                }
            }
            return null;
        }
    }

    private static class VirtualResolver extends ParentResolver {
        @Override
        public Duality resolve(LocalContext context, Reference reference, boolean createAllowed) {
            if (!createAllowed) {
                return null;
            }
            return super.resolve(context, reference, createAllowed);
        }
    }
}
