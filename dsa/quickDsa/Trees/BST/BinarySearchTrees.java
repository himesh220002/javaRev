
import java.util.*;

public class BinarySearchTrees {

    static class Node {

        int data;
        Node left;
        Node right;

        Node(int val) {
            this.data = val;
            this.left = null;
            this.right = null;
        }
    }

    static class BinaryTree {

        static int idx = -1;

        public Node buildTree(int[] nodes) {
            idx++;
            if (nodes[idx] == -1) {
                return null;
            }

            Node newNode = new Node(nodes[idx]);
            newNode.left = buildTree(nodes);
            newNode.right = buildTree(nodes);

            return newNode;
        }

    }

    // Unbalanced BST
    static class BinarySearchTree {

        public Node insert(Node root, int val) {
            if (root == null) {
                return new Node(val);
            }
            if (val < root.data) {
                root.left = insert(root.left, val);
            } else {
                root.right = insert(root.right, val);
            }
            return root;
        }

        public Node buildBST(int[] nodes) {
            Node root = null;
            for (int val : nodes) {
                if (val != -1) {
                    root = insert(root, val);
                }
            }
            return root;
        }
    }

    // Balanced BST
    static class BalancedBST {

        public Node buildBalancedBST(int[] nodes) {
            List<Integer> values = new ArrayList<>();
            for (int val : nodes) {
                if (val != -1) {
                    values.add(val);
                }
            }
            Collections.sort(values);
            return buildBalancedBSTHelper(values, 0, values.size() - 1);
        }

        private Node buildBalancedBSTHelper(List<Integer> values, int start, int end) {
            if (start > end) {
                return null;
            }
            int mid = (start + end) / 2;
            Node root = new Node(values.get(mid));
            root.left = buildBalancedBSTHelper(values, start, mid - 1);
            root.right = buildBalancedBSTHelper(values, mid + 1, end);
            return root;
        }
    }

    // search
    Node searchHelper(Node root, int val) {
        if (root == null || root.data == val) {
            return root;
        }
        if (val < root.data) {
            return searchHelper(root.left, val);
        } else {
            return searchHelper(root.right, val);
        }
    }

    boolean search(Node root, int val) {
        Node result = searchHelper(root, val);
        return result != null;
    }

    // preOrder traversal
    private static void preorder(Node root) {
        if (root == null) {
            return;
        }
        System.out.print(root.data + " ");
        preorder(root.left);
        preorder(root.right);
    }

    // inOrder traversal
    private static void inorder(Node root) {
        if (root == null) {
            return;
        }
        inorder(root.left);
        System.out.print(root.data + " ");
        inorder(root.right);
    }

    // postOrder traversal
    private static void postorder(Node root) {
        if (root == null) {
            return;
        }
        postorder(root.left);
        postorder(root.right);
        System.out.print(root.data + " ");
    }

    // public static Node insert(Node root, int val)
    public static void main(String[] args) {
        int[] nodes = {1, 2, 4, -1, -1, 5, -1, -1, 3, -1, 6, -1, -1};
        for (int x : nodes) {
            System.out.print(x + ", ");
        }
        System.out.println();

        BinaryTree BT = new BinaryTree();
        Node root = BT.buildTree(nodes);
        System.out.println("\nroot :" + root.data);

        System.out.print("preorder: ");
        preorder(root);
        System.out.print("\nInorder: ");
        inorder(root);
        System.out.print("\nPostorder: ");
        postorder(root);
        System.out.println("\n");

        BinarySearchTrees searchbst = new BinarySearchTrees();

        // BST unbalanced
        BinarySearchTree bst = new BinarySearchTree();

        Node root1 = bst.buildBST(nodes);
        System.out.println("\nroot1 :" + root1.data);

        System.out.print("\nBST preorder: ");
        preorder(root1);
        System.out.print("\nBST Inorder: ");
        inorder(root1);
        System.out.print("\nBST Postorder: ");
        postorder(root1);
        System.out.println("\n");

        // search in unbalance O(n)
        System.out.println("search 4 in BST: " + searchbst.search(root1, 4));
        System.out.println("search 7 in BST: " + searchbst.search(root1, 7));

        // Balanced BST
        BalancedBST bbst = new BalancedBST();
        Node root2 = bbst.buildBalancedBST(nodes);
        System.out.print("\nBalanced BST --------- ");
        System.out.println("\nroot2 :" + root2.data);

        System.out.print("\npreorder: ");
        preorder(root2);
        System.out.print("\nInorder: ");
        inorder(root2);
        System.out.print("\nPostorder: ");
        postorder(root2);
        System.out.println("\n");

        // search in balance BST O(log n)
        System.out.println("search 4 in Balanced BST: " + searchbst.search(root2, 4));
        System.out.println("search 7 in Balanced BST: " + searchbst.search(root2, 7));

    }
}
