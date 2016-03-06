package ksp.kos.ideaplugin.expressions;

import ksp.kos.ideaplugin.psi.KerboScriptArithExpr;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created on 31/01/16.
 *
 * @author ptasha
 */
public class Addition extends MultiExpression<Addition.Op,Expression> {
    @SuppressWarnings("unused")
    public Addition() {
        super();
    }

    protected Addition(List<Item<Op, Expression>> items) {
        super(items);
    }

    public Addition(Expression... expressions) {
        super(expressions);
    }

    public Addition(KerboScriptArithExpr arith) throws SyntaxException {
        super(arith);
    }

    @Override
    public MultiExpressionBuilder<Op, Expression> createBuilder() {
        return new MultiExpressionBuilder<Op, Expression>(Addition.class) {
            @Override
            protected Expression one() {
                return Number.ZERO;
            }

            @Override
            protected Expression singleItemExpression(Item<Op, Expression> item) {
                if (item.getOperation()==Op.PLUS) {
                    return item.getExpression();
                } else {
                    return item.getExpression().minus();
                }
            }

            @NotNull
            @Override
            protected Op[] operators() {
                return Op.values();
            }

            @Override
            protected MultiExpression<Op, Expression> createExpression(List<Item<Op, Expression>> items) {
                return new Addition(items);
            }
        };
    }

    @Override
    public Expression differentiate() {
        MultiExpressionBuilder<Op, Expression> builder = createBuilder();
        for (Item<Op, Expression> item : items) {
            builder.addExpression(item.getOperation(), item.getExpression().differentiate());
        }
        return builder.createExpression();
    }

    @Override
    public Expression plus(Expression expression) {
        return addExpression(expression);
    }

    @Override
    public Expression minus(Expression expression) {
        return addExpression(Op.MINUS, expression);
    }

    public enum Op implements MultiExpression.Op {
        PLUS("+", Expression::plus),
        MINUS("-", Expression::minus);

        private final String text;
        private final MultiExpression.Operation operation;

        Op(String text, MultiExpression.Operation operation) {
            this.text = text;
            this.operation = operation;
        }

        @Override
        public String getText() {
            return text;
        }

        public String toString() {
            return " "+text+" ";
        }

        @Override
        public Expression apply(Expression exp1, Expression exp2) {
            return operation.apply(exp1, exp2);
        }
    }
}
