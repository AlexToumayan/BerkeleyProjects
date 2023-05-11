import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BSTMap<K extends Comparable<K>,V> implements SortedMap<K,V> {

    /**
     * Saves the preferred way of traversing the BST. May be PREORDER or INORDER.
     */
    VisitorOrder defaultVisitOrder;
    /**
     * Node that saves the root of the BST and all of the subtrees.
     */
    private BSTMapNode<K,V> root;
    /**
     * Specifies the current version of the BST map. Useful to determine whether
     * the class's iterator may be used.
     */
    protected int versionNumberBSTmap;
    /**
     * Counter variable detailing the size of the BST map.
     */
    private int size;
    /**
     * Sorted map which stores the statistics provided by {@link #calculateStats()}
     */
    protected SortedMap<Integer, Integer> mapLevelNodes;

    /**
     * Class constructor.
     * <p>
     * Sets the root to null, specifies that the default traversal will be INORDER and initializes
     * the {@link #BSTsize} and {@link #versionNumberBSTmap} counters.
     */
    public BSTMap(){ root = null; defaultVisitOrder=VisitorOrder.INORDER; size=0; versionNumberBSTmap=0; }

    /**
     * Getter for the BST's size variable.
     * @return integer variable of the size.
     */
    public int getSize(){ return size; }


    public void put(K key, V value) throws SortedMapException {
        //Cannot accept null values
        if(key==null || value==null) throw new SortedMapException("Neither 'key' nor 'value' may be null!");

        //If the BST is empty set up the root
        if(root==null){
            root = new BSTMapNode<K, V>(key, value, null, null, null);
            size++; versionNumberBSTmap++;
        } else {
            //Rely on the recursive add method
            boolean added = add(key, value, root);
            //Keep track of the version of the BST
            if(added){ versionNumberBSTmap++; }
        }
    }

    private boolean add(K addKey, V addValue, BSTMapNode<K, V> N) {
        //Compare the comparable keys
        int ct = addKey.compareTo(N.key);
        //There shall be no repeats so do nothing if they're the same
        if(ct != 0){
            if(ct > 0){
                if(N.right == null){
                    //Set the new node to the right of this one (remember to give it the parent)
                    N.right = new BSTMapNode<K, V>(addKey, addValue, null, null, N);
                    size++;
                    return true;
                }else{
                    //Recursive call with node to its right
                    return add(addKey, addValue, N.right);
                }
            }else{ //if (ct < 0)
                if(N.left == null){
                    //Set the new node to this one's left
                    N.left = new BSTMapNode<K, V>(addKey, addValue, null, null, N);
                    size++;
                    return true;
                }else{
                    //Recursive call with node to its left
                    return add(addKey, addValue, N.left);
                }
            }
        }else if(ct == 0){
            //Update the value!
            N.value = addValue;
            return true;
        }else{
            //Return false because it's not getting added!
            return false;
        }
    }

    public V get(K key) throws SortedMapException {
        if(key==null) throw new SortedMapException("Cannot get a 'null' key");
        if(root == null) return null; //Empty root returns a null value
        else{
            //Call helper method to recursively find node
            BSTMapNode<K,V> result = dig(key, root);
            //Return the value of the node if it's not null
            if(result != null) return result.value;
            else return null;
        }
    }

    private BSTMapNode<K,V> dig(K digKey, BSTMapNode<K,V> N){
        if(N == null) return null;

        int ct = digKey.compareTo(N.key);
        if(ct == 0) return N;

        else if(ct>0) return dig(digKey,N.right);
        else return dig(digKey,N.left);
    }

    public boolean remove(K key) throws SortedMapException {
        if(key==null) throw new SortedMapException("Cannot remove a 'null' key");
        if(key.equals(root.key) && root.isLeaf()){
            root = null;
            size--;
            versionNumberBSTmap++;
            return true;
        }else{
            //true if successfully deleted, false if not
            boolean success = takeOut(key, root);
            if(success){ size--; versionNumberBSTmap++; }
            return success;
        }
    }

    private boolean takeOut(K toRemoveKey, BSTMapNode<K,V> N) throws SortedMapException{
        //Reached the end without finding it
        if(N==null){ return false; }

        int ct = toRemoveKey.compareTo(N.key);

        if(ct>0){
            return takeOut(toRemoveKey,N.right);
        }else if(ct<0){
            return takeOut(toRemoveKey,N.left);
        }else{
            //The element has been found (similar keys)
            if(N.isLeaf()){
                //Element is a leaf so just need to erase it
                deleteLeaf(N);
            }else if(N.left == null){
                //Doesn't have a left child
                shiftNodeUp(N.right);
            }else if(N.right == null){
                //Doesn't have a right child
                shiftNodeUp(N.left);
            }else{
                //Neither right nor left are null
                //There is a rightmost descendant
                BSTMapNode<K,V> rightmost = N.left.getRightMostDescendant();
                //"Updating in place makes it easier to handle the case of the root"
                N.value = rightmost.value;
                N.key = rightmost.key;

                //"Now deal with the rightmost node and its children"
                if(rightmost.isLeaf()){
                    deleteLeaf(rightmost);
                }else{
                    shiftNodeUp(rightmost.left);
                }
            }
            return true;
        }
    }

    private void deleteLeaf(BSTMapNode<K,V> L) throws SortedMapException{
        if(!L.isLeaf()) throw new SortedMapException("L is not a leaf");
        if(L == L.parent.left){ //Checking if L is its parent's left child
            L.parent.left = null; //The parent no longer has a left child
        }else{ //Checking if L is its parent's right child
            L.parent.right = null;
        }
    }

    private void shiftNodeUp(BSTMapNode<K,V> N) throws SortedMapException{
        if(N.parent == null) throw new SortedMapException("Can't shift up root node");
        if(!N.parent.hasOneChild()) throw new SortedMapException("Can't shift into full parent");

        N.parent.value = N.value;
        N.parent.left = N.left;
        N.parent.right = N.right;

        if(N.parent.left != null){
            N.parent.left.parent = N.parent;
        }
        if(N.parent.right != null){
            N.parent.right.parent = N.parent;
        }
    }

    private String inOrderTot = "";

    public String toString(){
        if(root == null) return "(null)";
        else{
            //In order traversal
            //Empty out the instance variable
            this.inOrderTot = "";
            return inOrderTraversal(root);
        }
    }

    private String inOrderTraversal(BSTMapNode<K,V> N){
        //Ensure that nothing is null before we call its properties
        if(N.left != null) inOrderTraversal(N.left);
        if(N != null) this.inOrderTot += N.toString()+ "\n";
        if(N.right != null) inOrderTraversal(N.right);

        return this.inOrderTot;
    }

    public void setIteratorDefault(VisitorOrder v) throws SortedMapException {
        if (v != VisitorOrder.PREORDER && v != VisitorOrder.INORDER){
            throw new SortedMapException("Iterator type not implemented");
        }
        defaultVisitOrder = v;
    }

    public Iterator<K> iterator() {
        return new BSTMapInorderIterator<K>(root, this, versionNumberBSTmap, defaultVisitOrder);
    }


    public SortedMap<Integer, Integer> calculateStats() {
        mapLevelNodes = new BSTMap<Integer, Integer>();
        if(root != null){
            fillMapInOrder(root, 1);
            return mapLevelNodes;
        }else return null; //return a null object for an empty BST
    }


    private void fillMapInOrder(BSTMapNode<K,V> N, int level){
        if(N.left != null){

            fillMapInOrder(N.left, level + 1);
            level--;
        }
        try{
            if(N != null){ //don't count the root; already did before
                if(mapLevelNodes.get(level) != null){
                    Integer currentVal = mapLevelNodes.get(level);
                    Integer newVal = new Integer(currentVal.intValue() + 1);
                    mapLevelNodes.put(level, newVal);
                }else{
                    mapLevelNodes.put(level, 1);
                };
            }
        }catch(Exception e){
            System.out.println("Error calculating stats");
        }
        if(N.right != null){
            level++;
            fillMapInOrder(N.right, level);
            level--;
        }
    }

}