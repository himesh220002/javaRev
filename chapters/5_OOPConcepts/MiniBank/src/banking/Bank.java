package banking;

// import java.util.Scanner;
public class Bank {

    public static void main(String[] args) {
        System.out.println("Starting MiniBank Web Application...");
        try {
            Class.forName("banking.BankManagement");
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load database connection");
        }
        BankServer.start();
    }
}
