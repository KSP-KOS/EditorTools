package ksp.kos.ideaplugin.dataflow;

import ksp.kos.ideaplugin.expressions.Expression;
import ksp.kos.ideaplugin.expressions.SyntaxException;
import ksp.kos.ideaplugin.psi.*;

import java.util.List;
import java.util.Set;

/**
 * Created on 14/08/16.
 *
 * @author ptasha
 */
public class IfFlow extends BaseFlow<IfFlow> {
    private final KerboScriptExpr expr;
    private final Context trueList;
    private final Context falseList;

    public IfFlow(KerboScriptExpr expr, Context trueList, Context falseList) {
        this.expr = expr;
        this.trueList = trueList;
        this.falseList = falseList;
    }

    public static IfFlow parse(KerboScriptIfStmt ifStmt, Context parent) throws SyntaxException {
        KerboScriptExpr expr = ifStmt.getExpr();
        List<KerboScriptInstruction> list = ifStmt.getInstructionList();
        Context trueList = parseBlock(list.get(0), parent);
        Context falseList;
        if (list.size()>1) {
            falseList = parseBlock(list.get(1), parent);
        } else {
            falseList = new Context();
        }
        return new IfFlow(expr, trueList, falseList);
    }

    @Override
    public boolean addContext(Context context) {
        addExpressionContext(expr, context);

        trueList.setParent(context);
        boolean trueCont = trueList.buildMap();
        falseList.setParent(context);
        boolean falseCont = falseList.buildMap();

        // TODO merge contexts
        context.addReturnFlow(trueList.getReturnFlow());
        context.addReturnFlow(falseList.getReturnFlow());
        if (!trueList.getReturnFlow().isEmpty() || !falseList.getReturnFlow().isEmpty()) {
            context.addReturnFlow(this);
        }

        return trueCont || falseCont;
    }

    private void addExpressionContext(KerboScriptExpr expr, Context context) {
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

    private void addExpressionsContext(List<KerboScriptExpr> list, Context context) {
        for (KerboScriptExpr kerboScriptExpr : list) {
            addExpressionContext(kerboScriptExpr, context);
        }
    }

    private static Context parseBlock(KerboScriptInstruction kerboScriptInstruction, Context parent) throws SyntaxException {
        FlowParser parser = new FlowParser(parent);
        parser.parseInstruction(kerboScriptInstruction);
        return parser.getContext();
    }

    @Override
    public IfFlow differentiate(Context context) {
        IfFlow diff = differentiate();
        context.add(diff);
        return diff;
    }

    @Override
    public IfFlow differentiate() {
        Context trueContext = new Context();
        trueList.differentiate(trueContext);
        Context falseContext = new Context();
        falseList.differentiate(falseContext);
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

    private String print(Context context) {
        //if (context.getList().size()==1) return context.getText();
        return "{\n" + context.getText() + "\n}";
    }
}
