package com.ustb.datastruct.ch8.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This is a class about the basic node of tree
 *
 * @author re-get
 * @create 2020/4/22
 * @since 1.0.0
 */
class Node {
    private int iData;
    private double dData;
    private Node leftChild;
    private Node rightChild;

    public Node(int iData, double dData) {
        this.iData = iData;
        this.dData = dData;
        this.leftChild = null;
        this.rightChild = null;
    }

    public int getiData() {
        return iData;
    }

    public void setiData(int iData) {
        this.iData = iData;
    }

    public double getdData() {
        return dData;
    }

    public void setdData(double dData) {
        this.dData = dData;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public void displayNode() {
        System.out.println('{' + iData + ", " + dData + "} ");
    }
}

/**
 * This is a class about Tree DataStruct
 *
 * @author re-get
 * @create 2020/4/22
 * @since 1.0.0
 */
public class Tree {
    private Node root;

    public Tree() {
        root = null;
    }

    /**
     * This function to find a node which iData is key
     *
     * @param key
     * @return
     */
    public Node find(int key) {
        Node current = root;
        while (current != null) {
            if (current.getiData() == key) {
                return current;
            } else if (current.getiData() > key) {
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
     *
     * @param id
     * @param dd
     * @return is insert node
     */
    public void insert(int id, double dd) {
        Node iNode = new Node(id, dd);
        if (root == null) {
            root = iNode;
        } else {
            Node current = root;
            while (true) {
                if (current.getiData() == id) {
                    current.setdData(dd);
                    return;
                } else if (current.getiData() > id) {
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
     * @param key
     * @return is the node delete
     */
    public boolean delete(int key) {
        if (root == null) {
            return false;
        } else {
            Node current = root;
            Node currentParent = null;
            Boolean isLeftChild = true;
            while (true) {
                if (current == null) {
                    return false;
                } else if (current.getiData() == key) {
                    break;
                } else if (current.getiData() > key) {
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
                Node successor = getSuccessor(current);
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


    private Node getSuccessor(Node delNode) {
        if (delNode == null) {
            throw new RuntimeException("Error: delNode is null!");
        } else {
            Node currentParent = delNode;
            Node current = currentParent.getRightChild();
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
        final int MAX_BLANKS = 32;
        int nBlanks = MAX_BLANKS;
        boolean isRowEmpty = false;
        Queue<Node> queue = new LinkedList();
        queue.offer(root);
        for (int i = 0; i < MAX_BLANKS * 2 + 2; i++) {
            System.out.print('*');
        }
        System.out.println();
        while (isRowEmpty == false) {
            isRowEmpty = true;
            for (int j = 0; j < nBlanks; j++) {
                System.out.print(' ');
            }
            for (int i = 0, n = queue.size(); i < n; i++) {
                Node temp = queue.poll();
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
                for (int j = 0; j < nBlanks * 2 - 2; j++) {
                    System.out.print(' ');
                }
            }
            System.out.println();
            nBlanks /= 2;
            for (int i = 0; i < MAX_BLANKS * 2 + 2; i++) {
                System.out.print('-');
            }
            System.out.println();
        }
    }
}