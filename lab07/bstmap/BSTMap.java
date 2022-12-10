package bstmap;
import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root;
    private int size;
    @Override
    public Iterator<K> iterator() {
        return null;
    }

    private class Node {
        private K key;
        private V val;
        private Node A, B;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    public BSTMap() {
        size = 0;
    }

    @Override
    public void clear() {
        root = null;


        size = 0;
    }

    @Override
    public boolean containsKey(K key) {

        return get(key) != null;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        Node n = root;
        while (n != null) {
            int c = key.compareTo(n.key);
            if (c == 0) {
                return n.val;
            } else if (c > 0) {
                n = n.A;
            } else {
                n = n.B;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        root = put(key, value, root);
    }

    private Node put(K key, V value, Node n) {
        if (n == null) {
            size++;
            return new Node(key, value);
        }

        int comparison = key.compareTo(n.key);
        if (comparison == 0) {
            n.val = value;
        } else if (comparison > 0) {
            n.B = put(key, value, n.B);
        } else {
            n.A = put(key, value, n.A);
        }
        return n;
    }


    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        return null;
    }
    public void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(Node n) {
        if (n == null) {
            return;
        }
        printInOrder(n.A);
        System.out.print(n.key + " ");
        printInOrder(n.B);
    }

}
