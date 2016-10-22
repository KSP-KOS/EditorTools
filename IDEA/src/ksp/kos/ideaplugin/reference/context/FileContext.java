package ksp.kos.ideaplugin.reference.context;

import ksp.kos.ideaplugin.reference.ReferableType;
import ksp.kos.ideaplugin.reference.Reference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 08/10/16.
 *
 * @author ptasha
 */
public abstract class FileContext<B extends Reference> extends Context<B> implements Reference<B> {
    private final String name;

    public FileContext(Context<B> parent, String name, FileContextResolver<B, FileContext<B>> fileResolver) {
        this(parent, name, createResolvers(fileResolver));
    }

    protected FileContext(Context<B> parent, String name, List<ReferenceResolver<B, Context<B>>> resolvers) {
        super(parent, resolvers);
        this.name = name;
    }

    public static <B extends Reference> List<ReferenceResolver<B, Context<B>>> createResolvers(FileContextResolver<B, FileContext<B>> fileResolver) {
        ArrayList<ReferenceResolver<B, Context<B>>> resolvers = new ArrayList<>();
        resolvers.add(new FileResolver<>(fileResolver));
        resolvers.addAll(Context.createResolvers());
        resolvers.add(new ImportsResolver<>(fileResolver));
        return resolvers;
    }

    @Override
    public Context<B> getKingdom() {
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

    public abstract B getFile(); // TODO remove me

    public static class FileResolver<R extends Reference, C extends FileContext<R>> implements ReferenceResolver<R, Context<R>> {

        private final FileContextResolver<R, C> fileContextResolver;

        public FileResolver(FileContextResolver<R, C> fileContextResolver) {
            this.fileContextResolver = fileContextResolver;
        }

        @Override
        public R resolve(Context<R> context, Reference reference, boolean createAllowed) {
            if (reference.getReferableType()==ReferableType.FILE) {
                C fileContext = fileContextResolver.resolveFile(reference.getName());
                if (fileContext!=null) {
                    return fileContext.getFile();
                }
            }
            return null;
        }
    }

    public static class ImportsResolver<R extends Reference> implements ReferenceResolver<R, Context<R>> {

        private final FileContextResolver<R, FileContext<R>> fileContextResolver;

        public ImportsResolver(FileContextResolver<R, FileContext<R>> fileContextResolver) {
            this.fileContextResolver = fileContextResolver;
        }

        @Override
        public R resolve(Context<R> context, Reference reference, boolean createAllowed) {
            for (R run : context.getDeclarations(ReferableType.FILE).values()) {
                FileContext<R> dependency = fileContextResolver.resolveFile(run.getName());
                if (dependency != null) {
                    R resolved = dependency.findLocalDeclaration(reference);
                    if (resolved != null) {
                        return resolved;
                    }
                }
            }
            return null;
        }
    }
}
