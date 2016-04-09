package ksp.kos.ideaplugin.dataflow;

import ksp.kos.ideaplugin.expressions.Expression;
import ksp.kos.ideaplugin.expressions.Number;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Set;

/**
 * Created on 12/03/16.
 *
 * @author ptasha
 */
public abstract class ExpressionFlow<F extends ExpressionFlow<F>> extends BaseFlow<F> {
    private final Expression expression;

    public ExpressionFlow(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @SuppressWarnings("unchecked")
    @Override
    public F addContext(HashMap<String, NamedFlow<?>> context) {
        HashMap<String, Expression> inline = new HashMap<>();
        Set<String> names = expression.getVariableNames();
        for (String name : names) {
            NamedFlow<?> flow = context.get(name);
            if (flow != null) {
                if (flow instanceof VariableFlow && ((VariableFlow) flow).isSimple()) {
                    inline.put(name, ((VariableFlow) flow).getExpression());
                } else {
                    flow.addDependee(this);
                }
            }
        }
        if (!inline.isEmpty()) {
            return create(expression.inline(inline));
        }
        return (F) this;
    }

    public boolean isSimple() {
        return expression.equals(Number.ZERO);
    }

    @Override
    public F differentiate() {
        return create(expression.differentiate());
    }

    @NotNull
    protected abstract F create(Expression diff);
}
