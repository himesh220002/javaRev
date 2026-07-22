
class Panagram {

    public static boolean isPangram(String s) {
        // convert to lowercase
        s = s.toLowerCase();

        // boolean array for 26 letters
        boolean[] seen = new boolean[26];  // default its [false,false,.....,false]

        // mark letters present
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch >= 'a' && ch <= 'z') {
                seen[ch - 'a'] = true;
            }
        }

        // check if all letters are seen
        for (boolean b : seen) {
            if (!b) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPangram("The quick brown fox jumps over the lazy dog")); // true
        System.out.println(isPangram("hi geeksforgeeks")); // false
    }
}
