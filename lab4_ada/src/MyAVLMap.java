import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class MyAVLMap<Key extends Comparable<Key>, Value> implements MySortedMap<Key, Value> {
    private Node root;             // root of BST

    private class Node {
        private final Key key;      // sorted by key
        private Value val;         // associated data

        private int height;     //height of node
        private Node left, right;  // left and right subtrees

        public Node(Key key, Value val, int height) {
            this.key = key;
            this.val = val;
            this.height = height;
        }
    }
    /**
     * Initializes an empty Map.
     */
    public MyAVLMap() {
        root = null;
    }
    /**
     * Searches if a key is contained in the Map.
     *
     * @param key - the key to be searched
     * @return true if this map contains a mapping for the specified key
     */
    @Override
    public boolean containsKey(Key key) {
        return get(key) != null;
    }
    /**
     * Searches the associated value of a key.
     *
     * @param key - the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key
     */
    @Override
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key){
        if(x == null) return null;
        int cmp = key.compareTo(x.key);
        if(cmp < 0) return get(x.left, key);
        else if(cmp > 0) return get(x.right, key);
        else return x.val;
    }

    /**
     * @return all keys as an Iterable in ascending order
     */
    @Override
    public Iterable<Key> getKeys() {
        Queue<Key> q= new LinkedList<Key>();
        keys(root, q);
        return q;
    }
    private void keys(Node x, Queue<Key> q) {
        if (x==null) return;
        keys(x.left, q);
        q.add(x.key);
        keys(x.right,q);
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a value for the key, the old value
     * is replaced by the specified value.
     *
     * @param key - the new key
     * @param val - the new value
     */
    @Override
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        if (val == null) {
            remove(key);
            return;
        }
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 0);
        if (key.compareTo(x.key)<0) {
            x.left = put(x.left, key, val);
        } else if (key.compareTo(x.key)>0) {
            x.right = put(x.right, key, val);
        } else {
            x.val = val;
            return x;
        }
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return balance(x);
    }

    private int height(Node x) {
        if (x == null) return -1;
        return x.height;
    }

    /**
     * Restores the AVL balance property of the subtree.
     *
     * @param x the root of the subtree
     * @return the subtree with restored AVL balance property
     */
    private Node balance(Node x) {
        if (balanceFactor(x) < -1) {
            if (balanceFactor(x.right) > 0) {
                x.right = rotateRight(x.right);
            }
            x = rotateLeft(x);
        } else if (balanceFactor(x) > 1) {
            if (balanceFactor(x.left) < 0) {
                x.left = rotateLeft(x.left);
            }
            x = rotateRight(x);
        }
        return x;
    }

    /**
     * The balance factor is defined  as the difference between
     * height of the left subtree and height of the right subtree
     * A subtree with a balance factor of -1, 0 or 1 is AVL
     *
     * @param x the root of a subtree
     * @return the balance factor of the subtree
     */
    private int balanceFactor(Node x) {
        return height(x.left) - height(x.right);
    }

    /**
     * Rotates the given subtree to the right.
     *
     * @param y the root of the subtree
     * @return the right rotated subtree
     */
    private Node rotateRight(Node y) {
        //System.out.println("rotate right at " + y.key);
        Node x = y.left;
        y.left = x.right;
        x.right = y;
        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return x;
    }

    /**
     * Rotates the given subtree to the left.
     *
     * @param x the root of the subtree
     * @return the left rotated subtree
     */
    private Node rotateLeft(Node x) {
        //System.out.println("rotate left at " + x.key);
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        return y;
    }

    /**
     * Removes the mapping containing given key from this map if it is present.
     *
     * @param key - key whose mapping is to be removed
     */
    @Override
    public void remove(Key key) {
        if (key == null) throw new IllegalArgumentException("calls remove() with a null key");
        root = remove(root, key);
    }

    private Node remove(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = remove(x.left, key);
        } else if (cmp > 0) {
            x.right = remove(x.right, key);
        } else {
            if (x.left == null) return x.right;
            if (x.right == null) return x.left;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return balance(x);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return balance(x);
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        else return min(x.left);
    }

    /**
     * @return all entries as an Iterable in ascending order of keys
     */
    @Override
    public Iterable<Map.Entry<Key, Value>> getEntries(){
        Queue<Map.Entry<Key, Value>> q= new LinkedList<Map.Entry<Key, Value>>();
        getEntries(root, q);
        return q;
    }

    private void getEntries(Node x, Queue<Map.Entry<Key, Value>> q){
        if (x == null) return;
        getEntries(x.left, q);
        q.add(new AbstractMap.SimpleEntry<>(x.key, x.val));
        getEntries(x.right, q);
    }

    public Key firstKey(){
        if(root == null) return null;
        return min(root).key;
    }

    public Key lastKey(){
        if(root == null) return null;
        return max(root).key;
    }

    private Node max(Node x){
        if(x.right == null) return x;
        else return max(x.right);
    }
}