package com.ustb.datastruct.ch8.tree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 〈Tree Class Test〉<br>
 * 〈〉
 *
 * @author re-get
 * @create 2020/4/22
 * @since 1.0.0
 */
public class TreeTest {
    public static void main(String[] args) {
        Tree<Integer, Double> theTree = new Tree<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        });

        theTree.insert(50, 1.5);
        theTree.insert(25, 1.2);
        theTree.insert(75, 1.7);
        theTree.insert(12, 1.5);
        theTree.insert(37, 1.2);
        theTree.insert(43, 1.7);
        theTree.insert(30, 1.5);
        theTree.insert(33, 1.1);
        theTree.insert(83, 1.5);
        theTree.insert(93, 1.9);
        theTree.insert(97, 1.3);

        theTree.displayTree();

        System.out.println("preOrder");
        List<Tree.Node<Integer, Double>> preList = theTree.preOrder();
        for(Tree.Node<Integer, Double> node : preList){
            System.out.print(node.toString());
        }
        System.out.println();

        System.out.println("inOrder");
        List<Tree.Node<Integer, Double>> inList = theTree.inOrder();
        for(Tree.Node<Integer, Double> node : inList){
            System.out.print(node.toString());
        }
        System.out.println();

        System.out.println("postOrder");
        List<Tree.Node<Integer, Double>> postList = theTree.postOrder();
        for(Tree.Node<Integer, Double> node : postList){
            System.out.print(node.toString());
        }
        System.out.println();

        System.out.println("layerOrder");
        List<Tree.Node<Integer, Double>> layerList = theTree.layerOrder();
        for(Tree.Node<Integer, Double> node : layerList){
            System.out.print(node.toString());
        }
        System.out.println();
    }
}