package hashmap;

import java.util.*;

public class MyHashMap<K, V> implements Map61B<K, V> {

    private List<K> keys;
    private List<V> values;

    public MyHashMap() {
        keys = new ArrayList<K>();
        values = new ArrayList<V>();
    }

    @Override
    public void clear() {
        keys.clear();
        values.clear();
    }

    @Override
    public boolean containsKey(K key) {
        return keys.contains(key);
    }

    @Override
    public V get(K key) {
        int index = keys.indexOf(key);
        if(index != -1) {
            return values.get(index);
        } else {
            return null;
        }
    }

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public void put(K key, V value) {
        if(containsKey(key)) {
            int index = keys.indexOf(key);
            values.set(index, value);
        } else {
            keys.add(key);
            values.add(value);
        }
    }

    @Override
    public Set<K> keySet() {
        return new HashSet<K>(keys);
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return keys.iterator();
    }
}
