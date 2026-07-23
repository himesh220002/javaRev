
import java.util.Scanner;

public class Linkedlist {

    static Node head;

    public static class Node {

        Object data;
        Node next;

        public Node(Object new_data) {
            this.data = new_data;
            this.next = null;
        }
    }

    // inserting at first position
    public static void addFirst(Object data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            return;
        }

        newNode.next = head;
        head = newNode;
    }

    //inserting at last position
    public static void addLast(Object data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            return;
        }

        Node currNode = head;

        while (currNode.next != null) {
            currNode = currNode.next;
        }

        currNode.next = newNode;
    }

    // inserting at position 
    public static Node insertNode(Node head, int pos, Object value) {
        if (pos < 1) {
            System.out.println("Invalid position: " + pos + ". Must be >= 1.");
            return head;
        }

        if (pos == 1) {
            Node newNode = new Node(value);
            newNode.next = head;
            System.out.println("Inserted node at position 1: " + newNode.data);
            return newNode;
        }

        Node curr = head;

        for (int i = 1; i < pos - 1 && curr != null; i++) {
            curr = curr.next;
        }

        if (curr == null) {
            System.out.println("Position " + pos + " is beyond the list length. No insertion performed.");
            return head;
        }

        Node newNode = new Node(value);
        newNode.next = curr.next;
        curr.next = newNode;
        System.out.println("Inserted node at position " + pos + ": " + newNode.data);
        return head;
    }

    public static void display() {

        if (head == null) {
            System.out.println("The list is empty");
            return;
        }
        Node currNode = head;
        System.out.print("[[ ");
        while (currNode != null) {
            System.out.print(currNode.data + "->");
            currNode = currNode.next;
        }

        System.out.println("null ]]");
    }

    // delete first
    public static void deleteFirst() {
        if (head == null) {
            System.out.println("The list is empty");
            return;
        }

        head = head.next;

    }

    // delete last
    public static void deleteLast() {
        if (head == null) {
            System.out.println("The list is empty");
            return;
        }

        if (head.next == null) {    // if there is only one node available  { head } -> [1] -> null 
            head = null;            // { head } -> null
            return;
        }

        Node lastNode = head.next;
        Node secondLastNode = head;

        while (lastNode.next != null) {
            lastNode = lastNode.next;
            secondLastNode = secondLastNode.next;
        }
        secondLastNode.next = null;
    }

    //delete at specific position
    public static Node deleteAtSpecificPosition(Node head, int pos) {
        if (head == null) {
            System.out.println("List is empty, nothing to delete.");
            return head;
        }

        if (pos < 1) {
            System.out.println("Invalid position: " + pos + ". Must be >= 1.");
            return head;
        }

        if (pos == 1) {
            System.out.println("Deleted node at position 1: " + head.data);
            head = head.next;
            return head;
        }

        Node curr = head;
        for (int i = 1; i < pos - 1 && curr != null; i++) {
            curr = curr.next;
        }

        if (curr == null || curr.next == null) {
            System.out.println("Position " + pos + " is beyond the list length. No deletion performed.");
            return head;
        }

        System.out.println("Deleted node at position " + pos + ": " + curr.next.data);
        curr.next = curr.next.next;
        return head;
    }

    // iterative search time-O(n) space O(1)
    public static int search(Node head, Object key) {
        Node curr = head;
        int pos = 1;
        while (curr != null) {
            if (curr.data == key) {
                return pos;
            }
            curr = curr.next;
            pos++;
        }
        return -1;
    }

    //reverse a linked list
    public static Node reverseLinkedList(Node head) {
        Node curr = head;
        Node prev = null;
        Node next;

        while (curr != null) {
            next = curr.next;  // store next node
            curr.next = prev;  // reverse current node's pointer
            prev = curr;       // move prev to current node
            curr = next;       // move current node to next node
        }

        return prev;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nOptions for Linked List operation:");
            System.out.println("0. Display | 1. Search | 2. Add First | 3. Add Last");
            System.out.println("4. Insert at specific position | 5. Delete First");
            System.out.println("6. Delete Last | 7. Delete at specific position");
            System.out.println("8. Reverse Linked List | 9. Exit \n");

            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();
            int option;
            try {
                option = Integer.parseInt(choice);
            } catch (NumberFormatException e) {
                option = -1; // invalid
            }

            switch (option) {
                case 0 ->
                    display();

                case 1 -> {
                    System.out.print("Enter the value to search: ");
                    String input = sc.nextLine();
                    Object value;
                    try {
                        value = Integer.valueOf(input); // if it parses, store as Integer
                    } catch (NumberFormatException e) {
                        value = input; // otherwise store as String
                    }
                    int pos = search(head, value);
                    if (pos == -1) {
                        System.out.println("Element not found");
                    } else {
                        System.out.println("Element found at position " + pos);
                    }
                }

                case 2 -> {
                    System.out.print("Enter the value to insert at first: ");
                    String input = sc.nextLine();
                    Object value;
                    try {
                        value = Integer.valueOf(input); // if it parses, store as Integer
                    } catch (NumberFormatException e) {
                        value = input; // otherwise store as String
                    }
                    addFirst(value);
                    System.out.println("\nAdded at first position: ");
                    display();
                }

                case 3 -> {
                    System.out.print("Enter the value to insert at last: ");
                    String input = sc.nextLine();
                    Object value;
                    try {
                        value = Integer.valueOf(input); // if it parses, store as Integer
                    } catch (NumberFormatException e) {
                        value = input; // otherwise store as String
                    }
                    addLast(value);
                    System.out.println("\nAdded at last position: ");
                    display();
                }

                case 4 -> {
                    System.out.print("Enter the value to insert: ");
                    String input = sc.nextLine();
                    Object value;
                    try {
                        value = Integer.valueOf(input); // if it parses, store as Integer
                    } catch (NumberFormatException e) {
                        value = input; // otherwise store as String
                    }
                    System.out.print("Enter the position: ");
                    String posInput = sc.nextLine();
                    int position;
                    try {
                        position = Integer.parseInt(posInput);
                        head = insertNode(head, position, value);
                        System.out.println();
                        display();
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid position");
                    }
                }

                case 5 -> {
                    deleteFirst();
                    display();
                }

                case 6 -> {
                    deleteLast();
                    display();
                }

                case 7 -> {
                    System.out.print("Enter the position to delete: ");
                    String posInput = sc.nextLine();
                    int position;
                    try {
                        position = Integer.parseInt(posInput);
                        head = deleteAtSpecificPosition(head, position);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid position");
                    }
                    System.out.println();
                    display();
                }

                case 8 -> {
                    System.out.println("\nReversed Linked List: ");
                    head = reverseLinkedList(head);
                    display();
                }

                case 9 -> {
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);
                }

                default -> {
                    System.out.println("Invalid option");
                    sc.nextLine();
                }
            }
        }
    }

}
