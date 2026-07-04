const fs = require('fs');
const path = require('path');

const dataPath = path.join(__dirname, 'data.js');
let content = fs.readFileSync(dataPath, 'utf8');

const jsonStart = content.indexOf('{');
const jsonEnd = content.lastIndexOf('}') + 1;
const jsonStr = content.substring(jsonStart, jsonEnd);

let data = JSON.parse(jsonStr);

data["DSA"] = {
  "children": {
    "Data Structures": {
      "grandchildren": {
        "Arrays (1D & 2D)": { "completed": false, "notes": "" },
        "Strings & StringBuilders": { "completed": false, "notes": "" },
        "Linked Lists (Singly, Doubly, Circular)": { "completed": false, "notes": "" },
        "Stacks": { "completed": false, "notes": "" },
        "Queues (Simple, Circular, Deque)": { "completed": false, "notes": "" },
        "PriorityQueue (Heaps)": { "completed": false, "notes": "" },
        "HashMaps & HashSets": { "completed": false, "notes": "" },
        "Trees (Binary Tree, BST, AVL)": { "completed": false, "notes": "" },
        "Trie": { "completed": false, "notes": "" },
        "Graphs (Adjacency Matrix/List)": { "completed": false, "notes": "" }
      }
    },
    "Algorithms": {
      "grandchildren": {
        "Time & Space Complexity (Big O)": { "completed": false, "notes": "" },
        "Sorting (Bubble, Insertion, Selection)": { "completed": false, "notes": "" },
        "Sorting (Merge, Quick, Heap)": { "completed": false, "notes": "" },
        "Searching (Linear, Binary)": { "completed": false, "notes": "" },
        "Two Pointers & Sliding Window": { "completed": false, "notes": "" },
        "Recursion & Backtracking": { "completed": false, "notes": "" },
        "Graph Traversal (BFS, DFS)": { "completed": false, "notes": "" },
        "Shortest Path (Dijkstra, Bellman-Ford)": { "completed": false, "notes": "" },
        "Minimum Spanning Tree (Kruskal, Prim)": { "completed": false, "notes": "" },
        "Dynamic Programming (Memoization, Tabulation)": { "completed": false, "notes": "" },
        "Greedy Algorithms": { "completed": false, "notes": "" },
        "Bit Manipulation": { "completed": false, "notes": "" }
      }
    },
    "Practice Problems (15+ Items)": {
      "grandchildren": {
        "1. Two Sum (Array, Hash)": { "completed": false, "notes": "" },
        "2. Best Time to Buy and Sell Stock": { "completed": false, "notes": "" },
        "3. Contains Duplicate": { "completed": false, "notes": "" },
        "4. Product of Array Except Self": { "completed": false, "notes": "" },
        "5. Maximum Subarray": { "completed": false, "notes": "" },
        "6. Reverse Linked List": { "completed": false, "notes": "" },
        "7. Merge Two Sorted Lists": { "completed": false, "notes": "" },
        "8. Valid Parentheses": { "completed": false, "notes": "" },
        "9. Search in Rotated Sorted Array": { "completed": false, "notes": "" },
        "10. Climbing Stairs (DP)": { "completed": false, "notes": "" },
        "11. Number of Islands (Graph)": { "completed": false, "notes": "" },
        "12. Invert Binary Tree": { "completed": false, "notes": "" },
        "13. Maximum Depth of Binary Tree": { "completed": false, "notes": "" },
        "14. Lowest Common Ancestor of a BST": { "completed": false, "notes": "" },
        "15. Clone Graph": { "completed": false, "notes": "" },
        "16. Top K Frequent Elements": { "completed": false, "notes": "" },
        "17. Longest Substring Without Repeating Characters": { "completed": false, "notes": "" }
      }
    },
    "Interview Questions (40+ Items)": {
      "grandchildren": {
        "Q1. How does a HashMap work internally in Java?": { "completed": false, "notes": "" },
        "Q2. Array vs LinkedList?": { "completed": false, "notes": "" },
        "Q3. ArrayList vs Vector?": { "completed": false, "notes": "" },
        "Q4. Difference between HashSet and TreeSet?": { "completed": false, "notes": "" },
        "Q5. Explain collision resolution in HashMaps.": { "completed": false, "notes": "" },
        "Q6. How does PriorityQueue work?": { "completed": false, "notes": "" },
        "Q7. What is a balanced tree (AVL/Red-Black)?": { "completed": false, "notes": "" },
        "Q8. How to find a cycle in a Linked List?": { "completed": false, "notes": "" },
        "Q9. BFS vs DFS - when to use which?": { "completed": false, "notes": "" },
        "Q10. Explain Dijkstra's algorithm.": { "completed": false, "notes": "" },
        "Q11. Tail recursion vs Normal recursion?": { "completed": false, "notes": "" },
        "Q12. What is topological sorting?": { "completed": false, "notes": "" },
        "Q13. Difference between Memoization and Tabulation?": { "completed": false, "notes": "" },
        "Q14. How does QuickSort partition work?": { "completed": false, "notes": "" },
        "Q15. MergeSort vs QuickSort?": { "completed": false, "notes": "" },
        "Q16. What is a Trie and its use cases?": { "completed": false, "notes": "" },
        "Q17. Explain Binary Search Time Complexity.": { "completed": false, "notes": "" },
        "Q18. What is the Master Theorem?": { "completed": false, "notes": "" },
        "Q19. How to reverse a string in place?": { "completed": false, "notes": "" },
        "Q20. Explain the sliding window technique.": { "completed": false, "notes": "" },
        "Q21. How to find the middle of a Linked List in one pass?": { "completed": false, "notes": "" },
        "Q22. Can you implement a Stack using Queues?": { "completed": false, "notes": "" },
        "Q23. Can you implement a Queue using Stacks?": { "completed": false, "notes": "" },
        "Q24. What is a Disjoint Set (Union-Find)?": { "completed": false, "notes": "" },
        "Q25. Explain Kadane's Algorithm.": { "completed": false, "notes": "" },
        "Q26. What is Floyd's Cycle-Finding Algorithm?": { "completed": false, "notes": "" },
        "Q27. How does a Min-Heap differ from a Max-Heap?": { "completed": false, "notes": "" },
        "Q28. What is a sparse matrix?": { "completed": false, "notes": "" },
        "Q29. Difference between B-Tree and B+ Tree?": { "completed": false, "notes": "" },
        "Q30. Explain Bellman-Ford algorithm.": { "completed": false, "notes": "" },
        "Q31. How to detect negative weight cycles?": { "completed": false, "notes": "" },
        "Q32. What is an articulation point in a graph?": { "completed": false, "notes": "" },
        "Q33. What are strongly connected components?": { "completed": false, "notes": "" },
        "Q34. Explain the Knapsack Problem.": { "completed": false, "notes": "" },
        "Q35. Longest Common Subsequence vs Longest Increasing Subsequence?": { "completed": false, "notes": "" },
        "Q36. What is a bloom filter?": { "completed": false, "notes": "" },
        "Q37. What is LRU Cache and how to implement it?": { "completed": false, "notes": "" },
        "Q38. Explain Bit Masking.": { "completed": false, "notes": "" },
        "Q39. What is the XOR trick for finding missing numbers?": { "completed": false, "notes": "" },
        "Q40. How do you serialize and deserialize a binary tree?": { "completed": false, "notes": "" },
        "Q41. What is the difference between NP-Hard and NP-Complete?": { "completed": false, "notes": "" },
        "Q42. How does A* search algorithm work?": { "completed": false, "notes": "" }
      }
    }
  }
};

const newJs = `export const defaultState = ${JSON.stringify(data, null, 4)};\n`;
fs.writeFileSync(dataPath, newJs, 'utf8');
console.log('DSA Successfully added to data.js');
