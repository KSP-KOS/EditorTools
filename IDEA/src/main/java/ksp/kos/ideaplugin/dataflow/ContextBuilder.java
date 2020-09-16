package ksp.kos.ideaplugin.dataflow;

import ksp.kos.ideaplugin.expressions.ExpressionVisitor;
import ksp.kos.ideaplugin.reference.context.LocalContext;

import java.util.*;

/**
 * Created on 14/08/16.
 *
 * @author ptasha
 */
public class ContextBuilder { // TODO combine into diff context?
    private ContextBuilder parent;
    private final List<Flow<?>> list = new LinkedList<>();
    private final HashMap<String, Dependency> map = new HashMap<>();
    private final MixedDependency returnFlow = new MixedDependency();

    public ContextBuilder() {
        this(null);
    }

    public ContextBuilder(ContextBuilder parent) {
        this.parent = parent;
    }

    public void visit(ExpressionVisitor visitor) {
        for (Flow<?> flow : getList()) {
            flow.accept(visitor);
        }
    }

    public String getText() {
        String text = "";
        for (Flow<?> flow : getList()) {
            if (!text.isEmpty()) text += "\n";
            text += flow.getText();
        }
        return text;
    }

    public void differentiate(LocalContext context, ContextBuilder contextBuilder) {
        for (Flow<?> flow : list) {
            flow.differentiate(context, contextBuilder);
        }
    }

    public void simplify() {
        for (ListIterator<Flow<?>> iterator = list.listIterator(list.size()); iterator.hasPrevious(); ) {
            Flow<?> variable = iterator.previous();
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
        for (Flow<?> flow : list) {
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

    public MixedDependency getReturn() {
        return returnFlow;
    }

    public void addReturnFlow(Dependency returnFlow) {
        this.returnFlow.addDependency(returnFlow); // TODO optimize?
    }

    public List<Flow<?>> getList() {
        return list;
    }

    public void setParent(ContextBuilder parent) {
        this.parent = parent;
    }

    public void sort() {
        TreeSet<Flow<?>> sorted = new TreeSet<>((o1, o2) -> {
            if (o1 instanceof ReturnFlow) {
                return 1;
            } else if (o2 instanceof ReferenceFlow) {
                return -1;
            } else if (o1.isDependee(o2)) {
                return -1;
            } else if (o2.isDependee(o1)) {
                return 1;
            }
            if (o1 instanceof VariableFlow && o2 instanceof VariableFlow) {
                String n1 = ((VariableFlow) o1).getName();
                String n2 = ((VariableFlow) o2).getName();
                while (n1.endsWith("_")) {
                    if (!n2.endsWith("_")) {
                        return 1;
                    }
                    n1 = n1.substring(0, n1.length()-1);
                    n2 = n2.substring(0, n2.length()-1);
                }
                if (n2.endsWith("_")) {
                    return -1;
                }
            }
            return list.indexOf(o1) - list.indexOf(o2);
        });
        sorted.addAll(list);
        list.clear();
        list.addAll(sorted);
    }
}
