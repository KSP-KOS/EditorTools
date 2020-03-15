package ksp.kos.ideaplugin.expressions.inline;

import ksp.kos.ideaplugin.expressions.Expression;

import java.util.HashMap;

/**
 * Created on 07/03/16.
 *
 * @author ptasha
 */
public class InlineFunction {
    private final String name;
    private final String[] argNames;
    private final Expression expression;

    public InlineFunction(String name, String[] argNames, Expression expression) {
        this.name = name;
        this.argNames = argNames;
        this.expression = expression;
    }

    public String getName() {
        return name;
    }

    public Expression inline(Expression... args) {
        HashMap<String, Expression> inlineArgs = new HashMap<>();
        for (int i = 0; i < Math.min(args.length, argNames.length); i++) {
            inlineArgs.put(argNames[i], args[i]);
        }
        return expression.inline(inlineArgs);
    }
}
