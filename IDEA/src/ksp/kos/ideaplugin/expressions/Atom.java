package ksp.kos.ideaplugin.expressions;

import com.intellij.lang.ASTNode;
import ksp.kos.ideaplugin.psi.*;

import java.util.Collections;
import java.util.Set;

/**
 * Created on 28/01/16.
 *
 * @author ptasha
 */
public abstract class Atom extends Expression {
    public static Atom parse(KerboScriptExpr expr) throws SyntaxException {
        Expression atom = Expression.parse(expr);
        if (atom instanceof Atom) {
            return (Atom) atom;
        }
        throw new SyntaxException("Atom is expected: found "+expr+": "+expr.getText());
    }

    public static Atom parse(KerboScriptAtom atom) throws SyntaxException {
        ASTNode identifier = atom.getNode().findChildByType(KerboScriptTypes.IDENTIFIER);
        if (identifier!=null) {
            return new Variable(identifier.getText());
        } else if (atom.getNode().findChildByType(KerboScriptTypes.BRACKETOPEN)!=null) {
            return new Escaped(Expression.parse(atom.getExpr()));
        } else if (atom.getExpr() instanceof KerboScriptNumber) {
            return new Number((KerboScriptNumber) atom.getExpr());
        } else if (atom.getExpr() instanceof KerboScriptSciNumber) {
            return new Number((KerboScriptSciNumber) atom.getExpr());
        }
        throw new SyntaxException("Invalid atom: "+atom.getText());
    }

    public static Atom toAtom(Expression expression) {
        if (expression instanceof Atom) {
            return (Atom) expression;
        } else {
            return new Escaped(expression);
        }
    }

    public boolean isAddition() {
        return false;
    }

    @Override
    public Set<String> getVariableNames() {
        return Collections.emptySet();
    }
}
