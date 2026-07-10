
import java.util.Arrays;

class RawMergeSort {

    public static void merge(int[] arr, int left, int mid, int right) { // Merge two halves
        int sizeLeft = mid - left + 1;
        int sizeRight = right - mid;

        int[] L = new int[sizeLeft];
        int[] R = new int[sizeRight];

        // Copy data
        for (int i = 0; i < sizeLeft; i++) {
            L[i] = arr[left + i];
        }
        for (int j = 0; j < sizeRight; j++) {
            R[j] = arr[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;

        // Merge sorted temp arrays back into arr[l..r]
        while (i < sizeLeft && j < sizeRight) {
            if (L[i] < R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
        // copy remaining elements of L[] if right exhausted 
        while (i < sizeLeft) {
            arr[k] = L[i];
            i++;
            k++;
        }
        // copy remaining elements of R[] if left exhausted
        while (j < sizeRight) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    public static int[] mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            mergeSort(arr, left, mid);

            mergeSort(arr, mid + 1, right);

            // Key call
            System.out.println("merge(arr," + left + "," + mid + "," + right + ")");
            merge(arr, left, mid, right);
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] arr = {4, 8, 2, 3, 5, 9, 1, 0};

        int[] sortedArr = RawMergeSort.mergeSort(arr, 0, arr.length - 1);
        // mergeSort(arr, 0, arr.length - 1);

        System.out.println("sortedArray using Arrays.toString() : " + Arrays.toString(sortedArr));

        System.out.print("Sorted array using loop: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }

    }

}
