package ksp.kos.ideaplugin.expressions;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiWhiteSpace;
import ksp.kos.ideaplugin.psi.KerboScriptExpr;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * Created on 31/01/16.
 *
 * @author ptasha
 */
public abstract class MultiExpression<O extends Enum<O> & MultiExpression.Op, E extends Expression> extends Expression {
    private final E zero = toElement(zero());
    private final E one = toElement(one());

    protected final LinkedList<Item<O, E>> items = new LinkedList<>();

    protected MultiExpression(Expression... expressions) {
        for (Expression expression : expressions) {
            addItem(new Item<>(defaultOperator(), toElement(expression)));
        }
    }

    protected MultiExpression(KerboScriptExpr expr) throws SyntaxException {
        int state = 0;
        O operation = defaultOperator();
        for (ASTNode node : expr.getNode().getChildren(null)) {
            PsiElement element = node.getPsi();
            if (element instanceof PsiWhiteSpace) {
                continue;
            }
            switch (state) {
                case 0:
                    if (element instanceof KerboScriptExpr) {
                        addItem(new Item<>(operation, parseElement((KerboScriptExpr) element)));
                    } else {
                        throw new SyntaxException("Expression is required: found "+element+": "+element.getText());
                    }
                    state = 1;
                    break;
                case 1:
                    operation = parseOperation(element);
                    state = 0;
                    break;
            }
        }
    }

    protected O parseOperation(PsiElement element) throws SyntaxException {
        String text = element.getText();
        for (O o : operators()) {
            if (o.getText().equals(text)) {
                return o;
            }
        }
        throw new SyntaxException("One of "+ Arrays.toString(operators()) +" is required: found "+element+": "+text);
    }

    @SuppressWarnings("unchecked")
    protected E parseElement(KerboScriptExpr element) throws SyntaxException {
        return toElement(Expression.parse(element));
    }

    protected O defaultOperator() {
        return operators()[0];
    }

    @NotNull
    protected abstract O[] operators();

    @Override
    public String getText() {
        String text = "";
        boolean first = true;
        for (Item<O, E> item : items) {
            if (!first) {
                text+= item.operation;
            } else {
                first = false;
            }
            text += item.expression.getText();
        }
        return text;
    }

    public static class Item<O extends Enum<O>, E extends Expression> {
        private final O operation;
        private E expression;

        public Item(O operation, E expression) {
            this.operation = operation;
            this.expression = expression;
        }

        public O getOperation() {
            return operation;
        }

        public E getExpression() {
            return expression;
        }

        public void setExpression(E expression) {
            this.expression = expression;
        }
    }

    public interface Op extends Operation {
        String getText();
    }

    public interface Operation
            extends BiFunction<Expression, Expression, Expression> {}

    @SuppressWarnings("unchecked")
    @Override
    public Expression simplify() {
        Expression simple = one().copy();
        for (Item<O, E> item : items) {
            Expression simpleItem = item.getExpression().simplify();
            simple = item.getOperation().apply(simple, simpleItem);
        }
        return simple;
    }

    protected abstract Expression singleItemExpression(Item<O, E> item);

    protected Expression checkEmpty() {
        if (items.isEmpty()) {
            return one().copy();
        } if (items.size()==1) {
            return singleItemExpression(items.get(0));
        }
        return this;
    }

    protected Expression addExpression(Expression expression) {
        return addExpression(defaultOperator(), expression);
    }

    protected Expression addExpression(O operator, Expression expression) {
        MultiExpression<O, E> associate = associate(expression);
        if (associate==null) {
            if (!addItem(new Item<>(operator, toElement(expression)))) {
                return zero().copy();
            }
            return checkEmpty();
        } else {
            return addExpression(operator, associate);
        }
    }

    @NotNull
    private Expression addExpression(O operator, MultiExpression<O, E> associate) {
        for (Item<O, E> item : associate.items) {
            if (!addItem(new Item<>(merge(operator, item.getOperation()),  item.getExpression()))) {
                return zero().copy();
            }
        }
        return checkEmpty();
    }

    private boolean addItem(Item<O, E> newItem) {
        return addItem(newItem, items::addLast);
    }

    private boolean addItemFirst(Item<O, E> newItem) {
        return addItem(newItem, items::addFirst);
    }

    private boolean addItem(Item<O, E> newItem, Consumer<Item<O, E>> consumer) {
        if (nullifyEverything(newItem)) {
            items.clear();
            items.add(newItem);
            return false;
        }
        if (omit(newItem)) {
            return true;
        }
        if (this instanceof ConsumeSupported) {
            if (this.consume(newItem)) {
                return true;
            }
        }
        consumer.accept(newItem);
        return true;
    }

    protected Expression addExpressionLeft(Expression expression) {
        MultiExpression<O, E> associate = associate(expression);
        if (associate==null) {
            if (!addItemFirst(new Item<>(defaultOperator(), toElement(expression)))) {
                return zero().copy();
            }
            return checkEmpty();
        } else {
            return associate.addExpression(this);
        }
    }

    @SuppressWarnings("unchecked")
    protected MultiExpression<O, E> associate(Expression expression) {
        if (expression.getClass()==this.getClass()) {
            return (MultiExpression<O, E>) expression;
        } else if (expression instanceof Escaped) {
            return associate(((Escaped) expression).getExpression());
        } else if (expression instanceof Element && ((Element) expression).isSimple()) {
            return associate(((Element) expression).getAtom());
        }
        return null;
    }

    protected boolean consume(Item<O, E> newItem) {
        for (Item<O, E> item : items) {
            if (consumeItem(item, newItem)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    protected boolean consumeItem(Item<O, E> item, Item<O, E> newItem) {
        return ((ConsumeSupported<O, E>) this).consume(item, newItem);
    }

    protected O merge(O operation1, O operation2) {
        if (operation1==operation2) {
            return defaultOperator();
        } else if (operation1==defaultOperator()) {
            return operation2;
        } else {
            return operation1;
        }
    }

    protected boolean nullifyEverything(Item<O, E> item) {
        return item.operation==defaultOperator() && item.expression.equals(zero);
    }

    protected boolean omit(Item<O, E> item) {
        return item.expression.equals(one);
    }

    protected Expression zero() {
        return null;
    }

    protected abstract Expression one();

    @SuppressWarnings("unchecked")
    protected E toElement(Expression expression) {
        return (E) expression;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Expression copy() {
        try {
            MultiExpression<O, E> copy = this.getClass().newInstance();
            for (Item<O, E> item : items) {
                copy.items.add(new Item<>(item.operation, (E)item.expression.copy()));
            }
            return copy;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
