
import java.util.*;

class Bitonic {

    public int findMaximum(int[] arr) {
        int left = 0;
        int right = arr.length - 1;

        // Binary search for peak element in a bitonic array
        while (left < right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] > arr[mid + 1]) {
                // mid could be the maximum, so move right pointer
                right = mid;
            } else {
                // maximum lies to the right
                left = mid + 1;
            }
        }

        // When left == right, they point to the maximum element
        return arr[left];
    }

    public static void main(String[] args) {
        Bitonic sol = new Bitonic();
        try (Scanner sc = new Scanner(System.in)) {

            System.out.print("Enter size of array: ");
            int n = sc.nextInt();
            int[] arr = new int[n];

            System.out.println("Enter " + n + " elements (bitonic array):");
            for (int i = 0; i < n; i++) {
                arr[i] = sc.nextInt();
            }

            int result = sol.findMaximum(arr);

            System.out.println("Maximum element in array: " + result);
        }
    }
}
