package ksp.kos.ideaplugin.expressions;

/**
 * Created on 29/01/16.
 *
 * @author ptasha
 */
public class Escaped extends Atom {
    private Expression expression;

    public Escaped(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String getText() {
        return "("+expression.getText()+")";
    }

    @Override
    public Expression differentiate() {
        return expression.differentiate();
    }

    @Override
    public Expression copy() {
        return new Escaped(expression.copy());
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public Expression simplify() {
        expression = expression.simplify();
        return expression;
    }
}
