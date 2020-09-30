package ksp.kos.ideaplugin.expressions;

import com.intellij.lang.ASTNode;
import ksp.kos.ideaplugin.psi.*;
import ksp.kos.ideaplugin.reference.context.LocalContext;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created on 28/01/16.
 *
 * @author ptasha
 */
public abstract class Expression {
    public static Expression parse(KerboScriptExpr psiExpression) throws SyntaxException {
        if (psiExpression instanceof KerboScriptFactor) {
            return Element.parse(psiExpression);
        } else if (psiExpression instanceof KerboScriptUnaryExpr) {
            return Element.parse(psiExpression);
        } else if (psiExpression instanceof KerboScriptArithExpr) {
            return new Addition((KerboScriptArithExpr) psiExpression);
        } else if (psiExpression instanceof KerboScriptMultdivExpr) {
            return new Multiplication((KerboScriptMultdivExpr) psiExpression);
        } else if (psiExpression instanceof KerboScriptCompareExpr) {
            return new Compare((KerboScriptCompareExpr) psiExpression);
        } else if (psiExpression instanceof KerboScriptNumber) {
            return new Number((KerboScriptNumber) psiExpression);
        } else if (psiExpression instanceof KerboScriptSciNumber) {
            return new Number((KerboScriptSciNumber) psiExpression);
        } else if (psiExpression instanceof KerboScriptSuffix) {
            return new Constant((KerboScriptSuffix) psiExpression);
        } else if (psiExpression instanceof KerboScriptSuffixterm) {
            KerboScriptSuffixterm suffixterm = (KerboScriptSuffixterm) psiExpression;
            int tailSize = suffixterm.getSuffixtermTrailerList().size();
            if (tailSize == 0) {
                return Atom.parse(suffixterm.getAtom());
            } else if (tailSize == 1) {
                KerboScriptAtom atom = suffixterm.getAtom();
                String identifierName = atom.getName();
                if (identifierName !=null) {
                    KerboScriptSuffixtermTrailer trailer = suffixterm.getSuffixtermTrailerList().get(0);
                    if (trailer instanceof KerboScriptFunctionTrailer) {
                        KerboScriptArglist arglist = ((KerboScriptFunctionTrailer) trailer).getArglist();
                        if (arglist==null) {
                            return new Function(identifierName);
                        }
                        return new Function(identifierName, arglist.getExprList());
                    }
                }
            }
            return new Constant(suffixterm);
        } else {
            throw new SyntaxException("Unexpected element "+psiExpression+": "+psiExpression.getText());
        }
    }

    public Expression simplify() {
        return this;
    }

    public abstract String getText();

    public abstract Expression differentiate(LocalContext context);

    public Expression minus() {
        return Element.create(-1, Atom.toAtom(this));
    }

    public Expression simplePlus(Expression expression) {
        if (this.equals(expression)) {
            return this.multiply(Number.create(2));
        }
        return null;
    }

    public Expression plus(Expression expression) {
        Expression result = simplePlus(expression);
        if (result!=null) {
            return result;
        }
        return new Addition(this).plus(expression);
    }

    public Expression minus(Expression expression) {
        if (this.equals(expression)) {
            return Number.ZERO;
        }
        return new Addition(this).minus(expression);
    }

    public boolean isNegative() {
        return false;
    }

    public Expression simpleMultiply(Expression expression) {
        if (this.equals(expression)) {
            return Element.create(1, this, new Number(2));
        }
        return null;
    }

    public Expression multiply(Expression expression) {
        Expression result = simpleMultiply(expression);
        if (result!=null) {
            return result;
        }
        result = expression.simpleMultiply(this);
        if (result!=null) {
            return result;
        }
        return new Multiplication(this).multiply(expression);
    }

    public Expression multiplyLeft(Expression expression) {
        return expression.multiply(this);
    }

    public Expression divide(Expression expression) {
        Expression result = simpleDivide(expression);
        if (result != null) return result;
        return new Multiplication(this).divide(expression);
    }

    public boolean canMultiply(Expression expression) { // TODO symmetry for this and multiply and plus
        return this.equals(expression);
    }

    public Expression power(Expression expression) {
        return Element.create(1, Atom.toAtom(this), Atom.toAtom(expression));
    }

    @Nullable
    public Expression simpleDivide(Expression expression) {
        if (this.equals(expression)) {
            return Number.ONE;
        }
        return expression.simpleDivideBackward(this);
    }

    protected Expression simpleDivideBackward(Expression expression) {
        return null;
    }

    public abstract Expression inline(HashMap<String, Expression> args);

    public final Set<String> getVariableNames() {
        HashSet<String> variables = new HashSet<>();
        accept(new ExpressionVisitor() {
            @Override
            public void visitVariable(Variable variable) {
                variables.add(variable.getName());
                super.visitVariable(variable);
            }
        });
        return variables;
    }

    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    public void acceptChildren(ExpressionVisitor visitor) {
    }

    public boolean isNumber() {
        return false;
    }

    public Expression normalize() {
        return this;
    }

    public Expression distribute() {
        return this;
    }

    public Expression root(Expression expression) {
        return this.divide(expression.divide(this).divisor());
    }

    public Expression divisor() {
        return Number.ONE;
    }

    public Expression distribute(Expression expression) {
        if (expression instanceof Addition) {
            return expression.distribute(this);
        }
        return this.multiply(expression);
    }

    public Expression distribute(Multiplication.Op operation, Expression expression) {
        if (operation==Multiplication.Op.MUL) {
            return distribute(expression);
        }
        return operation.apply(this, expression);
    }

    public String toString() {
        return getClass().getSimpleName()+"="+getText();
    }
}
