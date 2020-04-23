package com.ustb.datastruct.ch8.tree;

import java.util.*;

/**
 * This is a class about the basic node of tree
 *
 * @author re-get
 * @since 1.0.0
 */
class Node<K, V> {
    private K iData;
    private V dData;
    private Node<K, V> leftChild;
    private Node<K, V> rightChild;

    public Node(K iData, V dData) {
        this.iData = iData;
        this.dData = dData;
        this.leftChild = null;
        this.rightChild = null;
    }

    public K getiData() {
        return iData;
    }

    public void setiData(K iData) {
        this.iData = iData;
    }

    public V getdData() {
        return dData;
    }

    public void setdData(V dData) {
        this.dData = dData;
    }

    public Node<K, V> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node<K, V> leftChild) {
        this.leftChild = leftChild;
    }

    public Node<K, V> getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node<K, V> rightChild) {
        this.rightChild = rightChild;
    }

    public void displayNode() {
        System.out.println('{' + iData.toString() + ", " + dData.toString() + "} ");
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
        return Objects.equals(iData, node.iData) &&
                Objects.equals(dData, node.dData) &&
                Objects.equals(leftChild, node.leftChild) &&
                Objects.equals(rightChild, node.rightChild);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iData, dData);
    }

}

/**
 * This is a class about Tree DataStruct
 *
 * @author re-get
 * @since 1.0.0
 */
public class Tree<K, V> {
    private Node<K, V> root;
    private final Comparator<? super K> comparator;
    private final int MAX_BLANKS = 32;
    private final int BLANKS_TIMES = 2;
    private final int APPEND_BLANKS = 2;

    public Tree(Comparator<? super K> comparator) {
        this.comparator = comparator;
        root = null;
    }

    /**
     * This function to find a node which iData is key
     *
     * @return the node to find by key
     */
    public Node<K, V> find(K key) {
        Node<K, V> current = root;
        while (current != null) {
            if (current.getiData() == key) {
                return current;
            } else if (this.comparator.compare(current.getiData(), key) > 0) {
                current = current.getLeftChild();
            } else {
                current = current.getRightChild();
            }
        }
        return null;
    }

    /**
     * This function to insert a node into tree
     * If the id is already exist in the ree it will overwrite the node
     */
    public void insert(K id, V dd) {
        Node<K, V> iNode = new Node<>(id, dd);
        if (root == null) {
            root = iNode;
        } else {
            Node<K, V> current = root;
            while (true) {
                if (this.comparator.compare(current.getiData(), id) == 0) {
                    current.setdData(dd);
                    return;
                } else if (this.comparator.compare(current.getiData(), id) > 0) {
                    if (current.getLeftChild() == null) {
                        current.setLeftChild(iNode);
                        return;
                    } else {
                        current = current.getLeftChild();
                    }
                } else {
                    if (current.getRightChild() == null) {
                        current.setRightChild(iNode);
                        return;
                    } else {
                        current = current.getRightChild();
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
                } else if (this.comparator.compare(current.getiData(), key) == 0) {
                    break;
                } else if (this.comparator.compare(current.getiData(), key) > 0) {
                    currentParent = current;
                    current = current.getLeftChild();
                    isLeftChild = true;
                } else {
                    currentParent = current;
                    current = current.getRightChild();
                    isLeftChild = false;
                }
            }
            // 分情况讨论
            if (current.getLeftChild() == null
                    && current.getRightChild() == null) {
                // 1:子节点均为null
                if (currentParent == null) {
                    root = null;
                } else {
                    if (isLeftChild) {
                        currentParent.setLeftChild(null);
                    } else {
                        currentParent.setRightChild(null);
                    }
                }
            } else if (current.getLeftChild() == null) {
                // 2:左节点为null
                if (currentParent == null) {
                    root = root.getRightChild();
                } else {
                    if (isLeftChild) {
                        currentParent.setLeftChild(current.getRightChild());
                    } else {
                        currentParent.setRightChild(current.getRightChild());
                    }
                }
            } else if (current.getRightChild() == null) {
                // 3:右节点为null
                if (currentParent == null) {
                    root = root.getLeftChild();
                } else {
                    if (isLeftChild) {
                        currentParent.setLeftChild(current.getLeftChild());
                    } else {
                        currentParent.setRightChild(current.getLeftChild());
                    }
                }
            } else {
                // 4:左右节点均不为空
                Node<K, V> successor = getSuccessor(current);
                successor.setLeftChild(current.getLeftChild());
                if (currentParent == null) {
                    root = successor;
                } else if (isLeftChild) {
                    currentParent.setLeftChild(successor);
                } else {
                    currentParent.setRightChild(successor);
                }
            }
            return true;
        }
    }

//    public List<Node<K,V>> preOrder() {
//        List<Node<K,V>> preList = new ArrayList<>();
//        Stack<Node<K,V>> stack = new Stack<>();
//
//    }
//
//    public List<Node<K,V>> inOrder() {
//        List<Node<K,V>> inList = new ArrayList<>();
//    }
//
//    public List<Node<K,V>> postOrder() {
//        List<Node<K,V>> postList = new ArrayList<>();
//    }

    public List<Node<K, V>> layerOrder() {
        List<Node<K, V>> layerList = new ArrayList<>();
        Queue<Node<K, V>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            for (int i = 0, n = queue.size(); i < n; i++) {
                Node<K, V> temp = queue.poll();
                layerList.add(temp);
                assert temp != null;
                Node<K, V> leftChild = temp.getLeftChild();
                Node<K, V> rightChild = temp.getRightChild();
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
        if (delNode == null) {
            throw new RuntimeException("Error: delNode is null!");
        } else {
            Node<K, V> currentParent = delNode;
            Node<K, V> current = currentParent.getRightChild();
            while (current.getLeftChild() != null) {
                currentParent = current;
                current = current.getLeftChild();
            }
            if (current != delNode.getRightChild()) {
                currentParent.setLeftChild(current.getRightChild());
                current.setRightChild(delNode.getRightChild());
            }
            return current;
        }
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
                    System.out.print(temp.getiData());
                    queue.offer(temp.getLeftChild());
                    queue.offer(temp.getRightChild());
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