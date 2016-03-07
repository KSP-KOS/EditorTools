package ksp.kos.ideaplugin.expressions;

import com.intellij.util.containers.HashMap;

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
        return "("+expression.getText()+")";
    }

    @Override
    public Expression differentiate() {
        return expression.differentiate();
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
}
