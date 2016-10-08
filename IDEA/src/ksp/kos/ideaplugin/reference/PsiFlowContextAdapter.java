package ksp.kos.ideaplugin.reference;

import ksp.kos.ideaplugin.KerboScriptFile;
import ksp.kos.ideaplugin.dataflow.FunctionFlow;
import ksp.kos.ideaplugin.dataflow.ReferenceFlow;
import ksp.kos.ideaplugin.expressions.SyntaxException;
import ksp.kos.ideaplugin.psi.KerboScriptDeclareFunctionClause;
import ksp.kos.ideaplugin.psi.KerboScriptNamedElement;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

/**
 * Created on 07/10/16.
 *
 * @author ptasha
 */
public class PsiFlowContextAdapter extends Context<ReferenceFlow> {
    private final Context<KerboScriptNamedElement> context;

    public PsiFlowContextAdapter(Context<KerboScriptNamedElement> context) {
        super(createParentContext(context.getParent()));
        this.context = context;
    }

    private static Context<ReferenceFlow> createParentContext(Context<KerboScriptNamedElement> parent) {
        if (parent==null) {
            return null;
        } else {
            return new PsiFlowContextAdapter(parent);
        }
    }

    @Override
    public KerboScriptFile getKerboScriptFile() {
        return context.getKerboScriptFile();
    }

    @Override
    public void clear() {
        super.clear();
        context.clear();
    }

    @Override
    protected ReferenceFlow resolve(Reference reference, BiFunction<Context<ReferenceFlow>, Reference, ReferenceFlow> function) {
        ReferenceFlow flow = super.resolve(reference, function);
        if (flow==null) {
            KerboScriptNamedElement psi = context.findDeclaration(reference);
            // TODO it's not fair, function may be from another context
            if (psi instanceof KerboScriptDeclareFunctionClause) {
                try {
                    flow = FunctionFlow.parse((KerboScriptDeclareFunctionClause) psi);
                    register(flow);
                    return flow;
                } catch (SyntaxException e) {
                    e.printStackTrace(); // TODO
                    return null;
                }
            }
        }
        return flow;
    }

    @NotNull
    @Override
    public ScopeMap<ReferenceFlow> getDeclarations(ReferableType type) {
        return super.getDeclarations(type);
    }

    @NotNull
    @Override
    protected ScopeMap<ReferenceFlow> createMap() {
        return new AdapterMap(context.createMap());
    }

    protected static class AdapterMap extends ScopeMap<ReferenceFlow> {
        private final ScopeMap<KerboScriptNamedElement> map;

        private AdapterMap(ScopeMap<KerboScriptNamedElement> map) {
            this.map = map;
        }

        @Override
        public ReferenceFlow get(String key) {
            ReferenceFlow flow = super.get(key);
            if (flow==null) {
                KerboScriptNamedElement psiElement = map.get(key);
                // TODO make it for any flow?
                if (psiElement instanceof KerboScriptDeclareFunctionClause) {
                    try {
                        FunctionFlow function = FunctionFlow.parse((KerboScriptDeclareFunctionClause) psiElement);
                        put(key, function);
                        return function;
                    } catch (SyntaxException e) {
                        e.printStackTrace(); // TODO
                        return null;
                    }
                }
            }
            return flow;
        }
    }
}
