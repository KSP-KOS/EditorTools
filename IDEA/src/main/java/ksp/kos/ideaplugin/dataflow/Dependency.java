package ksp.kos.ideaplugin.dataflow;

/**
 * Created on 30/08/16.
 *
 * @author ptasha
 */
public interface Dependency {
    void addDependee(Flow<?> flow);
}
