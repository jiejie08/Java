package com.ld;

import javafx.util.Pair;

import java.util.Arrays;

/**
 * 使用Pair可以把多个返回值打包到一起，就不需要创建多个类
 * Author:li_d
 * Created:2019/5/23
 */
public class BinaryTree2 {
    static class Node{
        char value;
        Node left;
        Node right;

        public Node(char value){
            this.value = value;
            this.left = this.right = null;;
        }
    }

    private static Pair<Node,Integer> createTree(char[] preOrder){
        if (preOrder.length == 0){
            return new Pair<Node, Integer>(null,0);
        }
        if (preOrder[0] == '#'){
            return new Pair<Node, Integer>(null,1);
        }
        Node root = new Node(preOrder[0]);
        Pair<Node, Integer> left = createTree(Arrays.copyOfRange(preOrder,1,preOrder.length));
        Pair<Node, Integer> right = createTree(Arrays.copyOfRange(preOrder,1 + left.getValue(),preOrder.length));

        root.left = left.getKey();
        root.right = right.getKey();
        return new Pair<Node, Integer>(root,1 + left.getValue() + right.getValue());
    }

    private static void preOrderTraversal(Node root){
        if (root != null){
            System.out.print(root.value);
            preOrderTraversal(root.left);
            preOrderTraversal(root.right);
        }
    }

    private static void inOrderTraversal(Node root){
        if (root != null){
            inOrderTraversal(root.left);
            System.out.print(root.value);
            inOrderTraversal(root.right);
        }
    }

    private static void preOrderTree2Str(Node root, StringBuffer sb){
        if (root != null){
            sb.append('(');
            sb.append(root.value);
            if (root.left == null && root.right != null){
                sb.append("()");
            }else {
                preOrderTree2Str(root.left,sb);
            }
            preOrderTree2Str(root.right,sb);
            sb.append(')');
        }
    }
    //根据二叉树创建字符串
    private static String tree2str(Node root){
        if (root == null){
            return "";
        }
        StringBuffer sb = new StringBuffer();
        preOrderTree2Str(root,sb);
        sb.delete(0,1);
        sb.delete(sb.length() - 1,sb.length());

        return sb.toString();
    }

    //查找节点是否存在
    private static boolean find(Node root,Node t){
        if (root == null){
            return false;
        }
        if (root == t){
            return true;
        }
        if (find(root.left,t)){
            return true;
        }
        return find(root.right,t);
    }
    //寻找最近的公共祖先
    private static Node lowestCommonAncestor(Node root,Node p,Node q){
        if (root == p || root == q){
            return root;
        }
        boolean pInleft = find(root.left,p);
        boolean qInLeft = find(root.left,q);
        if (pInleft && qInLeft){
            return lowestCommonAncestor(root.left,p,q);
        }
        if (!pInleft && !qInLeft){
            return lowestCommonAncestor(root.right,p,q);
        }
        return root;
    }

    private static Node prev = null;
    private static Node head = null;
    private static void buildDList(Node node) {
        node.left = prev;   //node.prev = prev
        if (prev != null){
            prev.right = node;  //prev.next = node;
        }else {
            head = node;
        }
        prev = node;
    }
    //搜索二叉树，把二叉树串成一个双向链表
    private static void inOrderTraversalSearchTree(Node root){
        if (root != null){
            inOrderTraversalSearchTree(root.left);
            buildDList(root);
            inOrderTraversalSearchTree(root.right);
        }
    }
    private static Node searchTreeToSortedList(Node root){
        prev = null;
        head = null;
        inOrderTraversalSearchTree(root);
        return head;
    }

    private static Node buildSearchTree(){
        Node a = new Node('A');
        Node b = new Node('B');
        Node c = new Node('C');
        Node d = new Node('D');
        Node e = new Node('E');
        Node f = new Node('F');
        Node g = new Node('G');
        Node h = new Node('H');

        e.left = b;e.right = g;
        b.left = a;b.right = d;
        d.left = c;
        g.left = f;g.right = h;
        return e;
    }

    public static void main(String[] args) {
        char[] preOrder = new char[]{'A','B','D','#','#','E','#','#','C','#','F'};
        Pair<Node,Integer> pair = createTree(preOrder);
        Node root = pair.getKey();
        preOrderTraversal(root);
        System.out.println();
        inOrderTraversal(root);
        System.out.println();

        String str = tree2str(root);
        System.out.println(str);

        //System.out.println(lowestCommonAncestor(root,'E','C'));//测试方法

        Node searchTreeRoot = buildSearchTree();
        Node head = searchTreeToSortedList(searchTreeRoot);
        for (Node cur = head;cur != null;cur = cur.right){
            System.out.print(cur.value+" ");
        }
    }

}
