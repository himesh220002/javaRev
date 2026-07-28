
class Queuee<T> {

    public class Node {

        T data;
        Node next;

        Node(T val) {
            this.data = val;
            this.next = null;
        }
    }

    Node head = null;
    Node tail = null;

    public boolean isEmpty() {
        return head == null && tail == null;
    }

    // enqueue
    public void add(T val) {
        Node newNode = new Node(val);

        if (isEmpty()) {
            head = tail = newNode;
            return;
        }

        tail.next = newNode;
        tail = newNode;
    }

    // dequeue
    public T remove() {
        if (isEmpty()) {
            System.out.println("Queue is empty , can't remove any element");
            return null;
        }
        T front = head.data;

        if (head == tail) {
            tail = null;
        }

        head = head.next;
        return front;
    }

    // peek 
    public T peek() {

        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return null;
        }

        return head.data;
    }

    public static void main(String[] args) {
        Queuee<Object> q = new Queuee<>();

        q.add(11);
        q.add(22);
        q.add(33);
        q.add(44);
        q.add("hey");

        q.add(55);

        System.out.println("peeking " + q.peek());
        System.out.println("Removing " + q.remove());
        System.out.println("peeking " + q.peek());

        System.out.println("");

        while (!q.isEmpty()) {
            System.out.println(q.peek());
            q.remove();
        }

    }
}
