
import java.util.Scanner;

class BinarySearch {

    public static int searchItem(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {

            int mid = left + (right - left) / 2;
            if (arr[mid] < target) {
                left = mid + 1;  // search right half
            } else {
                right = mid - 1; // search left half
            }
            if (arr[mid] == target) {
                return mid;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int[] arr = {2, 3, 4, 5, 7, 8, 9};
            System.out.println("Enter the target element : ");
            int target = sc.nextInt();

            int result = searchItem(arr, target);

            if (result == -1) {
                System.out.println("Element " + target + " not found");
            } else {
                System.out.println("Element " + target + " found at index " + result);
            }

            sc.close();
        }
    }

}
