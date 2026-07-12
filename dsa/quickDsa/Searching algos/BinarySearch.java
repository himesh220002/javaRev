
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

//recursive implementation
// class BinarySearch {
//     int binarySearch(int arr[], int l, int r, int x)
//     {
//         if (r >= l) {
//             int mid = l + (r - l) / 2;
//             if (arr[mid] == x)
//                 return mid;
//             if (arr[mid] > x)
//                 return binarySearch(arr, l, mid - 1, x);
//             return binarySearch(arr, mid + 1, r, x);
//         }
//         return -1;
//     }
//     public static void main(String args[])
//     {
//         BinarySearch ob = new BinarySearch();
//         int arr[] = { 2, 3, 4, 10, 40 };
//         int n = arr.length;
//         int x = 10;
//         int result = ob.binarySearch(arr, 0,
//                                      n - 1, x);
//         if (result == -1)
//             System.out.println("Element "+ "not present");
//         else
//             System.out.println("Element found"+ " at index "+ result);
//     }
// }
