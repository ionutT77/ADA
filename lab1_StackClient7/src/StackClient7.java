import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class StackClient7 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String filename = scanner.nextLine();
        try {

            if (isValidHTML(filename)) {
                System.out.println("The HTML file is well-formed.");
            } else {
                System.out.println("The HTML file is not well-formed.");
            }
        } catch (IOException e) {

            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    public static boolean isValidHTML(String filename) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(filename));

        Stack<String> stack = new Stack<>();
        String line;

        while ((line = reader.readLine()) != null) {
            int i = 0;

            while (i < line.length()) {

                if (line.charAt(i) == '<') {

                    int j = line.indexOf('>', i);
                    if (j == -1) {
                        reader.close();
                        return false;
                    }

                    String tag = line.substring(i + 1, j);

                    if (!tag.startsWith("/")) {
                        stack.push(tag);
                    } else {

                        if (stack.isEmpty() || !stack.pop().equals(tag.substring(1))) {
                            reader.close();
                            return false;
                        }
                    }

                    i = j;
                }

                i++;
            }
        }
        reader.close();
        return stack.isEmpty();
    }
}