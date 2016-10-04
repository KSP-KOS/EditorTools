package ksp.kos.ideaplugin.dataflow;

import ksp.kos.ideaplugin.expressions.Expression;
import ksp.kos.ideaplugin.expressions.ExpressionVisitor;
import ksp.kos.ideaplugin.expressions.Number;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * Created on 12/03/16.
 *
 * @author ptasha
 */
public abstract class ExpressionFlow<F extends ExpressionFlow<F>> extends BaseFlow<F> {
    private Expression expression;

    public ExpressionFlow(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean addContext(ContextBuilder context) {
        HashMap<String, Expression> inline = new HashMap<>();
        for (String name : expression.getVariableNames()) {
            Dependency flow = context.getFlow(name);
            if (flow != null) {
                // TODO inline simple functions
                // TODO add ability to add user function to inline (InlineFunctions)
                // TODO add function to inline automatically
                if (flow instanceof VariableFlow && ((VariableFlow) flow).isSimple()) {
                    inline.put(name, ((VariableFlow) flow).getExpression());
                }
            } else if (name.endsWith("_")) {
                inline.put(name, Number.ZERO);
            }
        }
        if (!inline.isEmpty()) {
            expression = expression.inline(inline);
        }
        for (String name : expression.getVariableNames()) {
            Dependency flow = context.getFlow(name);
            if (flow != null) {
                flow.addDependee(this);
            }
        }
        return true;
    }

    public boolean isSimple() {
        return expression.equals(Number.ZERO) || expression.equals(Number.ONE);
    }

    @Override
    public F differentiate() {
        return create(expression.differentiate());
    }

    @NotNull
    protected abstract F create(Expression diff);

    @Override
    public void accept(ExpressionVisitor visitor) {
        getExpression().accept(visitor);
    }
}
