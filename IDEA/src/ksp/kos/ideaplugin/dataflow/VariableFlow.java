package ksp.kos.ideaplugin.dataflow;

import com.intellij.psi.util.PsiTreeUtil;
import ksp.kos.ideaplugin.expressions.Expression;
import ksp.kos.ideaplugin.expressions.SyntaxException;
import ksp.kos.ideaplugin.psi.*;
import ksp.kos.ideaplugin.reference.SuffixtermType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

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
    public VariableFlow addContext(HashMap<String, NamedFlow<?>> context) {
        VariableFlow flow = super.addContext(context);
        context.put(getName(), flow);
        return flow;
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
        KerboScriptExpr nameExpr = PsiTreeUtil.findChildOfType(setStmt.getVaridentifier(), KerboScriptExpr.class);
        if (nameExpr instanceof KerboScriptSuffixterm) {
            KerboScriptAtom atom = ((KerboScriptSuffixterm) nameExpr).getAtom();
            if (atom.getType().getType()== SuffixtermType.VARIABLE) {
                String name = atom.getName();
                return new VariableFlow(false, name, Expression.parse(setStmt.getExpr()));
            }
        }
        return null;
    }
}
