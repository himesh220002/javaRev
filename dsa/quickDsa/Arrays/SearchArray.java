
public class SearchArray {

    // Nested Solution class containing your DSA logic
    static class Solution {

        public int search(int arr[], int x) {
            // 1. Traverse through the array index by index
            for (int i = 0; i < arr.length; i++) {
                // 2. If the item matches, stop the loop and return the index location
                if (arr[i] == x) {
                    return i;
                }
            }
            // 3. If we loop through the entire array and find nothing, return -1
            return -1;
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        int[] arr1 = {10, 20, 30, 40, 50};
        int target1 = 30;
        int result1 = sol.search(arr1, target1);
        System.out.println("Searching for " + target1 + ": Found at index position " + result1);

        int target2 = 99;
        int result2 = sol.search(arr1, target2);
        System.out.println("Searching for " + target2 + ": Result is " + result2);
    }
}
