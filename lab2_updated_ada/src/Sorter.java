interface Sorter<T extends Comparable<T>> {
    String getName();

    void sort(T[] a);
}