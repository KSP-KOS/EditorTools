package ksp.kos.ideaplugin.reference;


import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;

import java.util.HashMap;

/**
 * Created on 03/01/16.
 *
 * @author ptasha
 */
public class LocalScope {
    private final ScopeMap variables = new ScopeMap();
    private final ScopeMap functions = new ScopeMap();

    public KerboScriptNamedElement resolveVariable(KerboScriptNamedElement element) {
        KerboScriptNamedElement resolved = variables.get(element.getName());
        if (resolved!=null && resolved.getTextOffset()>element.getTextOffset()) {
            return null;
        }
        return resolved;
    }

    public KerboScriptNamedElement resolveFunction(KerboScriptNamedElement element) {
        return functions.get(element.getName());
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

    public static class ScopeMap extends HashMap<String, KerboScriptNamedElement> {
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
