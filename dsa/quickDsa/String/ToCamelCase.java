
class ToCamelCase {

    // Function to convert the given string to Camel Case
    public String convertToCamelCase(String s) {
        // trim leading/trailing spaces, split by one or more spaces
        String[] words = s.trim().split("\\s+");

        StringBuilder sb = new StringBuilder();

        // first word stays original case
        sb.append(words[0]);

        // process remaining words
        for (int i = 1; i < words.length; i++) {
            String word = words[i];
            sb.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        ToCamelCase sol = new ToCamelCase();
        System.out.println(sol.convertToCamelCase("i'm learning dsa from geeksforgeeks"));
        // iGotInternAtGeeksforgeeks
    }
}
