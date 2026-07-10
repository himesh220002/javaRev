
import java.util.Arrays;

public class IsSorted {

    static class Solution {

        public boolean isSorted(int[] arr) {
            // Loop through up to the second-to-last item to prevent out-of-bounds
            for (int i = 0; i < arr.length - 1; i++) {
                // If any element is strictly greater than the next, it is unsorted
                if (arr[i] > arr[i + 1]) {
                    return false; // Drops out immediately!
                }
            }
            // If the loop finishes cleanly without hitting a violation, it is sorted
            return true;
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // Test Case 1: Early Violation (Stops immediately at index 0)
        int[] arr1 = {99, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println("Array: " + Arrays.toString(arr1));
        System.out.println("Is Sorted? : " + sol.isSorted(arr1)); // Expected: false

        Arrays.sort(arr1);   //  built-in sorting program 

        System.out.println(); // Spacing

        // Test Case 2: Perfectly Sorted Sequence
        int[] arr2 = {5, 12, 22, 22, 35, 60};
        System.out.println("Array: " + Arrays.toString(arr2));
        System.out.println("Is Sorted? : " + sol.isSorted(arr2)); // Expected: true
    }
}
