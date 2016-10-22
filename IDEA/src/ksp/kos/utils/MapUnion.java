package ksp.kos.utils;

import com.google.common.collect.Sets;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

/**
 * Created on 17/04/16.
 *
 * @author ptasha
 */
public class MapUnion<K, V> extends TrueAbstractMap<K, V> {
    private final Map<K, V> map1;
    private final Map<K, V> map2;

    public MapUnion(Map<K, V> map1, Map<K, V> map2) {
        this.map1 = map1;
        this.map2 = map2;
    }

    @Override
    public V get(Object key) {
        V value = map1.get(key);
        if (value==null) {
            return map2.get(key);
        }
        return value;
    }

    @NotNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        return Sets.union(map1.entrySet(), map2.entrySet()); // TODO may cause perf issues
    }
}
