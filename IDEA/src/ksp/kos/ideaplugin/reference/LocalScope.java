package ksp.kos.ideaplugin.reference;


import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;

/**
 * Created on 03/01/16.
 *
 * @author ptasha
 */
public class LocalScope {
    private final ScopeMap variables = new ScopeMap();
    private final ScopeMap functions = new ScopeMap();

    public KerboScriptNamedElement resolveVariable(KerboScriptNamedElement element) {
        KerboScriptNamedElement resolved = getVariable(element.getName());
        if (resolved!=null && resolved.getTextOffset()>element.getTextOffset()) {
            return null;
        }
        return resolved;
    }

    public KerboScriptNamedElement getVariable(String name) {
        return variables.get(name);
    }

    public KerboScriptNamedElement resolveFunction(KerboScriptNamedElement element) {
        return getFunction(element.getName());
    }

    public KerboScriptNamedElement getFunction(String name) {
        return functions.get(name);
    }

    @NotNull
    public ScopeMap getFunctions() {
        return functions;
    }

    public void clear() {
        functions.clear();
        variables.clear();
    }

    public void register(KerboScriptNamedElement element) {
        String name = element.getName();
        if (name!=null) {
            switch (element.getType().getType()) {
                case FUNCTION:
                    functions.put(name, element);
                    break;
                case VARIABLE:
                    variables.put(name, element);
                    break;
            }
        }
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
