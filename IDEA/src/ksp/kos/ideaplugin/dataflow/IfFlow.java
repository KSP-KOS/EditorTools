package ksp.kos.ideaplugin.dataflow;

import ksp.kos.ideaplugin.expressions.Expression;
import ksp.kos.ideaplugin.expressions.SyntaxException;
import ksp.kos.ideaplugin.psi.*;
import ksp.kos.ideaplugin.reference.context.Context;

import java.util.List;
import java.util.Set;

/**
 * Created on 14/08/16.
 *
 * @author ptasha
 */
public class IfFlow extends BaseFlow<IfFlow> {
    private final KerboScriptExpr expr;
    private final ContextBuilder trueList;
    private final ContextBuilder falseList;

    public IfFlow(KerboScriptExpr expr, ContextBuilder trueList, ContextBuilder falseList) {
        this.expr = expr;
        this.trueList = trueList;
        this.falseList = falseList;
    }

    public static IfFlow parse(KerboScriptIfStmt ifStmt, ContextBuilder parent) throws SyntaxException {
        KerboScriptExpr expr = ifStmt.getExpr();
        List<KerboScriptInstruction> list = ifStmt.getInstructionList();
        ContextBuilder trueList = parseBlock(list.get(0), parent);
        ContextBuilder falseList;
        if (list.size()>1) {
            falseList = parseBlock(list.get(1), parent);
        } else {
            falseList = new ContextBuilder();
        }
        return new IfFlow(expr, trueList, falseList);
    }

    @Override
    public boolean addContext(ContextBuilder context) {
        addExpressionContext(expr, context);

        trueList.setParent(context);
        boolean trueCont = trueList.buildMap();
        falseList.setParent(context);
        boolean falseCont = falseList.buildMap();

        // TODO merge contexts
        context.addReturnFlow(trueList.getReturn());
        context.addReturnFlow(falseList.getReturn());
        if (!trueList.getReturn().isEmpty() || !falseList.getReturn().isEmpty()) {
            context.addReturnFlow(this);
        }

        return trueCont || falseCont;
    }

    private void addExpressionContext(KerboScriptExpr expr, ContextBuilder context) {
        // TODO add them to Expression
        if (expr instanceof KerboScriptOrExpr) {
            addExpressionsContext(((KerboScriptOrExpr) expr).getExprList(), context);
        } else if (expr instanceof KerboScriptAndExpr) {
            addExpressionsContext(((KerboScriptAndExpr) expr).getExprList(), context);
        } else {
            try {
                Set<String> names = Expression.parse(expr).getVariableNames();
                for (String name : names) {
                    Dependency flow = context.getFlow(name);
                    if (flow != null) {
                        flow.addDependee(this);
                    }
                }
            } catch (SyntaxException e) {
                e.printStackTrace(); // TODO it should be already parsed thus no exception here
            }
        }
    }

    private void addExpressionsContext(List<KerboScriptExpr> list, ContextBuilder context) {
        for (KerboScriptExpr kerboScriptExpr : list) {
            addExpressionContext(kerboScriptExpr, context);
        }
    }

    private static ContextBuilder parseBlock(KerboScriptInstruction kerboScriptInstruction, ContextBuilder parent) throws SyntaxException {
        FlowParser parser = new FlowParser(parent);
        parser.parseInstruction(kerboScriptInstruction);
        return parser.getContext();
    }

    @Override
    public IfFlow differentiate(Context<ReferenceFlow> context, ContextBuilder contextBuilder) {
        IfFlow diff = differentiate(context);
        contextBuilder.add(diff);
        return diff;
    }

    @Override
    public IfFlow differentiate(Context<ReferenceFlow> context) {
        ContextBuilder trueContext = new ContextBuilder();
        trueList.differentiate(context, trueContext);
        ContextBuilder falseContext = new ContextBuilder();
        falseList.differentiate(context, falseContext);
        // TODO simplify
        return new IfFlow(expr, trueContext, falseContext);
    }

    @Override
    public String getText() {
        String text = "";
        text += "if " + expr.getText() + " " + print(trueList);
        if (!falseList.getList().isEmpty()) {
            text += "\n else " + print(falseList);
        }
        return text;
    }

    private String print(ContextBuilder context) {
        //if (context.getList().size()==1) return context.getText();
        return "{\n" + context.getText() + "\n}";
    }
}
