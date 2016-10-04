package ksp.kos.ideaplugin.dataflow;

import ksp.kos.ideaplugin.expressions.Expression;
import ksp.kos.ideaplugin.expressions.SyntaxException;
import ksp.kos.ideaplugin.psi.*;
import ksp.kos.ideaplugin.reference.ReferableType;
import org.jetbrains.annotations.NotNull;

/**
 * Created on 12/03/16.
 *
 * @author ptasha
 */
public class VariableFlow extends ExpressionFlow<VariableFlow> implements NamedFlow<VariableFlow> {
    private final boolean declare;
    private final String name;

    public VariableFlow(boolean declare, String name, Expression expression) {
        super(expression);
        this.declare = declare;
        this.name = name;
    }

    @Override
    public VariableFlow differentiate(ContextBuilder context) {
        context.add(this);
        VariableFlow diff = differentiate();
        context.add(diff);
        return diff;
    }

    @Override
    public boolean addContext(ContextBuilder context) {
        super.addContext(context);
        context.getMap().put(getName(), this);
        return true;
    }

    @NotNull
    @Override
    protected VariableFlow create(Expression diff) {
        return new VariableFlow(declare, name+"_", diff);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getText() {
        String text = "";
        text+=declare?"local ":"set ";
        text+=name;
        text+=" to "+getExpression().getText()+".";
        return text;
    }

    public static VariableFlow parse(KerboScriptDeclareIdentifierClause declareIdentifierClause) throws SyntaxException {
        return new VariableFlow(true, declareIdentifierClause.getName(), Expression.parse(declareIdentifierClause.getExpr()));
    }

    public static VariableFlow parse(KerboScriptSetStmt setStmt) throws SyntaxException {
        KerboScriptExpr nameExpr = setStmt.getVaridentifier().downTill(KerboScriptExpr.class);
        if (nameExpr instanceof KerboScriptSuffixterm) {
            KerboScriptAtom atom = ((KerboScriptSuffixterm) nameExpr).getAtom();
            if (atom.getReferableType() == ReferableType.VARIABLE) {
                String name = atom.getName();
                return new VariableFlow(false, name, Expression.parse(setStmt.getExpr()));
            }
        }
        return null;
    }
}
