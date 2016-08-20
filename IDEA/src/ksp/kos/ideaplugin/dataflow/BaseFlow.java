package ksp.kos.ideaplugin.dataflow;

import java.util.HashMap;
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
    public void addContext(HashMap<String, NamedFlow<?>> context) {
    }
}
