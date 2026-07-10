
import java.util.Scanner;

class LinearSearch {

    public static int searchElement(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int[] arr = {5, 8, 2, 9, 4, 1, 7};

            System.out.println("Enter the target element: ");
            int target = sc.nextInt();

            int result = searchElement(arr, target);

            if (result == -1) {
                System.out.println("Element " + target + " not found");
            } else {
                System.out.println("Element " + target + " found at index: " + result);
            }
        }
    }
}
