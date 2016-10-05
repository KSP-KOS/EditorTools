package ksp.kos.ideaplugin.reference;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Created on 04/10/16.
 *
 * @author ptasha
 */
public class Context<B extends Reference> {
    protected final Context<B> parent;
    private final Map<ReferableType, ScopeMap> declarations = new HashMap<>();

    public Context(Context<B> parent) {
        this.parent = parent;
    }

    public Context<B> getParent() {
        return parent;
    }

    @SuppressWarnings("unchecked")
    @NotNull
    public ScopeMap<B> getFunctions() {
        return getDeclarations(ReferableType.FUNCTION);
    }

    public B findDeclaration(Reference reference) {
        return resolve(reference, Context<B>::findDeclaration);
    }

    @Nullable
    public B findLocalDeclaration(Reference reference) {
        return getDeclarations(reference.getReferableType()).get(reference.getName());
    }

    public B resolve(Reference reference) {
        return resolve(reference, Context<B>::resolve);
    }

    protected B resolve(Reference reference, BiFunction<Context<B>, Reference, B> function) {
        B declaration = findLocalDeclaration(reference);
        if (declaration==null && parent!=null) {
            return function.apply(parent, reference);
        }
        return declaration;
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
    public ScopeMap<B> getDeclarations(ReferableType type) {
        ScopeMap map = declarations.get(type);
        if (map==null) {
            map = new ScopeMap();
            declarations.put(type, map);
        }
        return map;
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
            return super.get(key.toString().toLowerCase());
        }
    }
}
