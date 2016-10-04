package ksp.kos.ideaplugin.dataflow;

import ksp.kos.ideaplugin.expressions.ExpressionVisitor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created on 14/08/16.
 *
 * @author ptasha
 */
public class ContextBuilder {
    private ContextBuilder parent;
    private final List<Flow> list = new LinkedList<>();
    private final HashMap<String, Dependency> map = new HashMap<>();
    private final MixedDependency returnFlow = new MixedDependency();

    public ContextBuilder() {
        this(null);
    }

    public ContextBuilder(ContextBuilder parent) {
        this.parent = parent;
    }

    public void visit(ExpressionVisitor visitor) {
        for (Flow flow : getList()) {
            flow.accept(visitor);
        }
    }

    public String getText() {
        String text = "";
        for (Flow flow : getList()) {
            if (!text.isEmpty()) text += "\n";
            text += flow.getText();
        }
        return text;
    }

    public void differentiate(ContextBuilder context) {
        for (Flow<?> flow : list) {
            flow.differentiate(context);
        }
    }

    public void simplify() {
        for (ListIterator<Flow> iterator = list.listIterator(list.size()); iterator.hasPrevious(); ) {
            Flow variable = iterator.previous();
            // TODO simplify
            if (!variable.hasDependees()) {
                iterator.remove();
            }
        }
    }

    public void add(Flow<?> flow) {
        list.add(flow);
    }

    public boolean buildMap() {
        for (Flow flow : list) {
            if (!flow.addContext(this)) {
                return false;
            }
        }
        return true;
    }

    public Dependency getFlow(String name) {
        Dependency flow = map.get(name);
        if (flow==null && parent!=null) {
            return parent.getFlow(name);
        }
        return flow;
    }

    public HashMap<String, Dependency> getMap() {
        return map;
    }

    public MixedDependency getReturnFlow() {
        return returnFlow;
    }

    public void addReturnFlow(Dependency returnFlow) {
        this.returnFlow.addDependency(returnFlow); // TODO optimize?
    }

    public List<Flow> getList() {
        return list;
    }

    public ContextBuilder getParent() {
        return parent;
    }

    public void setParent(ContextBuilder parent) {
        this.parent = parent;
    }
}
