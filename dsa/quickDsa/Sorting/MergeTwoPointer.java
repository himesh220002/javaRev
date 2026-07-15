
import java.util.Arrays;

class MergeTwoPointer {

    public static int[] mergeArrays(int[] a, int[] b) {
        int n = a.length, m = b.length;
        // create a new array to store the merged result
        int[] c = new int[n + m];
        int i = 0, j = 0, k = 0;

        while (i < n && j < m) {
            if (a[i] <= b[j]) {
                c[k] = a[i];
                i++;
            } else {
                c[k] = b[j];
                j++;
            }
            k++;
        }
        // copy remaining elements of a[]
        while (i < n) {
            c[k++] = a[i++];
        }
        // copy remaining elements of b[]
        while (j < m) {
            c[k++] = b[j++];
        }

        return c;
    }

    public static void main(String[] args) {
        int[] a = {1, 3, 5, 7};
        int[] b = {4, 8, 9};

        int[] merged = mergeArrays(a, b);

        System.out.println("Using Merge two pinter: " + Arrays.toString(merged));

    }
}
