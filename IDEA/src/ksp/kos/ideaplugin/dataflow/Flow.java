package ksp.kos.ideaplugin.dataflow;

import java.util.HashMap;
import java.util.List;

/**
 * Created on 12/03/16.
 *
 * @author ptasha
 */
public interface Flow<F extends Flow<F>> {
    default void differentiate(HashMap<String, NamedFlow<?>> context, List<Flow> diffVariables) {
        Flow diff = differentiate();
        add(diffVariables, this, context);
        add(diffVariables, diff, context);
    }


    static void add(List<Flow> list, Flow<?> flow, HashMap<String, NamedFlow<?>> context) {
        list.add(flow);
        flow.addContext(context);
    }

    F differentiate();

    String getText();

    void addDependee(Flow<?> flow);

    void removeDependee(Flow<?> flow);

    boolean hasDependees();

    void addContext(HashMap<String, NamedFlow<?>> context);
}
