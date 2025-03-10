import java.util.Arrays;
import java.io.IOException;

public class SortComparison {
    public static void main(String[] args) throws IOException {
        Integer[] integers = DataReader.readIntegers("C:\\Users\\Ionut\\Desktop\\ADA\\lab2_updated_ada\\src\\RandomIntegers_1M.txt");
        Person[] persons = DataReader.readPersons("C:\\Users\\Ionut\\Desktop\\ADA\\lab2_updated_ada\\src\\people-1M.txt");

        // Sort integers
        measureSortTime(integers, "Arrays.sort", Arrays::sort);
        measureSortTime(integers, "MergeSorter", new MergeSorter<>()::sort);

        // Sort persons by last name
        measureSortTime(persons, "Arrays.sort by last name", arr -> Arrays.sort(arr, PersonComparators.byLastName()));
        measureSortTime(persons, "MergeSorter by last name", arr -> new MergeSorter<Person>().sort(arr, PersonComparators.byLastName()));

        // Sort persons by first name
        measureSortTime(persons, "Arrays.sort by first name", arr -> Arrays.sort(arr, PersonComparators.byFirstName()));
        measureSortTime(persons, "MergeSorter by first name", arr -> new MergeSorter<Person>().sort(arr, PersonComparators.byFirstName()));

        // Sort persons by age
        measureSortTime(persons, "Arrays.sort by age", arr -> Arrays.sort(arr, PersonComparators.byAge()));
        measureSortTime(persons, "MergeSorter by age", arr -> new MergeSorter<Person>().sort(arr, PersonComparators.byAge()));
    }

    private static <T> void measureSortTime(T[] array, String sortName, java.util.function.Consumer<T[]> sorter) {
        T[] copy = Arrays.copyOf(array, array.length);
        long startTime = System.currentTimeMillis();
        sorter.accept(copy);
        long endTime = System.currentTimeMillis();
        System.out.println(sortName + " took " + (endTime - startTime) + " ms");
    }
}