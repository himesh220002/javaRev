
import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {

            System.out.println("--- Simple Java Calculator ---");
            System.out.print("Enter first number: ");
            double num1 = sc.nextDouble();

            System.out.print("Enter an operator (+, -, *, /): ");
            char operator = sc.next().charAt(0);

            System.out.print("Enter second number: ");
            double num2 = sc.nextDouble();

            double result;
            switch (operator) {
                case '+' ->
                    result = num1 + num2;
                case '-' ->
                    result = num1 - num2;
                case '*' ->
                    result = num1 * num2;
                case '/' ->
                    result = num1 / num2;
                default -> {
                    System.out.println("Invalid operator");
                    return;
                }
            }

            System.out.println("Result: " + num1 + " " + operator + " " + num2 + " = " + result);
            sc.close();
        }
    }
}
