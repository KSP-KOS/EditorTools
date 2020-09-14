package ksp.kos.ideaplugin.expressions;

import ksp.kos.ideaplugin.psi.KerboScriptArithExpr;
import ksp.kos.ideaplugin.reference.context.LocalContext;
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
        return new AdditionBuilder();
    }

    @Override
    public Expression differentiate(LocalContext context) {
        MultiExpressionBuilder<Op, Expression> builder = createBuilder();
        for (Item<Op, Expression> item : items) {
            builder.addExpression(item.getOperation(), item.getExpression().differentiate(context));
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

    @Override
    public boolean canMultiply(Expression expression) {
        return false;
    }

    public Expression multiplyItems(Expression expression) {
        MultiExpressionBuilder<Op, Expression> builder = createBuilder();
        for (Item<Op, Expression> item : items) {
            builder.addExpression(item.getOperation(), item.getExpression().multiply(expression));
        }
        return builder.createExpression();
    }

    @Override
    public Expression normalize() {
        Expression expression = this.distribute();
        if (expression instanceof Addition) {
            return ((Addition) expression).extractRoot();
        }
        return expression;
    }

    public Expression extractRoot() {
        Expression root = null;
        AdditionBuilder builder = new AdditionBuilder();
        for (Item<Op, Expression> item : items) {
            Expression expression = item.getExpression();
            if (root==null) {
                root = item.getExpression();
            } else {
                root = root.root(item.getExpression());
            }
            builder.addExpression(item.getOperation(), expression);
        }
        Expression expression = builder.createExpression();
        if (root.isNegative()) {
            root = root.minus();
        }
        if (!root.equals(Number.ONE)) {
            return expression.distribute(Multiplication.Op.DIV, root).multiply(root);
        } else {
            return expression;
        }
    }

    @Override
    public Expression distribute() {
        AdditionBuilder builder = new AdditionBuilder();
        for (Item<Op, Expression> item : items) {
            builder.addExpression(item.getOperation(), item.getExpression().distribute());
        }
        return builder.createExpression();
    }

    @Override
    public Expression distribute(Expression expression) {
        return distribute(Multiplication.Op.MUL, expression);
    }

    @Override
    public Expression distribute(Multiplication.Op operation, Expression expression) {
        AdditionBuilder builder = new AdditionBuilder();
        for (Item<Op, Expression> item : items) {
            builder.addExpression(item.getOperation(), operation.apply(item.getExpression(), expression).distribute());
        }
        return builder.createExpression();
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

    private static class AdditionBuilder extends MultiExpressionBuilder<Op, Expression> implements ConsumeSupported<Op, Expression>{
        public AdditionBuilder() {
            super(Addition.class);
        }

        @Override
        protected Expression one() {
            return Number.ZERO;
        }

        @Override
        protected Expression singleItemExpression(Item<Op, Expression> item) {
            if (item.getOperation()== Op.PLUS) {
                return item.getExpression();
            } else {
                return item.getExpression().minus();
            }
        }

        @Override
        protected void addItem(Item<Op, Expression> newItem) {
            if (items.isEmpty()) {
                if (newItem.getOperation()== Op.MINUS) {
                    newItem = createItem(Op.PLUS, newItem.getExpression().minus());
                }
            } else if (newItem.getExpression().isNegative()) {
                newItem = createItem(merge(newItem.getOperation(), Op.MINUS), newItem.getExpression().minus());
            }
            super.addItem(newItem);
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

        @Override
        protected Item<Op, Expression> consumeItem(Item<Op, Expression> item, Item<Op, Expression> newItem) {
            return this.consume(item, newItem); // TODO multiply must be the same
        }

        @Override
        public Item<Op, Expression> consume(Item<Op, Expression> item, Item<Op, Expression> newItem) {
            Expression e1 = singleItemExpression(item);
            Expression e2 = singleItemExpression(newItem);
            Expression result = e1.simplePlus(e2);
            if (result!=null) {
                return createItem(Op.PLUS, result);
            }
            result = e2.simplePlus(e1);
            if (result!=null) {
                return createItem(Op.PLUS, result);
            }
            return null;
        }
    }
}
