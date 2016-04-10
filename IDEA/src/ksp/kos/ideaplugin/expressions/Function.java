package ksp.kos.ideaplugin.expressions;

import ksp.kos.ideaplugin.expressions.inline.InlineFunctions;
import ksp.kos.ideaplugin.psi.KerboScriptExpr;

import java.util.HashMap;
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

    public String getName() {
        return name;
    }

    public Expression[] getArgs() {
        return args;
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
            return new Function(name+"_", args).inline().multiply(args[0].differentiate());
        }
        Expression[] diffArgs = new Expression[args.length*2];
        for (int i = 0; i < args.length; i++) {
            diffArgs[i] = args[i];
            diffArgs[i + args.length] = args[i].differentiate();
        }
        return new Function(name+"_", diffArgs).inline();
    }

    @Override
    public Expression inline(HashMap<String, Expression> parameters) {
        Expression[] args = new Expression[this.args.length];
        for (int i = 0; i < this.args.length; i++) {
            args[i] = this.args[i].inline(parameters);
        }
        return new Function(name, args);
    }

    @Override
    public Expression simplify() {
        Expression[] args = new Expression[this.args.length];
        for (int i = 0; i < this.args.length; i++) {
            args[i] = this.args[i].simplify();
        }
        return new Function(name, args).inline();
    }

    private Expression inline() {
        return InlineFunctions.getInstance().inline(this);
    }

    @Override
    public void visit(ExpressionVisitor visitor) {
        visitor.visitFunction(this);
        for (Expression arg : args) {
            arg.visit(visitor);
        }
    }

    public static Expression log(Expression arg) {
        return new Function("log", arg);
    }
}
