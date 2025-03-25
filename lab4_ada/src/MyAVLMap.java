import java.util.*;

public class MyAVLMap<K extends Comparable<K>, V> implements MySortedMap<K, V> {
    private TreeMap<K, V> treeMap;

    public MyAVLMap() {
        this.treeMap = new TreeMap<>();
    }

    @Override
    public void put(K key, V value) {
        treeMap.put(key, value);
    }

    @Override
    public V get(K key) {
        return treeMap.get(key);
    }

    @Override
    public void remove(K key) {
        treeMap.remove(key);
    }

    @Override
    public Iterable<K> getKeys() {
        return treeMap.keySet();
    }

    @Override
    public Iterable<Map.Entry<K, V>> getEntries() {
        return treeMap.entrySet();
    }

    @Override
    public boolean containsKey(K key) {
        return treeMap.containsKey(key);
    }

    @Override
    public K firstKey() {
        if (treeMap.isEmpty()) {
            return null;
        } else {
            return treeMap.firstKey();
        }
    }

    @Override
    public K lastKey() {
        if (treeMap.isEmpty()) {
            return null;
        } else {
            return treeMap.lastKey();
        }
    }

}