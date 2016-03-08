package ksp.kos.ideaplugin.expressions;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.util.containers.HashMap;
import ksp.kos.ideaplugin.psi.KerboScriptExpr;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiFunction;

/**
 * Created on 31/01/16.
 *
 * @author ptasha
 */
public abstract class MultiExpression<O extends Enum<O> & MultiExpression.Op, E extends Expression> extends Expression {
    protected final List<Item<O, E>> items;

    protected MultiExpression(List<Item<O, E>> items) {
        this.items = items;
    }

    protected MultiExpression(Expression... expressions) {
        items = createBuilder().addExpressions(expressions).createItems();
    }

    protected MultiExpression(KerboScriptExpr expr) throws SyntaxException {
        items = createBuilder().parse(expr).createItems();
    }

    @Override
    public String getText() {
        String text = "";
        boolean first = true;
        for (Item<O, E> item : items) {
            if (!first) {
                text += item.operation;
            } else {
                first = false;
            }
            text += item.expression.getText();
        }
        return text;
    }

    public static class Item<O extends Enum<O>, E extends Expression> {
        private final O operation;
        private final E expression;

        private Item(O operation, E expression) {
            this.operation = operation;
            this.expression = expression;
        }

        public O getOperation() {
            return operation;
        }

        public E getExpression() {
            return expression;
        }
    }

    public interface Op extends Operation {
        String getText();
    }

    public interface Operation
            extends BiFunction<Expression, Expression, Expression> {
    }

    public abstract static class MultiExpressionBuilder<O extends Enum<O> & MultiExpression.Op, E extends Expression> {
        private final Class<? extends MultiExpression<O, E>> expressionClass;
        private final E zero = toElement(zero());
        private final E one = toElement(one());

        protected final LinkedList<Item<O, E>> items = new LinkedList<>();

        public MultiExpressionBuilder(Class<? extends MultiExpression<O, E>> expressionClass) {
            this.expressionClass = expressionClass;
        }

        public Item<O, E> createItem(O operation, Expression expression) {
            return new Item<>(operation, toElement(expression));
        }

        public MultiExpressionBuilder<O, E> addExpressions(Expression... expressions) {
            for (Expression expression : expressions) {
                addExpression(defaultOperator(), expression);
            }
            return this;
        }

        protected MultiExpressionBuilder<O, E> addExpression(Expression expression) {
            addExpression(defaultOperator(), expression);
            return this;
        }

        protected MultiExpressionBuilder<O, E> addExpression(O operator, Expression expression) {
            MultiExpression<O, E> associate = associate(expression);
            if (associate == null) {
                addItem(createItem(operator, toElement(expression)));
            } else {
                addExpression(operator, associate);
            }
            return this;
        }

        private void addExpression(O operator, MultiExpression<O, E> associate) {
            for (Item<O, E> item : associate.items) {
                addItem(createItem(merge(operator, item.getOperation()), item.getExpression()));
            }
        }

        protected void addItem(Item<O, E> newItem) {
            if (nullifyEverything(newItem)) {
                items.clear();
                items.add(newItem);
                return;
            }
            if (omit(newItem)) {
                return;
            }
            if (this instanceof ConsumeSupported) {
                Item<O, E> consumed = this.consume(newItem);
                if (consumed!=null) {
                    addExpression(consumed.getOperation(), consumed.getExpression());
                    return;
                }
            }
            items.add(newItem);
        }

        public MultiExpressionBuilder<O, E> addItems(List<Item<O, E>> items) {
            for (Item<O, E> item : items) {
                this.addItem(item);
            }
            return this;
        }

        protected Expression zero() {
            return null;
        }

        protected abstract Expression one();

        protected boolean nullifyEverything(Item<O, E> item) {
            return item.operation == defaultOperator() && item.expression.equals(zero);
        }

        protected boolean omit(Item<O, E> item) {
            return item.expression.equals(one);
        }

        protected Item<O, E> consume(Item<O, E> newItem) {
            for (Iterator<Item<O, E>> iterator = items.iterator(); iterator.hasNext(); ) {
                Item<O, E> item = iterator.next();
                item = consumeItem(item, newItem);
                if (item != null) {
                    iterator.remove();
                    return item;
                }
            }
            return null;
        }

        @SuppressWarnings("unchecked")
        protected Item<O, E> consumeItem(Item<O, E> item, Item<O, E> newItem) {
            return ((ConsumeSupported<O, E>) this).consume(item, newItem);
        }

        @SuppressWarnings("unchecked")
        protected MultiExpression<O, E> associate(Expression expression) {
            if (expression.getClass() == expressionClass) {
                return (MultiExpression<O, E>) expression;
            } else if (expression instanceof Escaped) {
                return associate(((Escaped) expression).getExpression());
            } else if (expression instanceof Element && ((Element) expression).isSimple()) {
                return associate(((Element) expression).getAtom());
            }
            return null;
        }

        protected abstract Expression singleItemExpression(Item<O, E> item);

        @NotNull
        protected abstract O[] operators();

        protected O defaultOperator() {
            return operators()[0];
        }

        protected O merge(O operation1, O operation2) {
            if (operation1 == operation2) {
                return defaultOperator();
            } else if (operation1 == defaultOperator()) {
                return operation2;
            } else {
                return operation1;
            }
        }

        @SuppressWarnings("unchecked")
        protected E toElement(Expression expression) {
            return (E) expression;
        }

        public MultiExpressionBuilder<O, E> parse(KerboScriptExpr expr) throws SyntaxException {
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
                            addItem(createItem(operation, parseElement((KerboScriptExpr) element)));
                        } else {
                            throw new SyntaxException("Expression is required: found " + element + ": " + element.getText());
                        }
                        state = 1;
                        break;
                    case 1:
                        operation = parseOperation(element);
                        state = 0;
                        break;
                }
            }
            return this;
        }

        protected O parseOperation(PsiElement element) throws SyntaxException {
            String text = element.getText();
            for (O o : operators()) {
                if (o.getText().equals(text)) {
                    return o;
                }
            }
            throw new SyntaxException("One of " + Arrays.toString(operators()) + " is required: found " + element + ": " + text);
        }

        @SuppressWarnings("unchecked")
        protected E parseElement(KerboScriptExpr element) throws SyntaxException {
            return toElement(Expression.parse(element));
        }

        public Expression createExpression() {
            if (items.isEmpty()) {
                return one();
            }
            if (items.size() == 1) {
                return singleItemExpression(items.get(0));
            }
            return createExpression(createItems());
        }

        protected abstract MultiExpression<O, E> createExpression(List<Item<O, E>> items);

        public List<Item<O, E>> createItems() {
            normalize();
            return Collections.unmodifiableList(items);
        }

        protected void normalize() {
        }
    }

    public abstract MultiExpressionBuilder<O, E> createBuilder();

    protected MultiExpressionBuilder<O, E> createBuilder(MultiExpression<O, E> expression) {
        return createBuilder().addItems(expression.items);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Expression simplify() {
        MultiExpressionBuilder<O, E> builder = createBuilder();
        for (Item<O, E> item : items) {
            builder.addExpression(item.getOperation(), item.getExpression().simplify());
        }
        return builder.createExpression();
    }

    protected Expression addExpression(Expression expression) {
        return createBuilder(this).addExpression(expression).createExpression();
    }

    protected Expression addExpression(O operator, Expression expression) {
        return createBuilder(this).addExpression(operator, expression).createExpression();
    }

    protected Expression addExpressionLeft(Expression expression) {
        return createBuilder().addExpression(expression).addItems(items).createExpression();
    }

    @Override
    public Expression inline(HashMap<String, Expression> args) {
        MultiExpressionBuilder<O, E> builder = createBuilder();
        for (Item<O, E> item : items) {
            builder.addExpression(item.getOperation(), item.getExpression().inline(args));
        }
        return builder.createExpression();
    }
}
