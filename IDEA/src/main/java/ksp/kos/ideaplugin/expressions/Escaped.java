package ksp.kos.ideaplugin.expressions;


import ksp.kos.ideaplugin.reference.context.LocalContext;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created on 29/01/16.
 *
 * @author ptasha
 */
public class Escaped extends Atom {
    private final Expression expression;

    public Escaped(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String getText() {
        return "(" + expression.getText() + ")";
    }

    @Override
    public Expression differentiate(LocalContext context) {
        return expression.differentiate(context);
    }

    @Override
    public Expression inline(HashMap<String, Expression> args) {
        return expression.inline(args);
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public Expression simplify() {
        return expression.simplify();
    }

    @Override
    public Expression minus() {
        return expression.minus();
    }

    @Override
    public boolean isNegative() {
        return expression.isNegative();
    }

    @Override
    public boolean isAddition() {
        if (expression instanceof Addition) {
            return true;
        }
        return super.isAddition();
    }

    @Override
    public void acceptChildren(ExpressionVisitor visitor) {
        expression.accept(visitor);
    }

    public static Expression unescape(Expression expression) {
        if (expression instanceof Escaped) {
            return unescape(((Escaped) expression).getExpression());
        }
        return expression;
    }

    @Override
    public Expression normalize() {
        return expression.normalize();
    }

    @Override
    public Expression distribute() {
        return expression.distribute();
    }

    @Override
    public Expression distribute(Expression expression) {
        return this.expression.distribute(expression);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Escaped escaped = (Escaped) o;
        return Objects.equals(expression, escaped.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression);
    }
}
