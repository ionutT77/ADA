import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleTrieTree {
    private final int R = 256; // R - size of alphabet.
    private TrieNode root;      // root of trie tree

    private class TrieNode {
        private Integer val;  // if current node contains end of a key(end of a word)
        private final TrieNode[] next = new TrieNode[R]; // may have R children
    }

    public SimpleTrieTree() {
        root = null;
    }

    public void put(String key, Integer val) {
        if ((key == null) || (val == null)) throw new IllegalArgumentException("key or val argument is null");
        else root = put(root, key, val);
    }

    private TrieNode put(TrieNode x, String key, Integer val) {
        if (x == null) x = new TrieNode();
        if (key.equals("")) {
            x.val = val;
            return x;
        }
        char c = key.charAt(0);
        String restkey = "";
        if (key.length() > 1) restkey = key.substring(1);
        x.next[c] = put(x.next[c], restkey, val);
        return x;
    }

    public void put_v2(String key, Integer val) {
        if (root == null) {
            root = new TrieNode();
        }
        TrieNode node = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (node.next[c] == null) {
                node.next[c] = new TrieNode();
            }
            node = node.next[c];
        }
        node.val = val;
    }

    public boolean contains(String key) {
        TrieNode node = getNode(root, key);
        return node != null && node.val != null;
    }

    private TrieNode getNode(TrieNode x, String key) {
        if (x == null) return null;
        if (key.equals("")) return x;
        char c = key.charAt(0);
        return getNode(x.next[c], key.substring(1));
    }

    public void printAllKeys() {
        for (String key : getAllKeys()) {
            System.out.println(key);
        }
    }

    public void printAllWithPrefix(String prefix) {
        for (String key : getAllKeysWithPrefix(prefix)) {
            System.out.println(key);
        }
    }

    public String findFirstWord() {
        return getAllKeys().iterator().next();
    }

    public String findLastWord() {
        List<String> keys = new ArrayList<>();
        getAllKeys().forEach(keys::add);
        return keys.get(keys.size() - 1);
    }

    public String findLongestWord() {
        String longest = "";
        for (String key : getAllKeys()) {
            if (key.length() > longest.length()) {
                longest = key;
            }
        }
        return longest;
    }

    public String findShortestWord() {
        String shortest = null;
        for (String key : getAllKeys()) {
            if (shortest == null || key.length() < shortest.length()) {
                shortest = key;
            }
        }
        return shortest;
    }

    public Iterable<String> getAllKeys() {
        List<String> keys = new ArrayList<>();
        collect(root, "", keys);
        return keys;
    }

    private void collect(TrieNode x, String prefix, List<String> keys) {
        if (x == null) return;
        if (x.val != null) keys.add(prefix);
        for (char c = 0; c < R; c++) {
            collect(x.next[c], prefix + c, keys);
        }
    }

    public Iterable<String> getAllKeysWithPrefix(String prefix) {
        List<String> keys = new ArrayList<>();
        TrieNode node = getNode(root, prefix);
        collect(node, prefix, keys);
        return keys;
    }

    public int countAllKeysWithPrefix(String prefix) {
        int count = 0;
        for (String key : getAllKeysWithPrefix(prefix)) {
            count++;
        }
        return count;
    }

    public void removeWord(String key) {
        root = remove(root, key);
    }

    private TrieNode remove(TrieNode x, String key) {
        if (x == null) return null;
        if (key.equals("")) {
            x.val = null;
        } else {
            char c = key.charAt(0);
            x.next[c] = remove(x.next[c], key.substring(1));
        }
        if (x.val != null) return x;
        for (char c = 0; c < R; c++) {
            if (x.next[c] != null) return x;
        }
        return null;
    }

    public void initFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int index = 0;
            while ((line = br.readLine()) != null) {
                put(line, index++);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SimpleTrieTree st = new SimpleTrieTree();
        st.initFromFile("src/random_numbers.txt");

        System.out.println("First word: " + st.findFirstWord());
        System.out.println("Last word: " + st.findLastWord());
        System.out.println("Longest word: " + st.findLongestWord());
        System.out.println("Number of words starting with 'ban': " + st.countAllKeysWithPrefix("ban"));
    }
}