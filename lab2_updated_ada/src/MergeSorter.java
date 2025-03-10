import java.util.Arrays;
import java.util.Comparator;

public class MergeSorter<T> {
    public void sort(T[] array) {
        if (array.length > 1) {
            T[] left = Arrays.copyOfRange(array, 0, array.length / 2);
            T[] right = Arrays.copyOfRange(array, array.length / 2, array.length);

            sort(left);
            sort(right);

            merge(array, left, right);
        }
    }

    public void sort(T[] array, Comparator<? super T> comparator) {
        if (array.length > 1) {
            T[] left = Arrays.copyOfRange(array, 0, array.length / 2);
            T[] right = Arrays.copyOfRange(array, array.length / 2, array.length);

            sort(left, comparator);
            sort(right, comparator);

            merge(array, left, right, comparator);
        }
    }

    private void merge(T[] result, T[] left, T[] right) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (((Comparable<T>) left[i]).compareTo(right[j]) <= 0) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }
        while (i < left.length) {
            result[k++] = left[i++];
        }
        while (j < right.length) {
            result[k++] = right[j++];
        }
    }

    private void merge(T[] result, T[] left, T[] right, Comparator<? super T> comparator) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (comparator.compare(left[i], right[j]) <= 0) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }
        while (i < left.length) {
            result[k++] = left[i++];
        }
        while (j < right.length) {
            result[k++] = right[j++];
        }
    }
}