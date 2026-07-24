
class DLinkList {

    static Node head;
    static Node tail;

    public static class Node {

        Object data;
        Node prev;
        Node next;

        public Node(Object val) {
            this.data = val;
            this.next = null;
            this.prev = null;
        }
    }

    // addFirst
    public static void addFirst(Object data) {
        Node newNode = new Node(data);

        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
    }

    //addLast
    public static void addLast(Object data) {
        Node newNode = new Node(data);

        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
    }

    // insert at position
    public static void insertAtPos(Object data, int pos) {
        Node newNode = new Node(data);
        if (head == null) {
            if (pos != 1) {
                System.out.println("invalid position" + pos
                        + ", inserted at end instead.");
            }
            head = tail = newNode;
            return;
        }

        Node curr = head;

        for (int i = 1; i < pos && curr != null; i++) {
            curr = curr.next;
        }
        if (curr == null) {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
            System.out.println("POSITION is beyond the range, so auto-inserted at the end");
            return;
        }

        newNode.prev = curr.prev;
        newNode.next = curr;
        if (curr.prev != null) {
            curr.prev.next = newNode;
        }
        curr.prev = newNode;

        /*or
        newNode.next = curr;
        newNode.prev = curr.prev;
        if(curr.prev != null){
            curr.prev.next = newNode;
        }
        curr.prev = newNode;
         */
    }

    // removeFirst
    public static void removeFirst() {
        if (head == null) {
            System.out.println("The list is empty, nothing to delete");
            return;
        }

        Node temp = head;
        head = head.next;

        if (head != null) {
            head.prev = null;
        }

        temp.next = null;
    }

    //removeLast
    public static void removeLast() {
        if (tail == null) {
            System.out.println("the list is empty, nothing to delete");
            return;
        }

        Node temp = tail;
        tail = tail.prev;

        if (tail != null) {
            tail.next = null;
        }
        temp.prev = null;
    }

    // deleteAtPos
    public static void deleteAtPos(int pos) {
        if (head == null) {
            System.out.println("The list is empty, nothing to delete");
            return;
        }
        Node curr = head;

        for (int i = 1; i < pos && curr != null; i++) {
            curr = curr.next;
        }

        if (curr == null) {
            System.out.println("Invalid position " + pos + "nothing to delete");
            return;
        }

        if (curr.next != null) {
            curr.next.prev = curr.prev;
        }
        if (curr.prev != null) {
            curr.prev.next = curr.next;
        }
        if (head == curr) {
            head = curr.next;
        }
        if (tail == curr) {
            tail = curr.prev;
        }
        curr.next = null;
        curr.prev = null;
    }

    public static Node display() {
        Node temp = head;
        System.out.print("null <-> ");
        while (temp != null) {
            System.out.print(temp.data + " <-> ");
            temp = temp.next;
        }
        System.out.println("null");
        return head;
    }

    public static void main(String[] args) {

        addFirst(10);
        addFirst(20);
        addFirst(30);

        addLast(40);
        display();

        removeFirst();
        removeLast();

        display();

        insertAtPos(60, 2);
        display();

        insertAtPos(100, 6);
        display();

        insertAtPos(22, 2);
        display();

        deleteAtPos(4);
        display();
    }

}
