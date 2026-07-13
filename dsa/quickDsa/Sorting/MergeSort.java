
import java.util.Arrays;

class MergeSort {

    public int[] mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);

            merge(arr, left, mid, right);
        }
        return arr;
    }

    public void merge(int[] arr, int left, int mid, int right) {
        //sizes
        int leftSize = mid - left + 1;
        int rightSize = right - mid;

        //init empty arrays
        int[] leftArray = new int[leftSize];
        int[] rightArray = new int[rightSize];

        // copy data to temp arrays
        for (int i = 0; i < leftSize; ++i) {
            leftArray[i] = arr[left + i];
        }
        for (int j = 0; j < rightSize; ++j) {
            rightArray[j] = arr[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;

        //merge sorted temp arrays back into arr[l..r]
        while (i < leftSize && j < rightSize) {
            if (leftArray[i] < rightArray[j]) {
                arr[k] = leftArray[i];
                i++;
            } else {
                arr[k] = rightArray[j];
                j++;
            }
            k++;
        }

        //copy remaining elements
        while (i < leftSize) {
            arr[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < rightSize) {
            arr[k] = rightArray[j];
            j++;
            k++;
        }

    }

    public static void main(String[] args) {
        MergeSort ms = new MergeSort();

        int arr[] = {12, 11, 6, 8, 10, 9, 15, 3};
        int merged[] = ms.mergeSort(arr, 0, arr.length - 1);
        System.out.println("Using Merge Sort : " + Arrays.toString(merged));

    }
}
