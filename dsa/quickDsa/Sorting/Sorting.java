
import java.util.*;

class Sorting {

    public static void main(String[] args) {

        // Array example
        int[] nums = {5, 3, 8, 1};
        Arrays.sort(nums);
        System.out.println("Sorted array: " + Arrays.toString(nums));

        // List example
        List<Integer> list = new ArrayList<>(Arrays.asList(5, 3, 8, 1));
        Collections.sort(list);
        System.out.println("Sorted list: " + list);
    }
}
