public class IntegerBTree {

    private final int T; // the minimum degree of the B-Tree

    class BTreeNode {
        int n;    // current number of keys contained in node
        Integer key[];   // maximum 2T-1 keys
        BTreeNode child[]; // maximum 2T children
        boolean leaf;

        BTreeNode(int t, boolean leaf) {
            this.leaf = leaf;
            this.key = new Integer[2 * t - 1];
            this.child = new BTreeNode[2 * t];
            this.n = 0;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(" [ ");
            for (int i = 0; i < n; i++)
                sb.append(" " + key[i]);
            sb.append(" ] ");
            return sb.toString();
        }
    }

    private BTreeNode root; // root of tree

    public IntegerBTree(int t) {
        T = t;
        root = new BTreeNode(t, true);
    }

    public void buildConfig() {
        root = new BTreeNode(T, false);
        root.n = 5;
        root.key[0] = 5;
        root.key[1] = 12;
        root.key[2] = 16;
        root.key[3] = 23;
        root.key[4] = 30;

        BTreeNode child1 = new BTreeNode(T, true);
        child1.n = 3;
        child1.key[0] = 1;
        child1.key[1] = 2;
        child1.key[2] = 3;

        BTreeNode child2 = new BTreeNode(T, true);
        child2.n = 5;
        child2.key[0] = 6;
        child2.key[1] = 7;
        child2.key[2] = 9;
        child2.key[3] = 10;
        child2.key[4] = 11;

        BTreeNode child3 = new BTreeNode(T, true);
        child3.n = 2;
        child3.key[0] = 13;
        child3.key[1] = 14;

        BTreeNode child4 = new BTreeNode(T, true);
        child4.n = 3;
        child4.key[0] = 18;
        child4.key[1] = 19;
        child4.key[2] = 20;

        BTreeNode child5 = new BTreeNode(T, true);
        child5.n = 2;
        child5.key[0] = 24;
        child5.key[1] = 26;

        BTreeNode child6 = new BTreeNode(T, true);
        child6.n = 2;
        child6.key[0] = 32;
        child6.key[1] = 36;

        root.child[0] = child1;
        root.child[1] = child2;
        root.child[2] = child3;
        root.child[3] = child4;
        root.child[4] = child5;
        root.child[5] = child6;
    }
    public boolean contains(int key) {
        return contains(root, key);
    }

    private boolean contains(BTreeNode node, int key) {
        int i = 0;
        while (i < node.n && key > node.key[i]) {
            i++;
        }
        if (i < node.n && key == node.key[i]) {
            return true;
        }
        if (node.leaf) {
            return false;
        }
        return contains(node.child[i], key);
    }

    public int height() {
        return height(root);
    }

    private int height(BTreeNode node) {
        if (node.leaf) {
            return 1;
        }
        return 1 + height(node.child[0]);
    }

    public int level(int key) {
        return level(root, key, 1);
    }

    private int level(BTreeNode node, int key, int level) {
        int i = 0;
        while (i < node.n && key > node.key[i]) {
            i++;
        }
        if (i < node.n && key == node.key[i]) {
            return level;
        }
        if (node.leaf) {
            return -1;
        }
        return level(node.child[i], key, level + 1);
    }

    public int min() {
        BTreeNode node = root;
        while (!node.leaf) {
            node = node.child[0];
        }
        return node.key[0];
    }

    public int max() {
        BTreeNode node = root;
        while (!node.leaf) {
            node = node.child[node.n];
        }
        return node.key[node.n - 1];
    }

    public int successor(int key) {
        BTreeNode node = root;
        BTreeNode successorNode = null;
        while (node != null) {
            int i = 0;
            while (i < node.n && key >= node.key[i]) {
                i++;
            }
            if (i < node.n) {
                successorNode = node;
                node = node.child[i];
            } else {
                node = node.child[node.n];
            }
        }
        if (successorNode != null) {
            for (int j = 0; j < successorNode.n; j++) {
                if (successorNode.key[j] > key) {
                    return successorNode.key[j];
                }
            }
        }
        return -1; // No successor found
    }

    public int predecessor(int key) {
        BTreeNode node = root;
        while (node != null) {
            int i = node.n - 1;
            while (i >= 0 && key <= node.key[i]) {
                i--;
            }
            if (i >= 0) {
                return node.key[i];
            }
            node = node.leaf ? null : node.child[i + 1];
        }
        return -1; // No predecessor found
    }

    public void inorder() {
        inorder(root);
    }

    private void inorder(BTreeNode node) {
        if (node == null) return;
        for (int i = 0; i < node.n; i++) {
            if (!node.leaf) {
                inorder(node.child[i]);
            }
            System.out.print(" " + node.key[i]);
        }
        if (!node.leaf) {
            inorder(node.child[node.n]);
        }
    }

    public static void main(String[] args) {
        IntegerBTree btree = new IntegerBTree(3);
        btree.buildConfig();

        System.out.println("Inorder traversal:");
        btree.inorder();
        System.out.println();

        System.out.println("Contains 12: " + btree.contains(12));
        System.out.println("Contains 40: " + btree.contains(40));

        System.out.println("Height: " + btree.height());

        System.out.println("Level of 1: " + btree.level(1));
        System.out.println("Level of 4: " + btree.level(4));
        System.out.println("Level of 23: " + btree.level(23));

        System.out.println("Minimum: " + btree.min());
        System.out.println("Maximum: " + btree.max());

        System.out.println("Successor of 36: " + btree.successor(36));
        System.out.println("Predecessor of 1: " + btree.predecessor(1));
    }
}