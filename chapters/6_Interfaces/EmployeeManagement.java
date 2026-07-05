
import java.util.*;

class EmployeeManagement {

// class eemployee
    static class employee {

        String name;
        long code;
        String designation;
        int exp;
        int age;

    }

    static int num;
    static final int MAX = 20;

    static Scanner sc = new Scanner(System.in);

    static employee emp[] = new employee[MAX];

    static boolean isCodeExists(long code, int currentIndex) {
        for (int j = 0; j < currentIndex; j++) {
            if (emp[j] != null && emp[j].code == code) {
                return true;
            }
        }
        return false;
    }

    static void build() {
        System.out.println("Build the table");
        System.out.println("Max number of etries can be- " + MAX);
        System.out.print("Enter the number of entries required: ");

        num = sc.nextInt();
        if (num > MAX) {
            System.out.println("Maximum number of entries are " + MAX);
            num = 20;
        }

        for (int i = 0; i < num; i++) {
            emp[i] = new employee();
            System.out.print("Enter Name: ");
            emp[i].name = sc.next();

            System.out.print("Enter Emopoyee ID: ");
            long inputCode = sc.nextLong();
            while (isCodeExists(inputCode, i)) {
                System.out.println("Employee ID already exists! Please enter a unique ID.");
                System.out.print("Enter Emopoyee ID: ");
                inputCode = sc.nextLong();
            }
            emp[i].code = inputCode;

            System.out.print("Enter Designation: ");
            emp[i].designation = sc.next();

            System.out.print("Enter Experience: ");
            emp[i].exp = sc.nextInt();

            System.out.print("Enter Age: ");
            emp[i].age = sc.nextInt();

            if (i <= num - 1) {
                System.out.println("Record Inserted successfully at index " + i);
            }
        }
        System.out.println("");
        showMenu();
    }

    //insert
    static void insert() {
        if (num < MAX) {
            int i = num;

            num++;

            System.out.println("Insert new Employee record in the table");
            if (emp[i] == null) {
                emp[i] = new employee();
            }
            System.out.print("Enter Name: ");
            emp[i].name = sc.next();

            System.out.print("Enter Emopoyee ID: ");
            long inputCode = sc.nextLong();
            while (isCodeExists(inputCode, i)) {
                System.out.println("Employee ID already exists! Please enter a unique ID.");
                System.out.print("Enter Emopoyee ID: ");
                inputCode = sc.nextLong();
            }
            emp[i].code = inputCode;

            System.out.print("Enter Designation: ");
            emp[i].designation = sc.next();

            System.out.print("Enter Experience: ");
            emp[i].exp = sc.nextInt();

            System.out.print("Enter Age: ");
            emp[i].age = sc.nextInt();

            System.out.println("Record Inserted successfully at index " + i);
        } else {
            System.out.println("System is full can't add anymore entries in the table !");
        }
        System.out.println("");
        showMenu();
    }

    //delete
    static void deleteIndex(int i) {
        for (int j = i; j < num - 1; j++) {
            emp[j].name = emp[j + 1].name;
            emp[j].code = emp[j + 1].code;
            emp[j].designation = emp[j + 1].designation;
            emp[j].exp = emp[j + 1].exp;
            emp[j].age = emp[j + 1].age;
        }
    }

    static void deleteRecord() {
        if (num == 0) {
            System.out.println("The table is empty. Please build the table or insert records first.");
            showMenu();
            return;
        }
        System.out.println("Delete an existing record from the table, Enter Employee ID to delete: ");
        long code = sc.nextLong();

        boolean found = false;
        for (int i = 0; i < num; i++) {
            if (emp[i].code == code) {
                deleteIndex(i);
                num--;
                System.out.println("Record deleted successfully");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No such employee code exists in the table");
        }
        System.out.println("");
        showMenu();
    }

    //search
    static void searchRecord() {
        if (num == 0) {
            System.out.println("The table is empty. Please build the table or insert records first.");
            showMenu();
            return;
        }
        System.out.println("Search an existing record in the table, Enter Employee ID to search: ");
        long code = sc.nextLong();

        boolean found = false;
        for (int i = 0; i < num; i++) {
            if (emp[i].code == code) {
                System.out.println("Name: " + emp[i].name);
                System.out.println("Employee ID: " + emp[i].code);
                System.out.println("Designation: " + emp[i].designation);
                System.out.println("Experience: " + emp[i].exp);
                System.out.println("Age: " + emp[i].age);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No such employee code exists in the table");
        }
        System.out.println("");
        showMenu();
    }

    //showMenu()
    static void showMenu() {

        System.out.println("++++=====EMS=====++++");

        System.err.println("Options:");
        System.err.println("Build Table-> 1");
        System.err.println("Insert new record-> 2");
        System.err.println("Delete entry-> 3");
        System.err.println("search a record-> 4");
        System.out.println("show table -> 5");
        System.err.println("Exit-> 6");

        int option = sc.nextInt();

        switch (option) {
            case 1 ->
                build();
            case 2 ->
                insert();
            case 3 ->
                deleteRecord();
            case 4 ->
                searchRecord();
            case 5 ->
                showTable();
            case 6 ->
                System.exit(0);
            default -> {
                System.out.println("Expected Options" + " are 1/2/3/4/5/6");
                showMenu();
            }
        }

    }

    static void showTable() {
        if (num == 0) {
            System.out.println("The table is empty. Please build the table or insert records first.");
            System.out.println("");
            showMenu();
            return;
        }

        System.out.println("---------------------------------------------------------------------------------");
        System.out.printf("%-20s | %-15s | %-20s | %-10s | %-5s%n", "Name", "Employee ID", "Designation", "Experience", "Age");
        System.out.println("---------------------------------------------------------------------------------");

        for (int i = 0; i < num; i++) {
            System.out.printf("%-20s | %-15d | %-20s | %-10d | %-5d%n",
                    emp[i].name,
                    emp[i].code,
                    emp[i].designation,
                    emp[i].exp,
                    emp[i].age);
        }
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("");
        showMenu();
    }

    public static void main(String[] args) {
        showMenu();
    }
}
