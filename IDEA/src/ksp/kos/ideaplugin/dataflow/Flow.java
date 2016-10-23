package ksp.kos.ideaplugin.dataflow;

import ksp.kos.ideaplugin.expressions.ExpressionVisitor;
import ksp.kos.ideaplugin.reference.context.LocalContext;

/**
 * Created on 12/03/16.
 *
 * @author ptasha
 */
public interface Flow<F extends Flow<F>> extends Dependency {
    F differentiate(LocalContext context, ContextBuilder contextBuilder);

    F differentiate(LocalContext context);

    String getText();

    boolean addContext(ContextBuilder context);

    void accept(ExpressionVisitor visitor);

    void removeDependee(Flow<?> flow);

    boolean hasDependees();
}
