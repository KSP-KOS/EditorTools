package ksp.kos.ideaplugin.expressions;

import ksp.kos.ideaplugin.psi.KerboScriptArithExpr;
import org.jetbrains.annotations.NotNull;

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

    public Addition(Expression... expressions) {
        super(expressions);
    }

    public Addition(KerboScriptArithExpr arith) throws SyntaxException {
        super(arith);
    }

    @NotNull
    @Override
    protected Op[] operators() {
        return Op.values();
    }

    @Override
    protected Expression singleItemExpression(Item<Op, Expression> item) {
        if (item.getOperation()==Op.PLUS) {
            return item.getExpression();
        } else {
            return item.getExpression().minus();
        }
    }

    @Override
    public Expression differentiate() {
        for (Item<Op, Expression> item : items) {
            item.setExpression(item.getExpression().differentiate());
        }
        return this;
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

    @Override
    protected Expression one() {
        return Number.ZERO;
    }
}
