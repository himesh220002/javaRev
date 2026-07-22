
class Palindrome {

    boolean isPalindrome(String s) {
        int l = 0;
        int r = s.length() - 1;

        while (l < r) {
            char lChar = s.charAt(l);
            char rChar = s.charAt(r);

            if (lChar != rChar) {
                return false;
            }
            l++;
            r--;
        }

        return true;
    }

    public static void main(String[] args) {
        Palindrome sol = new Palindrome();
        System.out.println(sol.isPalindrome("madam"));   // true
        System.out.println(sol.isPalindrome("racecar")); // true
        System.out.println(sol.isPalindrome("hello"));   // false
    }
}
