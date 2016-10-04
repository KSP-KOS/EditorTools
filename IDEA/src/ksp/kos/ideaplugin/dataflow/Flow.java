package ksp.kos.ideaplugin.dataflow;

import ksp.kos.ideaplugin.expressions.ExpressionVisitor;

/**
 * Created on 12/03/16.
 *
 * @author ptasha
 */
public interface Flow<F extends Flow<F>> extends Dependency {
    F differentiate(ContextBuilder context);

    F differentiate();

    String getText();

    boolean addContext(ContextBuilder context);

    void accept(ExpressionVisitor visitor);

    void removeDependee(Flow<?> flow);

    boolean hasDependees();
}
