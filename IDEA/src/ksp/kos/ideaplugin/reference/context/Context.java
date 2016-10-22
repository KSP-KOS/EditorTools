package ksp.kos.ideaplugin.reference.context;

import ksp.kos.ideaplugin.reference.ReferableType;
import ksp.kos.ideaplugin.reference.Reference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Created on 04/10/16.
 *
 * @author ptasha
 */
public class Context<B extends Reference> {
    protected final Context<B> parent;
    private final Map<ReferableType, Map<String, B>> declarations = new HashMap<>();

    private final List<ReferenceResolver<B, Context<B>>> resolvers;

    public Context(Context<B> parent) {
        this(parent, createResolvers());
    }

    protected Context(Context<B> parent, List<ReferenceResolver<B, Context<B>>> resolvers) {
        this.parent = parent;
        this.resolvers = resolvers;
    }

    public static <B extends Reference> List<ReferenceResolver<B, Context<B>>> createResolvers() {
        List<ReferenceResolver<B, Context<B>>> resolvers = new ArrayList<>();
        resolvers.add(new LocalResolver<B>());
        resolvers.add(new ParentResolver<B>());
        return resolvers;
    }

    public Context<B> getParent() {
        return parent;
    }

    @SuppressWarnings("unchecked")
    @NotNull
    public Map<String, B> getFunctions() {
        return getDeclarations(ReferableType.FUNCTION);
    }

    public B findDeclaration(Reference reference) {
        return resolve(reference, false);
    }

    @Nullable
    public B findLocalDeclaration(Reference reference) {
        B declaration = getDeclarations(reference.getReferableType()).get(reference.getName());
        if (declaration!=null && reference.matches(declaration)) {
            return declaration;
        }
        return null;
    }

    public B resolve(Reference reference) {
        return resolve(reference, true);
    }

    protected B resolve(Reference reference, boolean createAllowed) {
        for (ReferenceResolver<B, Context<B>> resolver : resolvers) {
            B resolved = resolver.resolve(this, reference, createAllowed);
            if (resolved!=null) {
                return resolved;
            }
        }
        return null;
    }

    public void clear() {
        declarations.clear();
    }

    public void register(B element) {
        String name = element.getName();
        if (name!=null) {
            ReferableType type = element.getReferableType();
            switch (type) {
                case FUNCTION:
                case VARIABLE:
                    addDefinition(type, name, element);
                    break;
                default:
                    registerUnknown(type, name, element);
            }
        }
    }

    protected void registerUnknown(ReferableType type, String name, B element) {
    }

    protected void addDefinition(ReferableType type, String name, B element) {
        getDeclarations(type).put(name, element);
    }

    @SuppressWarnings("unchecked")
    @NotNull
    public Map<String, B> getDeclarations(ReferableType type) {
        Map<String, B> map = declarations.get(type);
        if (map==null) {
            map = createMap(type);
            declarations.put(type, map);
        }
        return map;
    }

    @NotNull
    public Map<String, B> createMap(ReferableType type) {
        return new ScopeMap<>();
    }

    public static class ScopeMap<T> extends LinkedHashMap<String, T> {
        @Override
        public T put(String key, T value) {
            if (!containsKey(key)) {
                super.put(key.toLowerCase(), value);
            }
            return null;
        }

        @Override
        public T get(Object key) {
            return get((String)key);
        }

        public T get(String key) {
            return super.get(key.toLowerCase());
        }
    }

    /**
     * Created on 10/10/16.
     *
     * @author ptasha
     */
    public static class LocalResolver<B extends Reference> implements ReferenceResolver<B, Context<B>> {
        @Override
        public B resolve(Context<B> context, Reference reference, boolean createAllowed) {
            return context.findLocalDeclaration(reference);
        }
    }

    /**
     * Created on 10/10/16.
     *
     * @author ptasha
     */
    public static class ParentResolver<B extends Reference> implements ReferenceResolver<B, Context<B>> {
        @Override
        public B resolve(Context<B> context, Reference reference, boolean createAllowed) {
            Context<B> parent = context.getParent();
            if (parent!=null) {
                return parent.resolve(reference, createAllowed);
            }
            return null;
        }
    }
}
