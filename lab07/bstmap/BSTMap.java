package bstmap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.security.InvalidParameterException;
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private BSTNode root;
    private int size;

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        if (get(key) != null)
            return true;
        else
            return false;
    }

    @Override
    public V get(K key) {
        if(root == null){
            return null;
        }

        BSTNode node = root.find(key);
        if (node == null)
            return null;
        else
            return node.getValue();
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return size;
    }

    @Override
    public void put(K key, V value) {
        if (root == null) {
            root = new BSTNode(key, value);
        } else {
            root.insert(key, value);
        }
        size += 1;
    }

    @Override
    public V remove(K key) {
        V value = get(key);
        if (value == null) {
            return null;
        } else {
            root = root.remove(key);
            size -= 1;
            return value;
        }
    }

    @Override
    public V remove(K key, V value) {
        V testValue = get(key);
        if (testValue == null || testValue != value) {
            return null;
        } else {
            root = root.remove(key);
            size -= 1;
            return value;
        }
    }

    @Override
    public Set<K> keySet() {
        if (root == null)
            return null;
        else
            return new HashSet<K>(root.inOrder(new ArrayList<K>()));
    }

    public void printInOrder() {
        ArrayList<K> keys = getKeysInOrder();
        for (int i = 0; i < keys.size(); i += 1) {
            System.out.println(keys.get(i));
        }
    }

    public ArrayList<K> getKeysInOrder() {
        if (root == null)
            return null;

        return root.inOrder(new ArrayList<K>());
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }


    private class BSTNode {
        private BSTNode left;
        private BSTNode right;
        private K key;
        private V val;

        public ArrayList<K> inOrder(ArrayList<K> list) {
            if (left != null)
                left.inOrder(list);
            list.add(key);
            if (right != null)
                right.inOrder(list);
            return list;
        }

        private BSTNode(K key, V val, BSTNode left, BSTNode right) {
            key = key;
            val = val;
            left = left;
            right = right;
        }

        public BSTNode(K key, V val) {
            this(key, val, null, null);
        }

        public V getValue() {
            return val;
        }

        public K getKey() {
            return key;
        }

        public void left() {
            return;
        }

        public BSTNode right() {
            return right;
        }

        public BSTNode find(K k) {
            if (k == null)
                return null;

            if (k.equals(key))
                return this;

            if (k.compareTo(key) < 0) {
                if (left != null)
                    return left.find(k);
                else
                    return null;
            } else {
                if (right != null)
                    return right.find(k);
                else
                    return null;
            }

        }

        public boolean ispresent(K key) {
            return find(key) != null;
        }


        public BSTNode insert(K k, V val) {
            if (k == null)
                throw new InvalidParameterException("No key present");

            if (key.equals(k))
                throw new InvalidParameterException(
                        "Cannot insert duplicate key");

            if (key.compareTo(k) < 0) {
                if (left == null) {
                    left = new BSTNode(k, val);
                } else {
                    left = left.insert(k, val);
                }
            } else {
                if (right == null) {
                    right = new BSTNode(k, val);
                } else {
                    right = right.insert(k, val);
                }
            }
            return this;
        }


        public BSTNode remove(K k) {

            if (k == null)
                throw new InvalidParameterException("No key present");

            if (k.equals(key)) {

                if (left == null && right == null)
                    return null;

                if (left == null && right != null)
                    return right;

                if (left != null && right == null)
                    return left;

                BSTNode tracker = right;
                while (tracker.left != null) {
                    tracker = tracker.left;
                }
                key = tracker.getKey();
                val = tracker.getValue();
                right = right.remove(key);
                return this;
            }

            if (key.compareTo(key) < 0) {
                left = left.remove(k);
            } else {
                right = right.remove(k);
            }
            return this;

        }

    }

}
