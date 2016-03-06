package ksp.kos.ideaplugin.expressions;

import com.intellij.lang.ASTNode;
import ksp.kos.ideaplugin.psi.KerboScriptNumber;
import ksp.kos.ideaplugin.psi.KerboScriptSciNumber;
import ksp.kos.ideaplugin.psi.KerboScriptTypes;

/**
 * Created on 28/01/16.
 *
 * @author ptasha
 */
public class Number extends Atom {
    public static final Number ZERO = new Number(0);
    public static final Number ONE = new Number(1);

    private final int number;
    private final int point;
    private final int e;

    public static Expression create(int number) {
        if (number<0) {
            return new Element(-1, new Number(-number), ONE);
        }
        return new Number(number);
    }

    private Number(int number) {
        this(number, 0, 0);
    }

    private Number(int number, int point, int e) {
        if (number<0) throw new IllegalArgumentException("Negative numbers are not allowed");
        this.number = number;
        this.point = point;
        this.e = e;
    }

    public Number(KerboScriptNumber psiNumber) {
        this(psiNumber, 0);
    }

    public Number(KerboScriptNumber psiNumber, int e) {
        String text = psiNumber.getText();
        int p = text.indexOf('.');
        if (p>=0) {
            point = text.length() - p - 1;
            text = text.replace(".", "");
        } else {
            point = 0;
        }
        this.number = Integer.parseInt(text);
        this.e = e;
    }

    public Number(KerboScriptSciNumber psiNumber) {
        this(psiNumber.getNumber(), parseE(psiNumber));
    }

    private static int parseE(KerboScriptSciNumber psiNumber) {
        ASTNode plusMinus = psiNumber.getNode().findChildByType(KerboScriptTypes.PLUSMINUS);
        int sign = 1;
        if (plusMinus!=null && plusMinus.getText().equals("-")) {
            sign = -1;
        }
        ASTNode psiE = psiNumber.getNode().findChildByType(KerboScriptTypes.INTEGER);
        return psiE == null ? 0 : sign * Integer.parseInt(psiE.getText());
    }

    @Override
    public String getText() {
        String text = "";
        int point = this.point;
        int number = this.number;
        while (point-->0) {
            int c = number % 10;
            if (c>0 || !text.isEmpty()) {
                text = c + text;
            }
            number /= 10;
        }
        if (!text.isEmpty()) {
            text = "." + text;
        }
        text = number+text;
        if (e!=0) {
            text += "e"+e;
        }
        return text;
    }

    @Override
    public Expression plus(Expression expression) {
        if (number==0) {
            return expression;
        }
        return super.plus(expression);
    }

    @Override
    public Expression minus(Expression expression) {
        if (number==0) {
            return expression.minus();
        }
        return super.minus(expression);
    }

    @Override
    public Expression multiply(Expression expression) {
        if (number==0) {
            return ZERO;
        } else if (this.equals(ONE)) {
            return expression;
        }
        return super.multiply(expression);
    }

    @Override
    public Expression divide(Expression expression) {
        if (number==0) {
            return ZERO;
        }
        return super.divide(expression);
    }

    @Override
    public Expression differentiate() {
        return Number.ZERO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Number number = (Number) o;

        return Double.compare(number.doubleValue(), doubleValue()) == 0;

    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(doubleValue());
        return (int) (temp ^ (temp >>> 32));
    }

    public double doubleValue() {
        return number * Math.pow(10, e - point);
    }
}
