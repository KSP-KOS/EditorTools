package ksp.kos.ideaplugin.dataflow;

import java.util.ArrayList;

/**
 * Created on 30/08/16.
 *
 * @author ptasha
 */
public class MixedDependency implements Dependency {
    private final ArrayList<Dependency> dependencies = new ArrayList<>();

    public MixedDependency() {
    }

    public void addDependency(Dependency dependency) {
        dependencies.add(dependency);
    }

    @Override
    public void addDependee(Flow<?> flow) {
        for (Dependency dependency : dependencies) {
            dependency.addDependee(flow);
        }
    }

    public boolean isEmpty() {
        return dependencies.isEmpty();
    }

    public ReturnFlow getReturnFlow() {
        if (dependencies.size()==1) {
            Dependency dependency = dependencies.get(0);
            if (dependency instanceof ReturnFlow) {
                return (ReturnFlow) dependency;
            }
        }
        return null;
    }
}
