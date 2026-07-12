
import java.util.Scanner;

class SecondLargest {

    public int getSecondLargest(int[] arr) {
        // Handle edge cases
        if (arr == null || arr.length < 2) {
            return -1;
        }

        int largest = Integer.MIN_VALUE;
        int secLargest = Integer.MIN_VALUE;

        for (int num : arr) {
            if (num > largest) {
                secLargest = largest;
                largest = num;
            } else if (num > secLargest && num != largest) {
                secLargest = num;
            }
        }

        return (secLargest == Integer.MIN_VALUE) ? -1 : secLargest;
    }

    public static void main(String[] args) {
        SecondLargest sol = new SecondLargest();
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter size of array: ");
            int n = sc.nextInt();
            int[] arr = new int[n];

            System.out.println("Enter " + n + " elements:");
            for (int i = 0; i < n; i++) {
                arr[i] = sc.nextInt();
            }

            int result = sol.getSecondLargest(arr);

            if (result == -1) {
                System.out.println("Second largest element not found.");
            } else {
                System.out.println("Second largest element is: " + result);
            }
        }
    }
}
