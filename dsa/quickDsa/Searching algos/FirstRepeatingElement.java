
import java.util.*;

class FirstRepeatingElement {

    public static int firstRepeated(int[] arr) {
        // Handle edge cases
        if (arr == null || arr.length < 2) {
            return -1;
        }

        HashSet<Integer> seen = new HashSet<>();
        int minIndex = -1;

        // Traverse from right to left so we capture the first repeated index
        for (int i = arr.length - 1; i >= 0; i--) {
            if (seen.contains(arr[i])) {
                minIndex = i + 1; // +1 for 1-based index
            } else {
                seen.add(arr[i]);
            }
        }

        return minIndex;
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {

            System.out.print("Enter size of array: ");
            int n = sc.nextInt();
            int[] arr = new int[n];

            System.out.println("Enter " + n + " elements:");
            for (int i = 0; i < n; i++) {
                arr[i] = sc.nextInt();
            }

            int result = firstRepeated(arr);

            if (result == -1) {
                System.out.println("No repeated element found.");
            } else {
                System.out.println("First repeated element occurs at position: " + result);
            }
        }
    }
}
