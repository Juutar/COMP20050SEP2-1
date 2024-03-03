package comp20050sep2.group1.utils;

import java.util.HashMap;
import java.util.Set;

public class BiMap<K,V> {
    private final HashMap<K,V> keyToValue;
    private final HashMap<V,K> valueToKey;

    public BiMap() {
        keyToValue = new HashMap<>();
        valueToKey = new HashMap<>();
    }

    public int size() {
        return keyToValue.size();
    }

    public void add(K key, V value) {
        keyToValue.put(key, value);
        valueToKey.put(value, key);
    }

    public void removeFromKey(K key) {
        V value = keyToValue.remove(key);
        valueToKey.remove(value);
    }

    public V getValue(K key) {
        return keyToValue.get(key);
    }

    public K getKey(V value) {
        return valueToKey.get(value);
    }

    public Set<K> getKeySet() {
        return keyToValue.keySet();
    }

    public Set<V> getValueSet() {
        return valueToKey.keySet();
    }
}
