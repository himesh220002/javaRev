
import java.util.Arrays;

class HeapSort {

    public void sort(int[] arr) {
        int n = arr.length;

        //BUild max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        //extract element one by one
        for (int i = n - 1; i > 0; i--) {
            //move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            //heapify reduce heap
            heapify(arr, i, 0);

        }

    }

    public void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;

            // Recursively heapify the affected subtree
            heapify(arr, n, largest);
        }

    }

    public static void main(String[] args) {
        int[] arr = {12, 11, 6, 8, 10, 9, 15, 3};

        HeapSort hs = new HeapSort();
        hs.sort(arr);
        System.out.println("Sorted array; " + Arrays.toString(arr));
    }
}
