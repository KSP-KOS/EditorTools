package ksp.kos.ideaplugin.expressions;

/**
 * Created on 08/04/16.
 *
 * @author ptasha
 */
public class ExpressionVisitor {
    public void visitFunction(Function function) {visit(function);}

    public void visitVariable(Variable variable) {visit(variable);}

    public void visit(Expression expression) {}
}
