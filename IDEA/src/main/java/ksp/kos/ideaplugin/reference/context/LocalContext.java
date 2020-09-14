package ksp.kos.ideaplugin.reference.context;

import ksp.kos.ideaplugin.dataflow.ReferenceFlow;
import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
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
public class LocalContext {
    protected final LocalContext parent;
    private final Map<ReferableType, Map<String, Duality>> declarations = new HashMap<>();

    private final List<ReferenceResolver<LocalContext>> resolvers;

    public LocalContext(LocalContext parent) {
        this(parent, createResolvers());
    }

    protected LocalContext(LocalContext parent, List<ReferenceResolver<LocalContext>> resolvers) {
        this.parent = parent;
        this.resolvers = resolvers;
    }

    public static List<ReferenceResolver<LocalContext>> createResolvers() {
        List<ReferenceResolver<LocalContext>> resolvers = new ArrayList<>();
        resolvers.add(new LocalResolver());
        resolvers.add(new ParentResolver());
        return resolvers;
    }

    public LocalContext getParent() {
        return parent;
    }

    @SuppressWarnings("unchecked")
    @NotNull
    public Map<String, Duality> getFunctions() {
        return getDeclarations(ReferableType.FUNCTION);
    }

    public <K extends KerboScriptNamedElement, F extends ReferenceFlow> Duality<K, F> findDeclaration(Reference reference) {
        return resolve(reference, false);
    }

    @Nullable
    public Duality findLocalDeclaration(Reference reference) {
        Duality declaration = getDeclarations(reference.getReferableType()).get(reference.getName());
        if (declaration!=null && reference.matches(declaration)) {
            return declaration;
        }
        return null;
    }

    public <K extends KerboScriptNamedElement, F extends ReferenceFlow> Duality<K, F> resolve(Reference reference) {
        return resolve(reference, true);
    }

    protected Duality resolve(Reference reference, boolean createAllowed) {
        for (ReferenceResolver<LocalContext> resolver : resolvers) {
            Duality resolved = resolver.resolve(this, reference, createAllowed);
            if (resolved!=null) {
                return resolved;
            }
        }
        return null;
    }

    public void clear() {
        declarations.clear();
    }

    public void register(Duality element) {
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

    public void register(KerboScriptNamedElement psi) {
        register(new PsiDuality(psi));
    }

    protected void registerUnknown(ReferableType type, String name, Duality element) {
    }

    protected void addDefinition(ReferableType type, String name, Duality element) {
        getDeclarations(type).put(name, element);
    }

    @SuppressWarnings("unchecked")
    @NotNull
    public Map<String, Duality> getDeclarations(ReferableType type) {
        Map<String, Duality> map = declarations.get(type);
        if (map==null) {
            map = createMap(type);
            declarations.put(type, map);
        }
        return map;
    }

    @NotNull
    public Map<String, Duality> createMap(ReferableType type) {
        return new ScopeMap<>();
    }

    public FileContext getFileContext() {
        if (this instanceof FileContext) {
            return (FileContext) this;
        } else if (parent!=null) {
            return parent.getFileContext();
        }
        return null;
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
    public static class LocalResolver implements ReferenceResolver<LocalContext> {
        @Override
        public Duality resolve(LocalContext context, Reference reference, boolean createAllowed) {
            return context.findLocalDeclaration(reference);
        }
    }

    /**
     * Created on 10/10/16.
     *
     * @author ptasha
     */
    public static class ParentResolver implements ReferenceResolver<LocalContext> {
        @Override
        public Duality resolve(LocalContext context, Reference reference, boolean createAllowed) {
            LocalContext parent = context.getParent();
            if (parent!=null) {
                return parent.resolve(reference, createAllowed);
            }
            return null;
        }
    }
}
