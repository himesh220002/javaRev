
public class LargestNumber {

    static class Solution {

        public static int largest(int[] arr) {
            // 1. Guard clause: Handle empty or null arrays instantly to avoid crashes
            if (arr == null || arr.length == 0) {
                return -1;
            }

            // 2. Assume the first element is the champion
            int largestElement = arr[0];

            // 3. Scan the array to find any element that can beat the current champion
            for (int i = 1; i < arr.length; i++) {
                if (largestElement < arr[i]) {
                    largestElement = arr[i];
                }
            }
            return largestElement;
        }
    }

    public static void main(String[] args) {
        // Test Case 1: Standard Mixed/Positive Numbers
        int[] arr1 = {4, 2, 3, 9, 4, 7, 6, 8};
        System.out.println("Test 1 (Mixed): Expected 9, Got -> " + Solution.largest(arr1));

        // Test Case 2: Pure Negative Numbers (Your custom trace case!)
        int[] arr2 = {-12, -1, -99, -8, -7};
        System.out.println("Test 2 (All Negative): Expected -1, Got -> " + Solution.largest(arr2));

        // Test Case 3: Array with duplicates of the largest number
        int[] arr3 = {5, 5, 2, 1, 5};
        System.out.println("Test 3 (Duplicates): Expected 5, Got -> " + Solution.largest(arr3));

        // Test Case 4: Single element array boundary check
        int[] arr4 = {42};
        System.out.println("Test 4 (Single Element): Expected 42, Got -> " + Solution.largest(arr4));
    }
}
