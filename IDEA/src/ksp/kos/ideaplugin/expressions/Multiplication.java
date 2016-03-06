package ksp.kos.ideaplugin.expressions;

import ksp.kos.ideaplugin.psi.KerboScriptMultdivExpr;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created on 30/01/16.
 *
 * @author ptasha
 */
public class Multiplication extends MultiExpression<Multiplication.Op, Element> {
    @SuppressWarnings("unused")
    public Multiplication() {
        super();
    }

    protected Multiplication(List<Item<Op, Element>> items) {
        super(items);
    }

    public Multiplication(Expression... expressions) {
        super(expressions);
    }

    public Multiplication(KerboScriptMultdivExpr muldiv) throws SyntaxException {
        super(muldiv);
    }

    @Override
    public MultiExpressionBuilder<Op, Element> createBuilder() {
        return new MultiExpressionBuilder<Op, Element>(Multiplication.class) {
            @Override
            protected Expression zero() {
                return Number.ZERO;
            }

            @Override
            protected Expression one() {
                return Number.ONE;
            }

            @Override
            protected Expression singleItemExpression(Item<Op, Element> item) {
                if (item.getOperation()==Op.MUL) {
                    Element expression = item.getExpression();
                    if (expression.isSimple()) {
                        return expression.getAtom();
                    }
                    return expression;
                } else {
                    items.add(0, new Item<>(Op.MUL, toElement(Number.ONE)));
                    return createExpression();
                }
            }

            @NotNull
            @Override
            protected Op[] operators() {
                return Op.values();
            }

            @Override
            protected Element toElement(Expression expression) {
                return Element.toElement(expression);
            }

            @Override
            protected void normalize() {
                LinkedList<Item<Op, Element>> items = new LinkedList<>();
                for (Iterator<Item<Op, Element>> iterator = this.items.iterator(); iterator.hasNext(); ) {
                    Item<Op, Element> item = iterator.next();
                    if (item.getOperation() == Op.MUL) {
                        items.add(item);
                        iterator.remove();
                    }
                }
                if (items.isEmpty()) {
                    items.add(new Item<>(Op.MUL, toElement(Number.ONE)));
                }
                items.addAll(this.items);
                this.items.clear();
                this.items.addAll(items);
            }

            @Override
            protected MultiExpression<Op, Element> createExpression(List<Item<Op, Element>> items) {
                return new Multiplication(items);
            }
        };
    }

    @Override
    public Expression differentiate() {
        Expression diff = Number.ZERO;
        for (Item<Op, Element> item : items) {
            Expression id = Number.ONE;
            for (Item<Op, Element> ditem : items) {
                if (item!=ditem) {
                    if (item.getOperation()==Op.MUL) {
                        id = id.multiply(ditem.getExpression());
                    } else {
                        id = id.divide(ditem.getExpression());
                    }
                } else {
                    if (item.getOperation()==Op.MUL) {
                        id = id.multiply(ditem.getExpression().differentiate());
                    } else {
                        id = id.multiply(ditem.getExpression().power(Number.create(-1)).differentiate());
                    }
                }
            }
            diff = diff.plus(id);
        }
        return diff;
    }

    @Override
    public Expression multiply(Expression expression) {
        return addExpression(expression);
    }

    @Override
    public Expression multiplyLeft(Expression expression) {
        return addExpressionLeft(expression);
    }

    @Override
    public Expression divide(Expression expression) {
        return addExpression(Op.DIV, expression);
    }

    public enum Op implements MultiExpression.Op {
        MUL("*", Expression::multiply),
        DIV("/", Expression::divide);

        private final String text;
        private final MultiExpression.Operation operation;

        Op(String text, Operation operation) {
            this.text = text;
            this.operation = operation;
        }

        @Override
        public String getText() {
            return text;
        }

        public String toString() {
            return text;
        }

        @Override
        public Expression apply(Expression exp1, Expression exp2) {
            return operation.apply(exp1, exp2);
        }
    }
}
