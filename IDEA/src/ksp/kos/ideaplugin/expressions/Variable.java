package ksp.kos.ideaplugin.expressions;

import com.intellij.util.containers.HashMap;

/**
 * Created on 29/01/16.
 *
 * @author ptasha
 */
public class Variable extends Atom {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public String getText() {
        return name;
    }

    @Override
    public Expression differentiate() {
        return new Variable(name+"_");
    }

    @Override
    public Expression inline(HashMap<String, Expression> args) {
        Expression expression = args.get(name);
        if (expression!=null) {
            return expression;
        }
        return this;
    }
}
