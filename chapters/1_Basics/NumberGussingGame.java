
import java.util.Scanner;

// The program generates a random number between a predefined range (e.g., 1 to 100).
// The user has limited attempts (K tries) to guess the number.
// At each guess, the program provides a hint: If the guessed number is higher, it tells the user to guess lower. If the guessed number is lower, it tells the user to guess higher.
// If the user guesses correctly, they win.
// If all attempts are exhausted, the game reveals the correct number.
public class NumberGussingGame {

    public static void main(String[] args) {

        System.out.println("welcome to the Number Guessing Game\n between 1 and 100 ");
        try (Scanner sc = new Scanner(System.in)) {
            int SystemSelectedNumber = (int) (Math.random() * 100 + 1);
            // System.out.println(SystemSelectedNumber);

            int attempts = 5;

            while (attempts > 0) {
                System.out.print("You have " + attempts + " attempts left.\n Guess the number: ");
                int typedNumber = sc.nextInt();

                if (typedNumber < 1 || typedNumber > 100) {
                    System.out.println("Invalid number! Please enter between 1 and 100.");
                    // ⚠️ Do NOT decrement attempts here
                    continue;
                }

                if (typedNumber == SystemSelectedNumber) {
                    System.out.println("You Win!");
                    sc.close();
                    return;
                } else if (typedNumber > SystemSelectedNumber) {
                    System.out.println("You guessed higher");
                } else {
                    System.out.println("You guessed lower");
                }
                attempts--;
            }
            System.out.println("The correct number was: " + SystemSelectedNumber);
        }
        // System.out.println(SystemSelectedNumber);
    }
}
