
import java.util.Arrays;

public class ReverseArray {

    static class Solution {

        public void reverseArray(int arr[]) {
            if (arr == null || arr.length <= 1) {
                return;
            }

            int start = 0;
            int end = arr.length - 1;

            while (start < end) {
                int temp = arr[start];
                arr[start] = arr[end];
                arr[end] = temp;

                start++;
                end--;
            }
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        int[] arr1 = {1, 2, 3, 4};
        System.out.println("Test 1 Original: " + Arrays.toString(arr1));
        sol.reverseArray(arr1);
        System.out.println("Test 1 Reversed: " + Arrays.toString(arr1));

        System.out.println();

        int[] arr2 = {10, 20, 30, 40, 50};
        System.out.println("Test 2 Original: " + Arrays.toString(arr2));
        sol.reverseArray(arr2);
        System.out.println("Test 2 Reversed: " + Arrays.toString(arr2));
    }
}
