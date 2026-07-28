
class stackLL<T> {

    public class Node {

        T data;
        Node next;

        Node(T val) {
            this.data = val;
            this.next = null;
        }
    }

    public Node top;

    public boolean isEmpty() {
        return top == null;
    }

    //push 
    public void push(T val) {
        Node newNode = new Node(val);

        if (isEmpty()) {
            top = newNode;
        } else {
            newNode.next = top;
            top = newNode;
        }
        System.out.println(val + " pushed to stack");

    }

    //pop
    public T pop() {
        if (isEmpty()) {
            System.out.println("Stack is Empty, nothing to pop");
            return null;
        }

        T val = top.data;
        top = top.next;
        // System.out.println(val + " popped from stack");
        return val;

    }

    //peak
    public T peek() {
        if (isEmpty()) {
            System.out.println("Stack is Empty, nothing to peek");
            return null;
        }

        return top.data;
    }

    public static void main(String[] args) {
        stackLL<Object> s = new stackLL<>();
        s.push(1);
        s.push(2);
        s.push(3);
        s.push(4);
        s.push("hey");

        System.out.println("");

        while (!s.isEmpty()) {
            System.out.println(s.peek());
            s.pop();
        }
    }
}
