package ksp.kos.ideaplugin.expressions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    @Override
    public Set<String> getVariableNames() {
        HashSet<String> name = new HashSet<>();
        name.add(this.name);
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return Objects.equals(name, variable.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
