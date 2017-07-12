package ksp.kos.utils;

import java.util.Map;

/**
 * Created on 10/04/16.
 *
 * @author ptasha
 */
public class MapBuilder<K, V> {
    private final Map<K, V> map;

    public MapBuilder(Map<K, V> map) {
        this.map = map;
    }

    public MapBuilder<K, V> put(K key, V value) {
        map.put(key, value);
        return this;
    }

    public Map<K, V> getMap() {
        return map;
    }
}
