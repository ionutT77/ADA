import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleTrieTree {
    private final int R = 256;
    private TrieNode root;

    private class TrieNode {
        private Integer val;
        private final TrieNode[] next = new TrieNode[R];
        private boolean isEndOfWord;
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
            x.isEndOfWord = true;
            return x;
        }
        char c = key.charAt(0);
        String restkey = key.length() > 1 ? key.substring(1) : "";
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
        node.isEndOfWord = true;
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
        List<String> keys = new ArrayList<>();
        collect(root, "", keys);
        for (String key : keys) {
            System.out.println(key);
        }
    }

    private void collect(TrieNode x, String prefix, List<String> keys) {
        if (x == null) return;
        if (x.val != null) keys.add(prefix);
        for (char c = 0; c < R; c++) {
            collect(x.next[c], prefix + c, keys);
        }
    }

    public void printAllWithPrefix(String prefix) {
        List<String> keys = new ArrayList<>();
        TrieNode node = getNode(root, prefix);
        collect(node, prefix, keys);
        for (String key : keys) {
            System.out.println(key);
        }
    }

    public String findFirstWord() {
        List<String> keys = new ArrayList<>();
        collect(root, "", keys);
        if (keys.isEmpty()) return null;
        Collections.sort(keys);
        return keys.get(0);
    }

    public String findLastWord() {
        List<String> keys = new ArrayList<>();
        collect(root, "", keys);
        if (keys.isEmpty()) return null;
        Collections.sort(keys);
        return keys.get(keys.size() - 1);
    }

    public String findLongestWord() {
        List<String> keys = new ArrayList<>();
        collect(root, "", keys);
        String longestWord = "";
        for (String key : keys) {
            if (key.length() > longestWord.length()) {
                longestWord = key;
            }
        }
        return longestWord;
    }

    public String findShortestWord() {
        List<String> keys = new ArrayList<>();
        collect(root, "", keys);
        if (keys.isEmpty()) return null;
        String shortestWord = keys.get(0);
        for (String key : keys) {
            if (key.length() < shortestWord.length()) {
                shortestWord = key;
            }
        }
        return shortestWord;
    }

    public Iterable<String> getAllKeys() {
        List<String> keys = new ArrayList<>();
        collect(root, "", keys);
        return keys;
    }

    public Iterable<String> getAllKeysWithPrefix(String prefix) {
        List<String> keys = new ArrayList<>();
        TrieNode node = getNode(root, prefix);
        collect(node, prefix, keys);
        return keys;
    }

    public int countAllKeysWithPrefix(String prefix) {
        List<String> keys = new ArrayList<>();
        TrieNode node = getNode(root, prefix);
        collect(node, prefix, keys);
        return keys.size();
    }

    public void removeWord(String key) {
        root = remove(root, key, 0);
    }

    private TrieNode remove(TrieNode x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) {
            x.val = null;
            x.isEndOfWord = false;
        } else {
            char c = key.charAt(d);
            x.next[c] = remove(x.next[c], key, d + 1);
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
        st.initFromFile("C:\\Users\\Ionut\\Desktop\\ADA\\lab6_ada_ex1\\src\\random_words.txt");

        System.out.println("All keys with prefix 'ban':");
        st.printAllWithPrefix("ban");

        System.out.println("\nContains 'banana': " + st.contains("banana"));
        System.out.println("Contains 'apple': " + st.contains("apple"));

        System.out.println("\nFirst word: " + st.findFirstWord());
        System.out.println("Last word: " + st.findLastWord());
        System.out.println("Longest word: " + st.findLongestWord());
        System.out.println("Shortest word: " + st.findShortestWord());

        System.out.println("\nNumber of words with prefix 'ban': " + st.countAllKeysWithPrefix("ban"));

        st.removeWord("banana");
        // System.out.println("\nAfter removing 'banana':");
        // st.printAllKeys();
    }
}