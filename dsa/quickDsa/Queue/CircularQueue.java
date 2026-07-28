
class CircularQueue {

    static class Queue {

        int arr[];
        int size;

        int rear = -1;
        int front = -1;

        Queue(int n) {
            arr = new int[n];
            this.size = n;
        }

        public boolean isEmpty() {
            return rear == -1 && front == -1;
        }

        public boolean isFull() {
            return (rear + 1) % size == front;
        }

        //add
        public void add(int val) {
            if (isFull()) {
                System.out.println("queue is full");
                return;
            }

            if (front == -1) {
                front = 0;
            }

            rear = (rear + 1) % size;
            arr[rear] = val;

        }

        //dequeue
        public int remove() {
            if (isEmpty()) {
                System.out.println("queue is empty , can't remove any element");
                return -1;
            }
            int frontVal = arr[front];

            if (front == rear) {
                front = rear = -1;
            } else {
                front = (front + 1) % size;
            }
            return frontVal;
        }

        //peek 
        public int peek() {
            if (isEmpty()) {
                System.out.println("queue is empty , can't peek");
                return -1;
            }

            return arr[front];
        }

    }

    public static void main(String[] args) {
        Queue q = new Queue(5);
        // Initial state:    [  ,  ,  ,  ,  ]      front = -1, rear = -1

        q.add(10);
        // added 10:         [10(f,r),  ,  ,  ,  ] front = 0, rear = 0

        q.add(20);
        // added 20:         [10(f), 20(r),  ,  ,  ] front = 0, rear = 1

        q.add(30);
        // added 30:         [10(f), 20, 30(r),  ,  ] front = 0, rear = 2

        q.add(40);
        // added 40:         [10(f), 20, 30, 40(r),  ] front = 0, rear = 3

        q.add(50);
        // added 50:         [10(f), 20, 30, 40, 50(r)] front = 0, rear = 4
        // QUEUE IS NOW FULL. (4+1)%5 = 0, which equals front.

        System.out.println("removed " + q.remove());
        // removed 10:       [ (empty), 20(f), 30, 40, 50(r)]  front = 1, rear = 4
        // Space is freed up at index 0.

        q.add(55);
        // added 55:         [55(r), 20(f), 30, 40, 50]  front = 1, rear = 0
        // Circular wrap! Rear goes back to index 0. QUEUE IS FULL AGAIN.

        System.out.println("removed " + q.remove());
        // removed 20:       [55(r), (empty), 30(f), 40, 50]  front = 2, rear = 0
        // Space is freed up at index 1.

        q.add(60);
        // added 60:         [55, 60(r), 30(f), 40, 50]  front = 2, rear = 1
        // QUEUE IS FULL AGAIN. (1+1)%5 = 2, which equals front.

        q.add(70);
        // Fails (Queue Full): State UNCHANGED! [55, 60(r), 30(f), 40, 50] front = 2, rear = 1

        q.add(80);
        // Fails (Queue Full): State UNCHANGED! [55, 60(r), 30(f), 40, 50] front = 2, rear = 1

        System.out.println("\n--- Remaining Elements ---");
        while (!q.isEmpty()) {
            System.out.println(q.peek());
            q.remove();
        }

    }
}
