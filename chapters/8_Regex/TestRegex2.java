import java.util.regex.*;
public class TestRegex2 {
    public static void main(String[] args){
        String text = "Java is fun. java is powerful. Java is easy.";
        Pattern p = Pattern.compile("[a-zA-Z]+ava"); 
        Matcher m = p.matcher(text);

        System.out.println("--- Using find() ---");
        while(m.find()){
            System.out.println("Found: " + m.group() + 
                               " at index " + m.start() + 
                               " to " + m.end());
        }

        System.out.println("\n--- Using replaceAll() ---");
        String newText = m.replaceAll("Python");
        System.out.println("Replaced: " + newText);
    }
}
