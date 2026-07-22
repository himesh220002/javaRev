
public class Linkedlist {

    Node head;

    class Node {

        Object data;
        Node next;

        public Node(Object new_data) {
            this.data = new_data;
            this.next = null;
        }
    }

    public void addFirst(Object data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            return;
        }

        newNode.next = head;
        head = newNode;
    }

    public void addLast(Object data) {
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

    public void display() {

        if (head == null) {
            System.out.println("The list is empty");
            return;
        }
        Node currNode = head;

        while (currNode != null) {
            System.out.print(currNode.data + "->");
            currNode = currNode.next;
        }

        System.out.println("null");
    }

    public void deleteFirst() {
        if (head == null) {
            System.out.println("The list is empty");
            return;
        }

        head = head.next;

    }

    public void deleteLast() {
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

    public static void main(String[] args) {
        Linkedlist list = new Linkedlist();

        list.display();     // The list is empty
        list.addFirst(1);   // 1->null
        list.addFirst(2);   // 2->1->null
        list.addFirst(3);   // 3->2->1->null
        list.addFirst(4);   // 4->3->2->1->null
        list.addLast(5);    // 4->3->2->1->5->null
        list.display();
        list.addLast(6);
        list.display();

        list.addFirst("hey");
        list.addLast("ok");
        list.display();

        list.deleteFirst();
        list.display();

        list.deleteLast();
        list.display();

    }

}
