package com.ld;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Author:li_d
 * Created:2019/5/18
 */
public class BinaryTree {
    static class Node{
        char value;
        Node left;
        Node right;
        Node(char v){
            this.value = v;
        }
    }

    //二叉树前序遍历
    private static void preOrderTraversal(Node root){
        if (root != null){
            System.out.print(root.value+" ");
            preOrderTraversal(root.left);
            preOrderTraversal(root.right);
        }
    }
    //二叉树前序非递归
    private static void preOrderNoR(Node root){
        Node cur = root;
        Stack<Node> stack = new Stack<Node>();
        while (!stack.empty() || cur != null){
            //优先走左边
            while (cur != null){
                //第一次遇到cur这个节点的位置
                System.out.print(cur.value + " ");
                stack.push(cur);
                cur = cur.left;
            }
            //走完左，从栈里取节点进行回溯
            Node top = stack.pop();
            cur = top.right;
        }
    }

    //二叉树中序遍历
    private static void inOrderTraversal(Node root) {
        if (root != null) {
            inOrderTraversal(root.left);
            System.out.print(root.value + " ");
            inOrderTraversal(root.right);
        }
    }

    //二叉树中序非递归
    private static void inOrderNoR(Node root){
        Node cur = root;
        Stack<Node> stack = new Stack<Node>();
        while (!stack.empty() || cur != null){
            //优先走左边
            while (cur != null){
                stack.push(cur);
                cur = cur.left;
            }
            //走完左，从栈里取节点进行回溯
            Node top = stack.pop();
            //top取出的节点，是第二次遇到该节点
            System.out.print(cur.value + " ");
            cur = top.right;
        }
    }

    //二叉树后序遍历
    private static void postOrderTraversal(Node root) {
        if (root != null) {
            postOrderTraversal(root.left);
            postOrderTraversal(root.right);
            System.out.print(root.value + " ");

        }
    }

    //二叉树后序非递归
    private static void postOrderNoR(Node root){
        Node cur = root;
        Node last = null;
        Stack<Node> stack = new Stack<Node>();
        while (!stack.empty() || cur != null){
            while (cur != null){
                stack.push(cur);
                cur = cur.left;
            }
            Node top = stack.peek();
            if (top.right == null){
                System.out.print(top.value+" ");
                stack.pop();
                last = top;
            }else if (top.right == last){
                System.out.print(top.value+" ");
                stack.pop();
                last = top;
            }else {
                cur = top.right;
            }
        }
    }

    //二叉树层序遍历---队列
    private static void levelOrderTraversal(Node root){
        if (root == null){
            return;
        }
        //使用链表当队列使用
        LinkedList<Node> queue = new LinkedList<Node>();
        //启动，将节点放入队列
        queue.addLast(root);
        //拉下线的整个过程
        while (!queue.isEmpty()){
            Node front = queue.pollFirst();
            System.out.print(front.value+" ");

            //拉下线，有要求，空的不要
            if (front.left != null){
                queue.addLast(front.left);
            }
            if (front.right != null){
                queue.addLast(front.right);
            }
        }
    }

    //求二叉树节点个数————方法一
    private  static int count = 0;
    private static int countByTraversal(Node root){
        if (root != null){
            countByTraversal(root.left);
            countByTraversal(root.right);
            count++;
        }
        return count;
    }

    //求二叉树节点个数————方法二
    private static int BTreeSize(Node root)
    {
        if (root == null)
            return 0;
        return 1 + BTreeSize(root.left) + BTreeSize(root.right);
    }

    //求二叉树叶子节点的个数
    private static int BTreeLeafSize(Node root)
    {
        if (root == null)
            return 0;
        if (root.left == null && root.right == null)
        {
            return 1;
        }
        return BTreeLeafSize(root.left) + BTreeLeafSize(root.right);
    }


    //求二叉树的高度
    private static int height(Node root){
        if (root == null){
            return 0;
        }else {
            int left = height(root.left);
            int right = height(root.right);
            return (left > right ? left : right) + 1;
        }
    }

    //求第k层节点个数
    private static int kLevel(Node root, int k){
        if (root == null){
            return 0;
        }else if (k == 1){//根节点
            return 1;
        }else {
            return kLevel(root.left,k - 1) + kLevel(root.right,k - 1);
        }
    }

    //查找二叉树是否有这个节点，有则返回
    private static Node find(Node root,char v){
        if (root == null){
            return null;
        }
        if (root.value == v){
            return root;
        }
        Node r = find(root.left,v);
        if (r != null){
            return r;
        }
        r = find(root.right,v);
        if (r != null){
            return r;
        }
        return null;
    }

    //给定一个二叉树，返回按层次遍历的节点值(???)
    private static List<List<Character>> levelOrder2(Node root){
        //返回值是一个二维的list
        List<List<Character>> list = new ArrayList<List<Character>>();
        if (root == null){
            return list;
        }

        class NodeLevel{
            Node node;
            int level;

            public NodeLevel(Node node,int level){
                this.node = node;
                this.level = level;
            }
        }
        //定义队列
        LinkedList<NodeLevel> queue = new LinkedList<NodeLevel>();
        queue.addLast(new NodeLevel(root,0));
        while (!queue.isEmpty()){
            NodeLevel front = queue.pollFirst();
            Node node = front.node;
            int level = front.level;
            //忽略中间遍历,处理返回值
            if (list.size() == level){
                list.add(new ArrayList<Character>());//插容器，即括号
            }
            list.get(level).add(node.value);

            if (node.left != null){
                queue.add(new NodeLevel(node.left,level + 1));
            }
            if (node.right != null){
                queue.add(new NodeLevel(node.right,level + 1));
            }
        }
        return list;
    }

    //判断二叉树是否是完全二叉树--带着空节点层序遍历，遇到空之后的所有都是空的是完全二叉树
    private static boolean isComplete(BinaryTree.Node root){
        if (root == null){
            return true;
        }
        LinkedList<BinaryTree.Node> queue = new LinkedList<BinaryTree.Node>();
        queue.addLast(root);
        while (true){
            BinaryTree.Node front = queue.pollFirst();
            if (front == null){
                //判断剩余节点是否全是空
                break;
            }
            queue.addLast(front.left);
            queue.addLast(front.right);
        }
        //判断所有的节点都是非空
        while (!queue.isEmpty()){
            BinaryTree.Node front = queue.pollFirst();
            if (front != null){
                return false;
            }
        }
        return true;
    }

        private static Node createTestTree(){
        Node a = new Node('A');
        Node b = new Node('B');
        Node c = new Node('C');
        Node d = new Node('D');
        Node e = new Node('E');
        Node f = new Node('F');
        Node g = new Node('G');
        Node h = new Node('H');
        a.left = b;a.right = c;
        b.left = d;b.right = e;
        c.left = f;c.right = g;
        e.right = h;
        return a;
    }

    public static void main(String[] args) {
        Node root = createTestTree();
        preOrderTraversal(root);
        System.out.println();
        preOrderNoR(root);
        System.out.println();
        inOrderTraversal(root);
        System.out.println();
//        inOrderNoR(root);
//        System.out.println();
        postOrderTraversal(root);
        System.out.println();
        postOrderNoR(root);
        System.out.println();
        levelOrderTraversal(root);
        System.out.println();
        System.out.println("二叉树节点个数：" + countByTraversal(root));
        System.out.println("二叉树节点个数：" + BTreeSize(root));
        System.out.println("二叉树叶子节点的个数：" + BTreeLeafSize(root));
        System.out.println("二叉树的高度：" + height(root));
        System.out.println("第三层叶子节点的个数："+kLevel(root,3));
        System.out.println(find(root,'F'));
        System.out.println(find(root,'O'));

        List<List<Character>> list = levelOrder2(root);
        System.out.println("{");
        for (List<Character> innerList : list){
            System.out.print("{");
            for (char value : innerList){
                System.out.print(value + " ");
            }
            System.out.println("}");
        }
        System.out.println("}");

        if (isComplete(root)){
            System.out.println("是完全二叉树");
        }else {
            System.out.println("不是完全二叉树");
        }
    }
}
