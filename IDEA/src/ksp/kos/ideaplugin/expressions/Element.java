package ksp.kos.ideaplugin.expressions;

import com.intellij.lang.ASTNode;
import ksp.kos.ideaplugin.psi.KerboScriptExpr;
import ksp.kos.ideaplugin.psi.KerboScriptFactor;
import ksp.kos.ideaplugin.psi.KerboScriptTypes;
import ksp.kos.ideaplugin.psi.KerboScriptUnaryExpr;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created on 30/01/16.
 *
 * @author ptasha
 */
public class Element extends Expression {
    private int sign;
    private Atom atom;
    private Expression power;

    public Element(int sign, Atom atom, Expression power) {
        this.sign = sign;
        this.atom = atom;
        this.power = power;
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
        int sign;ASTNode plusMinus = element.getNode().findChildByType(KerboScriptTypes.PLUSMINUS);
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
        if (sign==-1) {
            text+="-";
        }
        text+=atom.getText();
        if (power instanceof Atom) {
            if (!power.equals(Number.ONE)) {
                text += "^" + power.getText();
            }
        } else if (power instanceof Element) {
            text+="^"+power.getText();
        } else if (power instanceof Addition) {
            text+="^("+power.getText()+")";
        } else if (power instanceof Multiplication) {
            text+="^("+power.getText()+")";
        } else {
            throw new RuntimeException("Unexpected expression "+power+": "+power.getText());
        }
        return text;
    }

    @Override
    public Expression differentiate() {
        if (power.equals(Number.ONE)) {
            Expression diff = atom.differentiate();
            if (sign==-1) {
                diff = diff.minus();
            }
            return diff;
        }
        return power.copy().multiply(Function.log(atom.copy())).differentiate().multiply(this);
    }

    @Override
    public Expression copy() {
        return new Element(sign, (Atom) atom.copy(), power.copy());
    }

    public static Element toElement(Expression expression) {
        if (expression instanceof Element) {
            return (Element) expression;
        } else {
            return new Element(1, Atom.toAtom(expression), Number.ONE);
        }
    }

    public boolean isSimple() {
        return sign==1 && power.equals(Number.ONE);
    }

    @Override
    public Expression simplify() {
        power = power.simplify();
        if (power.equals(Number.ONE)) {
            return applySign(atom.simplify());
        } else if (power.equals(Number.ZERO)) {
            return applySign(Number.ONE);
        }
        atom = Atom.toAtom(atom.simplify());
        if (atom.equals(Number.ZERO)){
            return Number.ZERO;
        } else if (atom.equals(Number.ONE)) {
            return applySign(Number.ONE);
        }
        return this;
    }

    public Expression applySign(Expression expression) {
        if (sign<0) {
            return expression.minus();
        }
        return expression;
    }

    @Override
    public Expression power(Expression expression) {
        power = power.multiply(expression);
        return this;
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

    @Override
    public Element minus() {
        sign *= -1;
        return this;
    }
}
