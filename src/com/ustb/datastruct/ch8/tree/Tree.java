package com.ustb.datastruct.ch8.tree;

import jdk.nashorn.internal.ir.WhileNode;

import java.util.*;

/**
 * This is a class about Tree DataStruct
 *
 * @author re-get
 * @since 1.0.0
 */
public class Tree<K, V> {
    private Node<K, V> root;
    private final Comparator<? super K> comparator;
    private static final int MAX_BLANKS = 32;
    private static final int BLANKS_TIMES = 2;
    private static final int APPEND_BLANKS = 2;

    public Tree(Comparator<? super K> comparator) {
        this.comparator = comparator;
        root = null;
    }

    /**
     * This is a class about the basic node of tree
     *
     * @author re-get
     * @since 1.0.0
     */
    static final class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> leftChild;
        private Node<K, V> rightChild;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.leftChild = null;
            this.rightChild = null;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append('{').append(key.toString()).append(',').append(value.toString()).append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Node<?, ?> node = (Node<?, ?>) o;
            return Objects.equals(key, node.key) &&
                    Objects.equals(value, node.value) &&
                    Objects.equals(leftChild, node.leftChild) &&
                    Objects.equals(rightChild, node.rightChild);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }

    }

    /**
     * This function to find a node which key is key
     *
     * @return the node to find by key
     */
    public Node<K, V> find(K key) {
        Node<K, V> current = root;
        while (current != null) {
            if (current.key == key) {
                return current;
            } else if (this.comparator.compare(current.key, key) > 0) {
                current = current.leftChild;
            } else {
                current = current.rightChild;
            }
        }
        return null;
    }

    /**
     * This function to insert a node into tree
     * If the key is already exist in the ree it will overwrite the node
     */
    public void insert(K key, V value) {
        Node<K, V> iNode = new Node<>(key, value);
        if (root == null) {
            root = iNode;
        } else {
            Node<K, V> current = root;
            while (true) {
                if (this.comparator.compare(current.key, key) == 0) {
                    current.value = value;
                    return;
                } else if (this.comparator.compare(current.key, key) > 0) {
                    if (current.leftChild == null) {
                        current.leftChild = iNode;
                        return;
                    } else {
                        current = current.leftChild;
                    }
                } else {
                    if (current.rightChild == null) {
                        current.rightChild = iNode;
                        return;
                    } else {
                        current = current.rightChild;
                    }
                }
            }
        }
    }

    /**
     * @param key key is the key word to delete node
     * @return if the node is delete
     */
    public boolean delete(K key) {
        if (root == null) {
            return false;
        } else {
            Node<K, V> current = root;
            Node<K, V> currentParent = null;
            boolean isLeftChild = true;
            while (true) {
                if (current == null) {
                    return false;
                } else if (this.comparator.compare(current.key, key) == 0) {
                    break;
                } else if (this.comparator.compare(current.key, key) > 0) {
                    currentParent = current;
                    current = current.leftChild;
                    isLeftChild = true;
                } else {
                    currentParent = current;
                    current = current.rightChild;
                    isLeftChild = false;
                }
            }
            // 分情况讨论
            if (current.leftChild == null
                    && current.rightChild == null) {
                // 1:子节点均为null
                if (currentParent == null) {
                    root = null;
                } else {
                    if (isLeftChild) {
                        currentParent.leftChild = null;
                    } else {
                        currentParent.rightChild = null;
                    }
                }
            } else if (current.leftChild == null) {
                // 2:左节点为null
                if (currentParent == null) {
                    root = root.rightChild;
                } else {
                    if (isLeftChild) {
                        currentParent.leftChild = current.rightChild;
                    } else {
                        currentParent.rightChild = current.rightChild;
                    }
                }
            } else if (current.rightChild == null) {
                // 3:右节点为null
                if (currentParent == null) {
                    root = root.leftChild;
                } else {
                    if (isLeftChild) {
                        currentParent.leftChild = current.leftChild;
                    } else {
                        currentParent.rightChild = current.leftChild;
                    }
                }
            } else {
                // 4:左右节点均不为空
                Node<K, V> successor = getSuccessor(current);
                successor.leftChild = current.leftChild;
                if (currentParent == null) {
                    root = successor;
                } else if (isLeftChild) {
                    currentParent.leftChild = successor;
                } else {
                    currentParent.rightChild = successor;
                }
            }
            return true;
        }
    }

    public List<Node<K, V>> preOrder() {
        return preOrder(root);
    }

    public List<Node<K, V>> preOrder(Node<K, V> node) {
        List<Node<K, V>> preList = new ArrayList<>();
        Stack<Node<K, V>> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                preList.add(node);
                stack.push(node);
                node = node.leftChild;
            }
            if (!stack.isEmpty()) {
                node = stack.pop();
                node = node.rightChild;
            }
        }
        return preList;
    }

    public List<Node<K, V>> inOrder() {
        return inOrder(root);
    }

    public List<Node<K, V>> inOrder(Node<K, V> node) {
        List<Node<K, V>> inList = new ArrayList<>();
        Stack<Node<K, V>> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.add(node);
                node = node.leftChild;
            }
            if (!stack.isEmpty()) {
                node = stack.pop();
                inList.add(node);
                node = node.rightChild;
            }
        }
        return inList;
    }

    public List<Node<K, V>> postOrder() {
        return postOrder(root);
    }

    public List<Node<K, V>> postOrder(Node<K, V> node) {
        List<Node<K, V>> postList = new ArrayList<>();
        Stack<Node<K, V>> stack = new Stack<>();
        Stack<Boolean> flagStack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                flagStack.push(false);
                node = node.leftChild;
            }
            while (!stack.isEmpty() && flagStack.peek()) {
                postList.add(stack.pop());
                flagStack.pop();
            }
            if (!stack.isEmpty()) {
                flagStack.pop();
                flagStack.push(true);
                node = stack.peek().rightChild;
            }
        }
        return postList;
    }

    public List<Node<K, V>> layerOrder() {
        List<Node<K, V>> layerList = new ArrayList<>();
        Queue<Node<K, V>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            for (int i = 0, n = queue.size(); i < n; i++) {
                Node<K, V> temp = queue.poll();
                layerList.add(temp);
                assert temp != null;
                Node<K, V> leftChild = temp.leftChild;
                Node<K, V> rightChild = temp.rightChild;
                if (leftChild != null) {
                    queue.offer(leftChild);
                }
                if (rightChild != null) {
                    queue.offer(rightChild);
                }
            }
        }
        return layerList;
    }

    private Node<K, V> getSuccessor(Node<K, V> delNode) {
        Node<K, V> currentParent = delNode;
        Node<K, V> current = currentParent.rightChild;
        while (current.leftChild != null) {
            currentParent = current;
            current = current.leftChild;
        }
        if (current != delNode.rightChild) {
            currentParent.leftChild = current.rightChild;
            current.rightChild = delNode.rightChild;
        }
        return current;
    }

    public void displayTree() {
        int nBlanks = MAX_BLANKS;
        boolean isRowEmpty = false;
        Queue<Node<K, V>> queue = new LinkedList<>();
        queue.offer(root);
        for (int i = 0; i < MAX_BLANKS * BLANKS_TIMES + APPEND_BLANKS; i++) {
            System.out.print('*');
        }
        System.out.println();
        while (!isRowEmpty) {
            isRowEmpty = true;
            for (int j = 0; j < nBlanks; j++) {
                System.out.print(' ');
            }
            for (int i = 0, n = queue.size(); i < n; i++) {
                Node<K, V> temp = queue.poll();
                if (temp == null) {
                    System.out.print("--");
                    queue.offer(null);
                    queue.offer(null);
                } else {
                    isRowEmpty = false;
                    System.out.print(temp.key);
                    queue.offer(temp.leftChild);
                    queue.offer(temp.rightChild);
                }
                for (int j = 0; j < nBlanks * BLANKS_TIMES - APPEND_BLANKS; j++) {
                    System.out.print(' ');
                }
            }
            System.out.println();
            nBlanks /= 2;
            for (int i = 0; i < MAX_BLANKS * BLANKS_TIMES + APPEND_BLANKS; i++) {
                System.out.print('-');
            }
            System.out.println();
        }
    }
}