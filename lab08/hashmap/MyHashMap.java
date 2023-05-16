package hashmap;

import java.util.*;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private static final int DEFAULT_SIZE = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    private class Node {
        K key;
        V value;
        Node next;

        Node(K k, V v, Node n) {
            key = k;
            value = v;
            next = n;
        }
    }

    private Object[] buckets;
    private int size;
    private double loadFactor;

    public MyHashMap() {
        this(DEFAULT_SIZE, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialSize, double loadFactor) {
        buckets = new Object[initialSize];
        this.loadFactor = loadFactor;
        size = 0;
    }

    @Override
    public void clear() {
        buckets = new Object[buckets.length];
        size = 0;
    }


    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % buckets.length;
    }

    @Override
    public boolean containsKey(K key) {
        int i = hash(key);
        for (Node x = (Node) buckets[i]; x != null; x = x.next) {
            if (key.equals(x.key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        int i = hash(key);
        for (Node x = (Node) buckets[i]; x != null; x = x.next) {
            if (key.equals(x.key)) {
                return x.value;
            }
        }
        return null;
    }

    private void resize(int capacity) {
        MyHashMap<K, V> temp = new MyHashMap<>(capacity, loadFactor);
        for (int i = 0; i < buckets.length; i++) {
            for (Node x = (Node) buckets[i]; x != null; x = x.next) {
                temp.put(x.key, x.value);
            }
        }
        this.buckets = temp.buckets;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Null key not allowed");
        }

        if (size / buckets.length > loadFactor) {
            resize(2 * buckets.length);
        }

        int i = hash(key);
        for (Node x = (Node) buckets[i]; x != null; x = x.next) {
            if (key.equals(x.key)) {
                x.value = value;
                return;
            }
        }

        buckets[i] = new Node(key, value, (Node) buckets[i]);
        size++;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (Object bucket : buckets) {
            for (Node x = (Node) bucket; x != null; x = x.next) {
                set.add(x.key);
            }
        }
        return set;
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("Remove operation not supported");
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("Remove operation not supported");
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    @Override
    public int size() {
        return size;
    }
}
