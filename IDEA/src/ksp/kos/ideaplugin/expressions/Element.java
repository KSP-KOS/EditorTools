package ksp.kos.ideaplugin.expressions;

import com.intellij.lang.ASTNode;
import ksp.kos.ideaplugin.psi.KerboScriptExpr;
import ksp.kos.ideaplugin.psi.KerboScriptFactor;
import ksp.kos.ideaplugin.psi.KerboScriptTypes;
import ksp.kos.ideaplugin.psi.KerboScriptUnaryExpr;
import ksp.kos.ideaplugin.reference.context.LocalContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

/**
 * Created on 30/01/16.
 *
 * @author ptasha
 */
public class Element extends Expression {
    private final int sign;
    private final Atom atom;
    private final Atom power;

    public static Expression create(int sign, Expression atom) {
        return create(sign, atom, Number.ONE);
    }

    public static Expression create(int sign, Expression atom, Expression power) {
        if (atom.equals(Number.ZERO)) {
            power = Number.ONE;
            sign = 1;
        } else if (atom.equals(Number.ONE)) {
            power = Number.ONE;
        }
        if (power.equals(Number.ONE)) {
            if (sign == 1) {
                return atom;
            }
        } else if (power.equals(Number.ZERO)) {
            return create(sign, Number.ONE);
        }
        return new Element(sign, atom, power);
    }

    public Element(int sign, Expression atom, Expression power) {
        this.sign = sign;
        this.atom = Atom.toAtom(atom);
        this.power = Atom.toAtom(power);
    }

    public int getSign() {
        return sign;
    }

    public Atom getAtom() {
        return atom;
    }

    public Expression getPower() {
        return power;
    }

    public static Element parseUnary(KerboScriptUnaryExpr element) throws SyntaxException {
        int sign = parseSign(element);
        KerboScriptExpr expr = element.getExpr();
        if (expr instanceof KerboScriptFactor) {
            return parseFactor(sign, (KerboScriptFactor) expr);
        } else {
            return new Element(sign, Atom.parse(expr), Number.ONE);
        }
    }

    @NotNull
    public static Element parseFactor(KerboScriptFactor element) throws SyntaxException {
        return parseFactor(1, element);
    }

    @NotNull
    private static Element parseFactor(int sign, KerboScriptFactor expr) throws SyntaxException {
        List<KerboScriptExpr> list = expr.getExprList();
        Expression power = Number.create(1);
        for (int i = 1; i < list.size(); i++) {
            KerboScriptExpr fexp = list.get(i);
            power = power.multiply(Expression.parse(fexp));
        }
        return new Element(sign, Atom.parse(list.get(0)), power);
    }

    private static int parseSign(KerboScriptUnaryExpr element) throws SyntaxException {
        int sign;
        ASTNode plusMinus = element.getNode().findChildByType(KerboScriptTypes.PLUSMINUS);
        if (plusMinus == null) {
            sign = 1;
        } else {
            String text = plusMinus.getText();
            switch (text) {
                case "+":
                    sign = 1;
                    break;
                case "-":
                    sign = -1;
                    break;
                default:
                    throw new SyntaxException("+ or - is required: found " + plusMinus + ": " + text);
            }
        }
        return sign;
    }

    public static Element parse(KerboScriptExpr element) throws SyntaxException {
        if (element instanceof KerboScriptUnaryExpr) {
            return parseUnary((KerboScriptUnaryExpr) element);
        } else if (element instanceof KerboScriptFactor) {
            return parseFactor((KerboScriptFactor) element);
        } else {
            return new Element(1, Atom.parse(element), Number.ONE);
        }
    }

    @Override
    public String getText() {
        String text = "";
        if (sign == -1) {
            text += "-";
        }
        text += atom.getText();
        if (!power.equals(Number.ONE)) {
            text += "^" + power.getText();
        }
        return text;
    }

    @Override
    public Expression differentiate(LocalContext context) {
        if (power.equals(Number.ONE)) {
            Expression diff = atom.differentiate(context);
            if (sign == -1) {
                diff = diff.minus();
            }
            return diff;
        }
        return Function.log(atom).multiply(power).differentiate(context).multiply(this);
    }

    public static Element toElement(Expression expression) {
        expression = Escaped.unescape(expression);
        if (expression instanceof Element) {
            return (Element) expression;
        } else {
            return new Element(1, Atom.toAtom(expression), Number.ONE);
        }
    }

    public boolean isSimple() {
        return sign == 1 && power.equals(Number.ONE);
    }

    @Override
    public Expression simplify() {
        if (power.isNegative()) {
            return Number.ONE.divide(create(sign, atom, power.minus()));
        }
        return create(sign, atom.simplify(), power.simplify());
    }

    @Override
    public Expression power(Expression expression) {
        Expression power = this.power.multiply(expression);
        return create(sign, atom, power);
    }

    @Override
    public Expression inline(HashMap<String, Expression> args) {
        return create(sign, atom.inline(args), power.inline(args));
    }

    @Override
    public Expression simplePlus(Expression expression) {
        if (expression instanceof Element) {
            Element element = (Element) expression;
            if (power.equals(element.power) && atom.equals(element.atom)) {
                if (sign==element.sign) {
                    return Number.create(2).multiply(this);
                } else {
                    return Number.ZERO;
                }
            }
        }
        if (power.equals(Number.ONE)) {
            if (sign<0) {
                if (Atom.toAtom(expression).equals(atom)) {
                    return Number.ZERO;
                }
                Expression simplePlus = atom.simplePlus(expression.minus());
                if (simplePlus!=null) {
                    return simplePlus.minus();
                }
            } else {
                return atom.simplePlus(expression);
            }
        }
        return null;
    }

    @Override
    public Expression simpleMultiply(Expression expression) {
        if (expression instanceof Element) {
            Element element = (Element) expression;
            if (this.isNumber() && element.isNumber()) {
                return create(sign * element.sign, atom.multiply(element.getAtom()));
            } else if (atom.equals(((Element) expression).atom)) {
                return create(sign * element.sign, atom, power.plus(element.power));
            }
        } else if (isNumber() && expression instanceof Number) {
            return create(sign, atom.multiply(expression));
        }
        return null;
    }

    @Nullable
    @Override
    public Expression simpleDivide(Expression expression) {
        if (expression instanceof Element) {
            if (atom.equals(((Element) expression).atom)) {
                Element element = (Element) expression;
                return create(sign * element.sign, atom, power.minus(element.power));
            }
        } else if (atom.equals(expression)) {
            return create(sign, atom, power.minus(Number.ONE));
        } else if (power.equals(Number.ONE)) {
            Expression div = atom.simpleDivide(expression);
            if (div!=null) {
                return create(sign, div, Number.ONE);
            }
        }
        return null;
    }

    @Override
    protected Expression simpleDivideBackward(Expression expression) {
        if (atom.equals(expression)) {
            return create(sign, atom, Number.ONE.minus(power));
        } else if (power.equals(Number.ONE)) {
            Expression div = expression.simpleDivide(atom);
            if (div!=null) {
                return create(sign, div, Number.ONE);
            }
        }
        return null;
    }

    @Override
    public boolean canMultiply(Expression expression) {
        return expression instanceof Element && atom.equals(((Element) expression).atom);
    }

    @Override
    public Expression minus() {
        return create(-sign, atom, power);
    }

    @Override
    public boolean isNegative() {
        return sign < 0;
    }

    @Override
    public boolean isNumber() {
        return atom.isNumber() && power.equals(Number.ONE);
    }

    public boolean isAddition() {
        return atom.isAddition() && power.equals(Number.ONE);
    }

    @Override
    public Expression normalize() {
        if (power.equals(Number.ONE)) {
            Expression expression = atom.normalize();
            if (sign <= 0) {
                expression = expression.distribute(Element.create(-1, Number.ONE));
            }
            return expression;
        }
        return super.normalize();
    }

    @Override
    public Expression distribute() {
        if (power instanceof Number) {
            Number number = (Number) power;
            if (number.getE()==0 && number.getPoint()==0 && number.getNumber()>0) {
                return distribute(sign, atom, number.getNumber());
            }
        }
        return super.distribute();
    }

    private Expression distribute(int sign, Atom atom, int power) {
        if (power==1) {
            Expression expression = atom.distribute();
            if (sign <= 0) {
                expression = expression.distribute(Element.create(-1, Number.ONE));
            }
            return expression;
        } else {
            return distribute(sign, atom, power-1).distribute(atom.distribute());
        }
    }

    @Override
    public void acceptChildren(ExpressionVisitor visitor) {
        atom.accept(visitor);
        power.accept(visitor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Element element = (Element) o;

        if (!atom.equals(element.atom)) return false;
        if (sign != element.sign) return false;
        return power.equals(element.power);

    }

    @Override
    public int hashCode() {
        int result = sign;
        result = 31 * result + atom.hashCode();
        result = 31 * result + power.hashCode();
        return result;
    }
}
