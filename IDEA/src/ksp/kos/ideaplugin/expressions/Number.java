package ksp.kos.ideaplugin.expressions;

import com.intellij.lang.ASTNode;
import ksp.kos.ideaplugin.psi.KerboScriptNumber;
import ksp.kos.ideaplugin.psi.KerboScriptSciNumber;
import ksp.kos.ideaplugin.psi.KerboScriptTypes;
import ksp.kos.ideaplugin.reference.context.LocalContext;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.function.BiFunction;

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
        if (number < 0) {
            return Element.create(-1, new Number(-number));
        }
        return new Number(number);
    }

    public static Expression create(int number, int point, int e) {
        if (number < 0) {
            return Element.create(-1, new Number(-number, point, e));
        }
        return new Number(number, point, e);
    }

    public Number(int number) {
        this(number, 0, 0);
    }

    private Number(int number, int point, int e) {
        if (number < 0) {
            throw new IllegalArgumentException("Negative numbers are not allowed");
        }
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
        if (p >= 0) {
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
        if (plusMinus != null && plusMinus.getText().equals("-")) {
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
        while (point-- > 0) {
            int c = number % 10;
            if (c > 0 || !text.isEmpty()) {
                text = c + text;
            }
            number /= 10;
        }
        if (!text.isEmpty()) {
            text = "." + text;
        }
        text = number + text;
        if (e != 0) {
            text += "e" + e;
        }
        return text;
    }

    @Override
    @Nullable
    public Expression simplePlus(Expression expression) {
        if (number == 0) {
            return expression;
        } else if (expression instanceof Number) {
            return addition((Number) expression, (x, y) -> x + y);
        } else if (expression instanceof Element) {
            Element element = (Element) expression;
            if (element.getPower().equals(Number.ONE) && element.getAtom() instanceof Number) {
                return addition((Number) element.getAtom(), (x, y) -> x + element.getSign()*y);
            }
        }
        return null;
    }

    private Expression addition(Number number, BiFunction<Integer, Integer, Integer> function) {
        int pow = e - point - number.e + number.point;
        int e = Math.min(this.e, number.e);
        int point = e - pow;
        if (pow == 0) {
            return create(function.apply(this.number, number.number), point, e);
        } else if (pow > 0) {
            int n = this.number;
            double p = Math.pow(10, pow);
            if (n < Integer.MAX_VALUE / p) {
                n = (int) (n * p);
                return create(function.apply(n, number.number), point, e);
            }
        } else if (pow < 0) {
            int n = number.number;
            double p = Math.pow(10, -pow);
            if (n < Integer.MAX_VALUE / p) {
                n = (int) (n * p);
                return create(function.apply(this.number, n), point, e);
            }
        }
        return null;
    }

    @Override
    public Expression multiply(Expression expression) {
        if (number == 0) {
            return ZERO;
        } else if (this.equals(ONE)) {
            return expression;
        } else if (expression instanceof Number) {
            Number number = (Number) expression;
            return create(this.number*number.number, this.point + number.point, this.e + number.e);
        }
        return super.multiply(expression);
    }

    @Override
    @Nullable
    public Expression simpleDivide(Expression expression) {
        if (number == 0) {
            return ZERO;
        } else if (expression instanceof Number) {
            if (expression.equals(ONE)) {
                return this;
            }
            Number number = (Number) expression;
            if (this.number%number.number==0) {
                return create(this.number/number.number, this.point - number.point, this.e - number.e);
            } else if (10%number.number==0) {
//                return create(this.number*10/number.number, this.point - number.point + 1, this.e - number.e);
            }
        }
        return super.simpleDivide(expression);
    }

    @Override
    public Expression inline(HashMap<String, Expression> args) {
        return this;
    }

    @Override
    public Expression differentiate(LocalContext context) {
        return Number.ZERO;
    }

    @Override
    public boolean isNumber() {
        return true;
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

    public static Number root(Expression n1, Expression n2) {
        return root(getNumber(n1), getNumber(n2));
    }

    private static Number getNumber(Expression expression) {
        if (expression instanceof Number) {
            return (Number) expression;
        } else if (expression instanceof Element) {
            Element element = (Element) expression;
            if (element.isNumber()) {
                return (Number) element.getAtom();
            }
        }
        return Number.ONE;
    }

    public static Number root(Number n1, Number n2) {
        if (n1.number%n2.number==0) {
            return n2;
        } else if (n2.number%n1.number==0) {
            return n1;
        }
        return Number.ONE;
    }
}
