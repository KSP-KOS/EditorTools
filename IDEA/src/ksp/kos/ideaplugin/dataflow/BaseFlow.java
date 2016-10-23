package ksp.kos.ideaplugin.dataflow;

import ksp.kos.ideaplugin.expressions.ExpressionVisitor;
import ksp.kos.ideaplugin.reference.context.LocalContext;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 18/03/16.
 *
 * @author ptasha
 */
public abstract class BaseFlow<F extends BaseFlow<F>> implements Flow<F> {
    private final Set<Flow<?>> dependees = new HashSet<>();

    @Override
    public void addDependee(Flow<?> flow) {
        dependees.add(flow);
    }

    @Override
    public void removeDependee(Flow<?> flow) {
        dependees.remove(flow);
    }

    @Override
    public boolean hasDependees() {
        return !dependees.isEmpty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean addContext(ContextBuilder context) {
        return true;
    }

    @Override
    public F differentiate(LocalContext context, ContextBuilder contextBuilder) {
        return differentiate(context);
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
    }
}
