
import java.util.*;

class CountZeroes {

    /**
     * sorted array arr[] and an integer x , find the index (0-based) of the
     * largest element in arr[] that is less than or equal to x , This element
     * is called the floor of x. If such an element does not exist, return -1.
     */
    int countZeroes(int[] arr) {
        int size = arr.length;
        int left = 0;
        int right = size - 1;
        int firstZeroIndex = -1;

        // Binary search for the first occurrence of 0
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == 0) {
                firstZeroIndex = mid;
                right = mid - 1; // keep searching left side for earlier 0
            } else {
                left = mid + 1; // move right if mid is 1
            }
        }

        if (firstZeroIndex == -1) {
            return 0; // no zero found
        }
        return size - firstZeroIndex; // count of zeroes
    }

    public static void main(String[] args) {
        CountZeroes sol = new CountZeroes();
        try (Scanner sc = new Scanner(System.in)) {

            System.out.print("Enter size of array: ");
            int n = sc.nextInt();
            int[] arr = new int[n];

            System.out.println("Enter " + n + " elements (sorted binary array of 1s followed by 0s):");
            for (int i = 0; i < n; i++) {
                arr[i] = sc.nextInt();
            }

            int result = sol.countZeroes(arr);

            System.out.println("Number of zeroes in array: " + result);

        }
    }
}
