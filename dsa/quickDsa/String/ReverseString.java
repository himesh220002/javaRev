
class ReverseString {

    public static void main(String[] args) {
        String s = "Hello Geeks";
        char[] arr = s.toCharArray();
        int i = 0, j = arr.length - 1;

        while (i < j) {
            char temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            i++;
            j--;
        }

        System.out.println(new String(arr));
    }
}
// time = O(n)
// space = O(1)

// ____________________________________________________
// Using StringBuilder
// class ReverseString {
//     public static void main(String[] args) {
//         String s = "Hello Geeks";
//         StringBuilder sb = new StringBuilder(s);
//         System.out.println(sb.reverse().toString());
//     }
// }
// time = O(n)
// space = O(n)
