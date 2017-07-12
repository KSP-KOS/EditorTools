package ksp.kos.ideaplugin.dataflow;

import java.util.HashMap;

/**
 * Created on 12/03/16.
 *
 * @author ptasha
 */
public interface Flow<F extends Flow<F>> {
    F differentiate();

    String getText();

    void addDependee(Flow<?> flow);

    void removeDependee(Flow<?> flow);

    void addContext(HashMap<String, NamedFlow<?>> context);
}
