import java.util.Random;

public class GenericBST<K extends Comparable<K>, V> {

    private class Node {
        K key;
        V val;
        Node left, right;

        Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    private Node root;

    public GenericBST() {
        root = null;
    }

    public void inorder() {
        inorder(root);
    }

    private void inorder(Node x) {
        if (x == null) return;
        inorder(x.left);
        System.out.print(x.key + " ");
        inorder(x.right);
    }

    public void preorder() {
        preorder(root);
    }

    private void preorder(Node x) {
        if (x == null) return;
        System.out.print(x.key + " ");
        preorder(x.left);
        preorder(x.right);
    }

    public void postorder() {
        postorder(root);
    }

    private void postorder(Node x) {
        if (x == null) return;
        postorder(x.left);
        postorder(x.right);
        System.out.print(x.key + " ");
    }

    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) return 0;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    public boolean isBST() {
        return isBST(root, null, null);
    }

    private boolean isBST(Node x, K min, K max) {
        if (x == null) return true;
        if (min != null && x.key.compareTo(min) <= 0) return false;
        if (max != null && x.key.compareTo(max) >= 0) return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    public void spoilValues() {
        spoilValues(root);
    }

    private void spoilValues(Node x) {
        if (x == null) return;
        Random rand = new Random();
        x.val = (V) (Integer) rand.nextInt(100); // Assuming V is Integer for simplicity
        spoilValues(x.left);
        spoilValues(x.right);
    }

    public K successor(K key) {
        Node x = successor(root, key);
        if (x == null) {
            return null;
        } else {
            return x.key;
        }
    }

    private Node successor(Node x, K key) {
        if (x == null) return null;
        if (key.compareTo(x.key) >= 0) {
            return successor(x.right, key);
        } else {
            Node t = successor(x.left, key);
            if (t != null) {
                return t;
            } else {
                return x;
            }
        }
    }

    public K predecessor(K key) {
        Node x = predecessor(root, key);
        if (x == null) {
            return null;
        } else {
            return x.key;
        }
    }

    private Node predecessor(Node x, K key) {
        if (x == null) return null;
        if (key.compareTo(x.key) <= 0) {
            return predecessor(x.left, key);
        } else {
            Node t = predecessor(x.right, key);
            if (t != null) {
                return t;
            } else {
                return x;
            }
        }
    }

    public void put(K key, V val) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        root = put(root, key, val);
    }

    private Node put(Node x, K key, V val) {
        if (x == null) return new Node(key, val);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else x.val = val;
        return x;
    }

    public void delete(K key) {
        if (key == null) throw new IllegalArgumentException("calls delete() with a null key");
        root = deleteRecursive(root, key);
    }

    private Node deleteRecursive(Node x, K key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = deleteRecursive(x.left, key);
        else if (cmp > 0) x.right = deleteRecursive(x.right, key);
        else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        return x;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        else return min(x.left);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        return x;
    }

    public static void main(String[] args) {
        GenericBST<Integer, Integer> bst = new GenericBST<>();
        bst.put(15, 15);
        bst.put(10, 10);
        bst.put(20, 20);
        bst.put(8, 8);
        bst.put(12, 12);
        bst.put(17, 17);
        bst.put(25, 25);

        System.out.println("Inorder:");
        bst.inorder();
        System.out.println("\nPreorder:");
        bst.preorder();
        System.out.println("\nPostorder:");
        bst.postorder();
        System.out.println("\nHeight: " + bst.height());
        System.out.println("Is BST: " + bst.isBST());

        bst.spoilValues();
        System.out.println("After spoiling values:");
        System.out.println("Is BST: " + bst.isBST());

        System.out.println("Successor of 15: " + bst.successor(15));
        System.out.println("Predecessor of 15: " + bst.predecessor(15));
    }
}