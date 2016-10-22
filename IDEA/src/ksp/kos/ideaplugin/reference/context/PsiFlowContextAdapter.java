package ksp.kos.ideaplugin.reference.context;

import ksp.kos.ideaplugin.dataflow.FunctionFlow;
import ksp.kos.ideaplugin.dataflow.ReferenceFlow;
import ksp.kos.ideaplugin.expressions.SyntaxException;
import ksp.kos.ideaplugin.psi.KerboScriptDeclareFunctionClause;
import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
import ksp.kos.ideaplugin.reference.ReferableType;
import ksp.kos.ideaplugin.reference.Reference;
import ksp.kos.utils.TrueAbstractMap;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Created on 07/10/16.
 *
 * @author ptasha
 */
public class PsiFlowContextAdapter extends Context<ReferenceFlow> {
    private final Context<KerboScriptNamedElement> context;

    public PsiFlowContextAdapter(Context<KerboScriptNamedElement> context) {
        super(null, createResolvers(context));
        this.context = context;
    }

    public static List<ReferenceResolver<ReferenceFlow, Context<ReferenceFlow>>> createResolvers(Context<KerboScriptNamedElement> context) {
        ArrayList<ReferenceResolver<ReferenceFlow, Context<ReferenceFlow>>> resolvers = new ArrayList<>();
        resolvers.addAll(Context.createResolvers());
        resolvers.add(new TransformResolver(context));
        return resolvers;
    }

    @NotNull
    @Override
    public Map<String, ReferenceFlow> createMap(ReferableType type) {
        return new AdapterMap(context.createMap(type));
    }

    public static class AdapterMap extends TrueAbstractMap<String, ReferenceFlow> {
        private final Map<String, KerboScriptNamedElement> map;
        private final HashMap<String, ReferenceFlow> cache = new HashMap<>();

        public AdapterMap(Map<String, KerboScriptNamedElement> map) {
            this.map = map;
        }

        @Override
        public ReferenceFlow get(Object key) {
            ReferenceFlow flow = cache.get(key);
            if (flow==null) {
                flow = transform(map.get(key));
                if (flow!=null) {
                    cache.put((String) key, flow);
                }
            }
            return flow;
        }

        @NotNull
        @Override
        public Set<Entry<String, ReferenceFlow>> entrySet() {
            return new AbstractSet<Entry<String, ReferenceFlow>>() {
                @NotNull
                @Override
                public Iterator<Entry<String, ReferenceFlow>> iterator() {
                    return new Iterator<Entry<String, ReferenceFlow>>() {
                        private Iterator<Entry<String, KerboScriptNamedElement>> iterator = map.entrySet().iterator();

                        @Override
                        public boolean hasNext() {
                            return iterator.hasNext();
                        }

                        @Override
                        public Entry<String, ReferenceFlow> next() {
                            Entry<String, KerboScriptNamedElement> entry = iterator.next();
                            return new SimpleEntry<>(entry.getKey(), get(entry.getKey()));
                        }
                    };
                }

                @Override
                public int size() {
                    return map.size();
                }
            };
        }
    }

    public static FunctionFlow transform(KerboScriptNamedElement psi) {
        if (psi instanceof KerboScriptDeclareFunctionClause) {
            try {
                return FunctionFlow.parse((KerboScriptDeclareFunctionClause) psi);
            } catch (SyntaxException e) {
                e.printStackTrace(); // TODO
            }
        }
        return null;
    }

    public static class TransformResolver implements ReferenceResolver<ReferenceFlow, Context<ReferenceFlow>> {
        private final Context<KerboScriptNamedElement> context;

        public TransformResolver(Context<KerboScriptNamedElement> context) {
            this.context = context;
        }

        @Override
        public ReferenceFlow resolve(Context<ReferenceFlow> context, Reference reference, boolean createAllowed) {
            return transform(this.context.resolve(reference, createAllowed));
        }
    }
}
