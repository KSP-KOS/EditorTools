package ksp.kos.ideaplugin.expressions;

import ksp.kos.ideaplugin.dataflow.FunctionFlow;
import ksp.kos.ideaplugin.dataflow.ParameterFlow;
import ksp.kos.ideaplugin.expressions.inline.InlineFunction;
import ksp.kos.ideaplugin.expressions.inline.InlineFunctions;
import ksp.kos.ideaplugin.psi.KerboScriptExpr;
import ksp.kos.ideaplugin.reference.FlowSelfResolvable;
import ksp.kos.ideaplugin.reference.context.LocalContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
    public Expression differentiate(LocalContext context) {
        if (args.length==0) {
            return Number.ZERO;
        }
        String name = this.name + "_";
        // TODO diff function here
        FunctionFlow diff = (FunctionFlow) FlowSelfResolvable.function(context, name).resolve();
        if (args.length==1) {
            return new Function(name, args).inline(context).multiply(args[0].differentiate(context));
        }
        FunctionFlow original = (FunctionFlow) FlowSelfResolvable.function(context, this.name).resolve();
        if (original!=null && diff!=null) {
            int j = 0;
            List<ParameterFlow> origParams = original.getParameters();
            List<ParameterFlow> diffParams = diff.getParameters();
            Expression[] diffArgs = new Expression[diffParams.size()];
            for (ParameterFlow diffParam : diffParams) {
                int i = origParams.indexOf(diffParam);
                if (i>=0) {
                    diffArgs[j] = args[i];
                } else if (diffParam.getName().endsWith("_")) {
                    String paramName = diffParam.getName();
                    paramName = paramName.substring(0, paramName.length() - 1);
                    i = origParams.indexOf(new ParameterFlow(paramName));
                    if (i < 0) {
                        break;
                    }
                    diffArgs[j] = args[i].differentiate(context);
                } else {
                    break;
                }
                j++;
            }
            if (j==diffArgs.length) {
                return new Function(name, diffArgs).inline(context);
            }
        }

        Expression[] diffArgs = new Expression[args.length*2];
        for (int i = 0; i < args.length; i++) {
            diffArgs[2*i] = args[i];
            diffArgs[2*i + 1] = args[i].differentiate(context);
        }
        return new Function(name, diffArgs).inline(context);
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
        return inline((LocalContext)null);
    }

    private Expression inline(LocalContext context) {
        Expression inlined = InlineFunctions.getInstance().inline(this);
        if (inlined == null) {
            if (context != null) { // TODO pass context to simplify
                // TODO shouldn't parse all functions here for speed sake
                FunctionFlow flow = (FunctionFlow) FlowSelfResolvable.function(context, name).resolve();
                if (flow!=null) {
                    InlineFunction inlineFunction = flow.getInlineFunction();
                    if (inlineFunction!=null) {
                        return inlineFunction.inline(args);
                    }
                }
            }
            return this;
        }
        return inlined;
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visitFunction(this);
    }

    @Override
    public void acceptChildren(ExpressionVisitor visitor) {
        for (Expression arg : args) {
            arg.accept(visitor);
        }
    }

    public static Expression log(Expression arg) {
        return new Function("log", arg);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Function function = (Function) o;
        return Objects.equals(name, function.name) &&
                Arrays.equals(args, function.args);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, args);
    }
}
