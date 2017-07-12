package ksp.kos.ideaplugin.expressions;

/**
 * Created on 31/01/16.
 *
 * @author ptasha
 */
public interface ConsumeSupported<O extends Enum<O> & MultiExpression.Op, E extends Expression> {
    /**
     * Merge two items together, if it's possible.
     * Returns null if it's not.
     *
     * E.g. 2 * 3 -> 6, x^2 * x -> x^3, x * y -> null
     *
     * @param item item which is already in the expression
     * @param newItem new item to add
     * @return merge of two items, null - if not possible
     */
    MultiExpression.Item<O, E> consume(MultiExpression.Item<O, E> item, MultiExpression.Item<O, E> newItem);
}
