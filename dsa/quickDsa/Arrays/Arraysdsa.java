
import java.util.*;

class Arraysdsa {

    @SuppressWarnings("ManualArrayToCollectionCopy")
    public static void main(String[] args) {
        System.out.println("=== 1. INITIALIZATION ===");
        int[] arr = {10, 20, 30, 40, 50};  // Fixed size of 5

        List<Integer> list = new ArrayList<>();

        Collections.addAll(list, 30, 10, 50, 20, 40);  // Dynamic size

        System.out.println("Native Array: " + Arrays.toString(arr));
        System.out.println("Using List/Collection: " + list);

        //____________________________________________________________
        System.out.println("\n=== 2. UPDATE (Modify index 1 to 99) ===");

        arr[1] = 99; // Array: Uses direct square bracket syntax
        list.set(1, 99); // List: Uses .set() method

        System.out.println("Updated Native Array: " + Arrays.toString(arr));
        System.out.println("Updated List/Collection: " + list);

        //____________________________________________________________
        System.out.println("\n=== 3. SEARCH (Find the position of 50) ===");

        int arrayIndexPosition = -1;

        // Array: Requires a manual loop to look at each element
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 50) {
                arrayIndexPosition = i;
                break;
            }
        }

        int arrayListIndexPosition = list.indexOf(50);  // ArrayList: Single-line shortcut method

        System.out.println("Position of 50 in Array: " + arrayIndexPosition);
        System.out.println("Position of 50 in List: " + arrayListIndexPosition);

        //____________________________________________________________
        System.out.println("\n=== 4. SORT ===");

        Arrays.sort(arr);  // Array: Uses the Arrays utility helper class
        Collections.sort(list);  // ArrayList: Uses the Collections utility helper class

        System.out.println("Sorted Array: " + Arrays.toString(arr));
        System.out.println("Sorted List/Collection: " + list);

        //____________________________________________________________
        System.out.println("\n=== 5. ADD (Append 60 to the end) ===");
        // Array: (Cannot change size!) needs to build a new array entirely
        int newArray[] = new int[arr.length + 1];

        for (int i = 0; i < arr.length; i++) {   //manual array copy for arr into newArray 
            newArray[i] = arr[i];          // [null,null,null,null,null,null] -> [10, 20, 30, 40, 50, null]
        }

        newArray[newArray.length - 1] = 60;    // last index   -> [10, 20, 30, 40, 50, null] -> [10, 20, 30, 40, 50, 60]

        list.add(60); //  ArrayList: Just call .add() and it resizes itself automatically

        System.out.println("Appended Array: " + Arrays.toString(newArray) + " , New Array size: " + newArray.length);
        System.out.println("Appended List: " + list + " , New Array size: " + list.size());

        //____________________________________________________________
        System.out.println("\n=== 6. REMOVE (Delete item at index 2) ===");
        //Array must manually shift all remaining items left to fill the gap

        int[] shortArray = new int[newArray.length - 1];
        int targetIndex = 2;
        for (int i = 0, j = 0; i < newArray.length; i++) {
            if (i == targetIndex) {
                continue;
            }
            shortArray[j++] = newArray[i];
        }

        list.remove(2);

        System.out.println("Removed Array " + Arrays.toString(shortArray) + " , New Array size: " + shortArray.length);
        System.out.println("Removed List " + list + " , New Array size: " + list.size());

    }
}
