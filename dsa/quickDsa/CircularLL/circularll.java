
class circularll {

    public static class Node {

        Object data;
        Node next;

        Node(Object val) {
            this.data = val;
            this.next = null;
        }
    }
    public static Node head;
    public static Node tail;

// insert at head
    public static void insertAtBegining(Object val) {
        Node newNode = new Node(val);

        if (tail == null) {
            tail = head = newNode;
            tail.next = head;
        } else {
            newNode.next = head;
            tail.next = newNode;
            head = newNode;
        }
    }
// insert at tail

    public static void insertAtEnd(Object val) {
        Node newNode = new Node(val);

        if (tail == null) {
            tail = head = newNode;
            tail.next = head;
        } else {
            newNode.next = head;
            tail.next = newNode;
            tail = newNode;
        }
    }

// insert at position
    public static void insertAtPos(Object val, int pos) {
        Node newNode = new Node(val);
        // Case 1: Empty list
        if (head == null) {
            head = tail = newNode;
            tail.next = head;
            return;
        }
        // Case 2: Insert at head
        if (pos == 1) {
            newNode.next = head;
            tail.next = newNode;
            head = newNode;
            return;
        }
        // Traverse to node at position
        Node curr = head;
        for (int i = 1; i < pos - 1 && curr.next != head; i++) {
            curr = curr.next;
        }
        // Case 3: Insert at end (pos > length)
        if (curr.next == head) {
            newNode.next = head;
            curr.next = newNode;
            tail = newNode;
            return;
        }
        // Case 4: Insert in middle
        newNode.next = curr.next;
        curr.next = newNode;
    }

// delete at head
    public static void deleteAtBegining() {
        if (tail == null) {
            System.out.println("list is empty");
            return;
        }

        if (head == tail) {
            head = tail = null;

        } else {
            Node temp = head;

            head = head.next;
            tail.next = head;
            temp.next = null;
        }
    }

// delete at tail
    public static void deleteAtEnd() {
        if (tail == null) {
            return;
        }

        if (head == tail) {
            tail = null;
        } else {
            Node temp = head;
            while (temp.next != tail) {
                temp = temp.next;
            }
            tail.next = null;
            temp.next = head;
            tail = temp;
        }
    }
// delete at position

    public static void deleteAtPos(int pos) {
        if (head == null) {
            System.out.println("list is empty, nothing to delete");
            return;
        }

        //Case 1: delete head
        if (pos == 1) {
            if (head == tail) {  // only one node
                head = tail = null;
            } else {  // more than one node
                head = head.next;
                tail.next = head;
            }
            return;
        }
        Node curr = head;
        for (int i = 1; i < pos - 1 && curr.next != head; i++) {
            curr = curr.next;
        }

        if (curr.next == head) {
            System.out.println("Position " + pos + " is beyond list length");
            return;
        }
        // Case 2: Delete tail
        if (curr.next == tail) {
            tail = curr;
            tail.next = head;
        } else {  //Case 3: Delete in middle
            curr.next = curr.next.next;
        }

    }

// display
    public static void display() {
        if (head == null) {
            System.out.println("The list is empty");
            return;
        }
        System.out.print(head.data + " -> ");
        Node temp = head.next;
        while (temp != tail.next) {
            System.out.print(temp.data + " -> ");
            temp = temp.next;
        }
        System.out.println(temp.data + "(Head)");

    }

    public static void main(String[] args) {
        insertAtBegining(10);
        insertAtBegining(20);
        insertAtBegining(30);
        insertAtEnd(40);
        display();
        deleteAtBegining();
        display();
        deleteAtEnd();
        display();
        insertAtPos(90, 1);
        display();
        insertAtPos(99, 2);
        display();
        deleteAtPos(3);
        display();
    }
}
