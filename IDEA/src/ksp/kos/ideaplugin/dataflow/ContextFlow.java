package ksp.kos.ideaplugin.dataflow;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created on 14/08/16.
 *
 * @author ptasha
 */
public class ContextFlow {
    private final List<Flow> flows;

    public ContextFlow(List<Flow> flows) {
        this.flows = flows;
    }

    public ContextFlow differentiate() { // TODO add single parameter logic
        HashMap<String, NamedFlow<?>> context = new HashMap<>();
        List<Flow> diffVariables = new LinkedList<>();
        for (Flow<?> flow : flows) {
            flow.differentiate(context, diffVariables);
        }
        // TODO deal with return flow

        for (ListIterator<Flow> iterator = diffVariables.listIterator(diffVariables.size()); iterator.hasPrevious(); ) {
            Flow variable = iterator.previous();
            if (!variable.hasDependees()) {
                iterator.remove();
            }
        }
        /* TODO Uncomment me when functions can deal with it
        int i = 0;
        for (Iterator<ParameterFlow> iterator = diffFlows.iterator(); iterator.hasNext(); ) {
            ParameterFlow diffParameter = iterator.next();
            if (i >= parameters.size()) {
                if (!diffParameter.hasDependees()) {
                    iterator.remove();
                }
            }
            i++;
        }
        */

        return new ContextFlow(flows);
    }
}
