
import java.util.*;

class AlternetArray {

    public ArrayList<Integer> getAlternates(int arr[]) {
        // Code Here

        ArrayList<Integer> alt = new ArrayList<>();

        for (int i = 0; i < arr.length; i += 2) {
            alt.add(arr[i]);
        }

        return alt;
    }

    public static void main(String[] args) {
        AlternetArray sol = new AlternetArray();
        int arr[] = {1, 2, 3, 4, 5};
        System.out.println(sol.getAlternates(arr));
    }
}
