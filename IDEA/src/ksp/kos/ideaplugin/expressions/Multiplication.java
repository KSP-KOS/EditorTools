package ksp.kos.ideaplugin.expressions;

import ksp.kos.ideaplugin.psi.KerboScriptExpr;
import ksp.kos.ideaplugin.psi.KerboScriptMultdivExpr;
import org.jetbrains.annotations.NotNull;

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

    public Multiplication(Expression... expressions) {
        super(expressions);
    }

    public Multiplication(KerboScriptMultdivExpr muldiv) throws SyntaxException {
        super(muldiv);
    }

    @NotNull
    @Override
    protected Op[] operators() {
        return Op.values();
    }

    @Override
    protected Expression singleItemExpression(Item<Op, Element> item) {
        if (item.getOperation()==Op.MUL) {
            return item.getExpression();
        } else {
            items.add(0, new Item<>(Op.MUL, toElement(Number.ONE)));
            return this;
        }
    }

    @Override
    protected Element parseElement(KerboScriptExpr element) throws SyntaxException {
        return Element.parse(element);
    }

    @Override
    protected Element toElement(Expression expression) {
        return Element.toElement(expression);
    }

    @Override
    public Expression differentiate() {
        Expression diff = Number.ZERO;
        for (Item<Op, Element> item : items) {
            Expression id = Number.ONE;
            for (Item<Op, Element> ditem : items) {
                if (item!=ditem) {
                    if (item.getOperation()==Op.MUL) {
                        id = id.multiply(ditem.getExpression().copy());
                    } else {
                        id = id.divide(ditem.getExpression().copy());
                    }
                } else {
                    if (item.getOperation()==Op.MUL) {
                        id = id.multiply(ditem.getExpression().copy().differentiate());
                    } else {
                        id = id.multiply(ditem.getExpression().copy().power(Number.create(-1)).differentiate());
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

    @Override
    protected Expression zero() {
        return Number.ZERO;
    }

    @Override
    protected Expression one() {
        return Number.ONE;
    }
}
