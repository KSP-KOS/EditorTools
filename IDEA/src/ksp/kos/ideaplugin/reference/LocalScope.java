package ksp.kos.ideaplugin.reference;


import ksp.kos.ideaplugin.psi.KerboScriptBase;
import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Created on 03/01/16.
 *
 * @author ptasha
 */
public class LocalScope {
    private final LocalScope parent;
    private final Map<ReferableType, ScopeMap> declarations = new HashMap<>();

    public LocalScope(LocalScope parent) {
        this.parent = parent;
    }

    public LocalScope getParent() {
        return parent;
    }

    @NotNull
    public ScopeMap getFunctions() {
        return getDeclarations(ReferableType.FUNCTION);
    }

    public KerboScriptNamedElement findDeclaration(Reference reference) {
        return resolve(reference, LocalScope::findDeclaration);
    }

    @Nullable
    public KerboScriptNamedElement findLocalDeclaration(Reference reference) {
        KerboScriptNamedElement declaration = getDeclarations(reference.getReferableType()).get(reference.getName());
        if (declaration!=null && reference instanceof KerboScriptBase) {
            KerboScriptBase element = (KerboScriptBase) reference;
            if (declaration.getTextOffset() > element.getTextOffset() &&
                    element.getScope().getCachedScope() == this) {
                return null;
            }
        }
        return declaration;
    }

    public KerboScriptNamedElement resolve(Reference reference) {
        return resolve(reference, LocalScope::resolve);
    }

    protected KerboScriptNamedElement resolve(Reference reference, BiFunction<LocalScope, Reference, KerboScriptNamedElement> function) {
        KerboScriptNamedElement declaration = findLocalDeclaration(reference);
        if (declaration==null && parent!=null) {
            return function.apply(parent, reference);
        }
        return declaration;
    }

    public void clear() {
        declarations.clear();
    }

    public void register(KerboScriptNamedElement element) {
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

    protected void registerUnknown(ReferableType type, String name, KerboScriptNamedElement element) {
    }

    protected void addDefinition(ReferableType type, String name, KerboScriptNamedElement element) {
        getDeclarations(type).put(name, element);
    }

    @NotNull
    public ScopeMap getDeclarations(ReferableType type) {
        ScopeMap map = declarations.get(type);
        if (map==null) {
            map = new ScopeMap();
            declarations.put(type, map);
        }
        return map;
    }

    public static class ScopeMap extends LinkedHashMap<String, KerboScriptNamedElement> {
        @Override
        public KerboScriptNamedElement put(String key, KerboScriptNamedElement value) {
            if (!containsKey(key)) {
                super.put(key.toLowerCase(), value);
            }
            return null;
        }

        @Override
        public KerboScriptNamedElement get(Object key) {
            return super.get(key.toString().toLowerCase());
        }
    }
}
