package ksp.kos.ideaplugin.expressions;

import ksp.kos.ideaplugin.psi.KerboScriptCompareExpr;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created on 18/09/16.
 *
 * @author ptasha
 */
public class Compare extends MultiExpression<Compare.Op,Expression> {
    public Compare(KerboScriptCompareExpr expr) throws SyntaxException {
        super(expr);
    }

    public Compare(List<Item<Op,Expression>> items) {
        super(items);
    }

    @Override
    public Expression differentiate() {
        return null;
    }

    @Override
    public MultiExpressionBuilder<Op, Expression> createBuilder() {
        return new MultiExpressionBuilder<Op, Expression>(Compare.class) {
            @NotNull
            @Override
            protected Op[] operators() {
                return Op.values();
            }

            @Override
            protected MultiExpression<Op, Expression> createExpression(List<Item<Op, Expression>> items) {
                return new Compare(items);
            }
        };
    }

    public enum Op implements MultiExpression.Op {
        EQ("=", null),
        NEQ("<>", null),
        GT(">", null),
        LT("<", null),
        GET(">=", null),
        LET("<=", null);

        private final String text;
        private final MultiExpression.Operation operation;

        Op(String text, MultiExpression.Operation operation) {
            this.text = text;
            this.operation = operation;
        }

        @Override
        public String getText() {
            return text;
        }

        public String toString() {
            return " "+text+" ";
        }

        @Override
        public Expression apply(Expression exp1, Expression exp2) {
            return operation.apply(exp1, exp2);
        }
    }

}
