package ksp.kos.ideaplugin.expressions;

import ksp.kos.ideaplugin.psi.KerboScriptExpr;

import java.util.List;

/**
 * Created on 30/01/16.
 *
 * @author ptasha
 */
public class Function extends Atom {
    private final String name;
    private final Expression[] args;

    public Function(String name, Expression... args) {
        this.name = name;
        this.args = args;
    }

    public Function(String name, List<KerboScriptExpr> exprList) throws SyntaxException {
        this.name = name;
        this.args = new Expression[exprList.size()];
        for (int i = 0; i < exprList.size(); i++) {
            KerboScriptExpr expr = exprList.get(i);
            args[i] = Expression.parse(expr);
        }
    }

    @Override
    public String getText() {
        String text = this.name + "(";
        boolean first = true;
        for (Expression arg : args) {
            if (first) {
                first = false;
            } else {
                text+=", ";
            }
            text += arg.getText();
        }
        return text+")";
    }

    @Override
    public Expression differentiate() {
        if (args.length==0) {
            return Number.ZERO;
        }
        if (args.length==1) {
            return new Function(name+"_", args).multiply(args[0].differentiate());
        }
        Expression diff = Number.ZERO;
        for (int i = 0; i < args.length; i++) {
            Expression ad = new Function(name + "_" + (i + 1), args).multiply(args[i].differentiate());
            diff = diff.plus(ad);
        }
        return diff;
    }

    @Override
    public Expression simplify() {
        Expression[] args = new Expression[this.args.length];
        for (int i = 0; i < this.args.length; i++) {
            args[i] = this.args[i].simplify();
        }
        return new Function(name, args);
    }

    public static Expression log(Expression arg) {
        return new Function("log", arg);
    }
}
