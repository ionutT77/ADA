import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class MyAVLMapClient {

    private static String getRandomAlphaString(int n) {
        String Alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            // generate a random index in Alphabet
            int index = (int) (Alphabet.length() * Math.random());
            // add corresponding character to string
            sb.append(Alphabet.charAt(index));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        // two sorted maps implemented by MyAVLMap
        MySortedMap<Integer, Integer> map1 = new MyAVLMap<>();
        MySortedMap<Integer, String> map2 = new MyAVLMap<>();

        // two sorted maps implemented by java.util.TreeMap
        SortedMap<Integer, Integer> map1j = new TreeMap<>();
        SortedMap<Integer, String> map2j = new TreeMap<>();

        Integer intVals[] = {23, 1, 5, 6, 10, 41, 34, 99};

        // populate all maps with keys from intVals
        // put the same values in MyAVLMap and in java.util.TreeMap
        for (int i = 0; i < intVals.length; i++) {
            int rand_int1 = intVals[i];
            map1.put(rand_int1, 2 * rand_int1); // put in MySortedMap
            map1j.put(rand_int1, 2 * rand_int1); // put in Java's TreeMap
            String aValue = getRandomAlphaString(4);
            map2.put(rand_int1, aValue); // put in MySortedMap
            map2j.put(rand_int1, aValue); // put in Java's TreeMap
        }

        System.out.println("Keys of map1j Java's TreeMap in ascending order: ");
        for (Integer i : map1j.keySet()) {
            System.out.print(" " + i);
        }
        System.out.println("\n");

        System.out.println("Keys of map1 MySortedMap in ascending order: ");
        for (Integer i : map1.getKeys()) {
            System.out.print(" " + i);
        }
        System.out.println("\n");

        System.out.println("Entries of map2j Java's TreeMap in ascending order: ");
        for (Map.Entry<Integer, String> s : map2j.entrySet()) {
            System.out.print(" " + s);
        }
        System.out.println("\n");

        System.out.println("Entries of map2 MySortedMap in ascending order: ");
        for (Map.Entry<Integer, String> s : map2.getEntries()) {
            System.out.print(" " + s);
        }
        System.out.println("\n");

        int toDelete = intVals[2]; // one of the keys that was inserted will be deleted

        System.out.println("Delete key " + toDelete + " from map2j Java's TreeMap ");
        map2j.remove(toDelete);
        System.out.println("Entries of map2j after deleting: ");
        for (Map.Entry<Integer, String> s : map2j.entrySet()) {
            System.out.print(" " + s);
        }
        System.out.println("\n");

        System.out.println("Delete key " + toDelete + " from map2 MyTreeMap ");
        map2.remove(toDelete);
        System.out.println("Entries of map2 after deleting: ");
        for (Map.Entry<Integer, String> s : map2.getEntries()) {
            System.out.print(" " + s);
        }
        System.out.println("\n");

        // Test containsKey
        int testKey = intVals[0];
        System.out.println("map1 contains key " + testKey + ": " + map1.containsKey(testKey));
        System.out.println("map1j contains key " + testKey + ": " + map1j.containsKey(testKey));
        System.out.println("\n");

        // Test firstKey and lastKey
        System.out.println("First key in map1: " + map1.firstKey());
        System.out.println("First key in map1j: " + map1j.firstKey());
        System.out.println("Last key in map1: " + map1.lastKey());
        System.out.println("Last key in map1j: " + map1j.lastKey());
    }
}