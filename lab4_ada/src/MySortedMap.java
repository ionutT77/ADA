import java.util.Map;

public interface MySortedMap<K, V> {
    void put(K key, V value);
    V get(K key);
    void remove(K key);
    Iterable<K> getKeys();
    Iterable<Map.Entry<K, V>> getEntries();
    boolean containsKey(K key);
    K firstKey();
    K lastKey();
}