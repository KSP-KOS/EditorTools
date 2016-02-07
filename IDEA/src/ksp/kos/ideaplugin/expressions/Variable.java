package ksp.kos.ideaplugin.expressions;

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
    public Expression copy() {
        return new Variable(name);
    }
}
