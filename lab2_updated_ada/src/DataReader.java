import java.io.*;
import java.util.*;

public class DataReader {
    public static Integer[] readIntegers(String fileName) throws IOException {
        List<Integer> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(Integer.parseInt(line));
            }
        }
        return list.toArray(new Integer[0]);
    }

    public static Person[] readPersons(String fileName) throws IOException {
        List<Person> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 4) {
                    String firstName = parts[1].trim();
                    String lastName = parts[2].trim();
                    // Assuming age is calculated from the date of birth
                    int age = calculateAge(parts[3].trim());
                    list.add(new Person(firstName, lastName, age));
                } else {
                    System.err.println("Invalid line: " + line);
                }
            }
        }
        return list.toArray(new Person[0]);
    }

    private static int calculateAge(String dob) {
        // Implement age calculation from date of birth (dob)
        // For simplicity, let's assume the dob format is dd/MM/yyyy
        String[] dateParts = dob.split("/");
        int birthYear = Integer.parseInt(dateParts[2]);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return currentYear - birthYear;
    }
}