import java.util.Scanner;
import java.util.Stack;

public class StackClient2~{
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Stack<Integer> stack1 = new Stack<>();
        Stack<String> stack2 = new Stack<>();
        System.out.println("Type no of lines: ");
        int n = in.nextInt();
        in.nextLine();

        for (int i = 0; i < n; i++) {
            String line = in.nextLine();
            String[] tokens = line.split(" ", 2);
            try {
                int integer = Integer.parseInt(tokens[0]);
                stack1.push(integer);
                stack2.push(tokens[1]);

            } catch (NumberFormatException e){
            System.out.println("INVALID INPUT");
            }
        }
        if(n==0 || stack1.isEmpty() || stack2.isEmpty()) {
            System.out.println("EMPTY");
            return;
        }
        while(!stack1.isEmpty()) {
        System.out.println(stack1.pop() + " ");
        }
        while(!stack2.isEmpty())
        {
            System.out.println(stack2.pop() + " ");
        }

    }

    }
