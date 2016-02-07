package ksp.kos.ideaplugin.expressions;

/**
 * Created on 31/01/16.
 *
 * @author ptasha
 */
public interface ConsumeSupported<O extends Enum<O> & MultiExpression.Op, E extends Expression> {
    boolean consume(MultiExpression.Item<O, E> item, MultiExpression.Item<O, E> newItem);
}
