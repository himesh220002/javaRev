
class BinaryString {

    public static boolean isBinary(String s) {
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch != '0' && ch != '1') {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("101 is binary: " + isBinary("101"));   // true
        System.out.println("75 is binary: " + isBinary("75"));    // false
        System.out.println("00011 is binary: " + isBinary("00011")); // true
    }
}
