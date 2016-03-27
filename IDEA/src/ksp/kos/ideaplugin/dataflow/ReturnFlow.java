package ksp.kos.ideaplugin.dataflow;

import ksp.kos.ideaplugin.expressions.Expression;
import ksp.kos.ideaplugin.expressions.SyntaxException;
import ksp.kos.ideaplugin.psi.KerboScriptReturnStmt;
import org.jetbrains.annotations.NotNull;

/**
 * Created on 12/03/16.
 *
 * @author ptasha
 */
public class ReturnFlow extends ExpressionFlow<ReturnFlow> {
    public ReturnFlow(Expression expression) {
        super(expression);
    }

    @NotNull
    @Override
    protected ReturnFlow differentiate(Expression diff) {
        return new ReturnFlow(diff);
    }

    @Override
    public String getText() {
        return "return "+getExpression().getText()+".";
    }

    public static ReturnFlow parse(KerboScriptReturnStmt returnStmt) throws SyntaxException {
        return new ReturnFlow(Expression.parse(returnStmt.getExpr()));
    }
}
