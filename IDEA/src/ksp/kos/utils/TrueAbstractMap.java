package ksp.kos.utils;

import java.util.AbstractMap;

/**
 * Created on 09/10/16.
 *
 * @author ptasha
 */
public abstract class TrueAbstractMap<K, V> extends AbstractMap<K, V> {
    @Override
    public boolean containsKey(Object key) {
        return get(key)!=null;
    }

    @Override
    public abstract V get(Object key);

    @Override
    public V remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
