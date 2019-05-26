package com.ld;

import javax.swing.tree.TreeNode;
import java.util.Arrays;

/**
 * Author:li_d
 * Created:2019/5/23
 */

class CreateTreeReturnValue{
    //创建好的二叉树的根节点
    public BinaryTree1.Node returnRoot;
    //使用的个数
    public int used;

    CreateTreeReturnValue(BinaryTree1.Node returnRoot,int used){
        this.returnRoot = returnRoot;
        this.used = used;
    }

}

public class BinaryTree1 {
    public static class Node{
        public char value;
        public Node left;
        public Node right;

        Node(char value){
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    //利用带空节点的前序创建二叉树
    private static CreateTreeReturnValue createTree(char[] preOrder){
        //1.根节点
        if (preOrder.length == 0){
            return new CreateTreeReturnValue(null,0);
        }
        char rootValue = preOrder[0];
        if (rootValue == '#'){
            return new CreateTreeReturnValue(null,1);
        }

        Node root = new Node(rootValue);

        //2.创建左子树，利用递归
        char[] leftPreOrder = new char[preOrder.length - 1];
        leftPreOrder = Arrays.copyOfRange(preOrder,1,preOrder.length);
        CreateTreeReturnValue leftReturn = createTree(leftPreOrder);

        //3.创建右子树,需要告知右子树的长度
        char[] rightPreOrder = new char[preOrder.length - 1 - leftReturn.used];
        rightPreOrder = Arrays.copyOfRange(preOrder,1 + leftReturn.used,preOrder.length);
        //创建好右子树
        CreateTreeReturnValue rightReturn = createTree(rightPreOrder);
        //绑定左右子树和根
        root.left =  leftReturn.returnRoot;
        root.right = rightReturn.returnRoot;

        return new CreateTreeReturnValue(root,1 + leftReturn.used + rightReturn.used);
    }

    private static BinaryTree1.Node find(BinaryTree1.Node root, char v){
        if (root == null){
            return null;
        }
        if (root.value == v){
            return root;
        }
        BinaryTree1.Node r = find(root.left,v);
        if (r != null){
            return r;
        }
        r = find(root.right,v);
        if (r != null){
            return r;
        }
        return null;
    }

    private static int find2(char[] array,char v){
        for (int i = 0;i < array.length;i++){
            if (array[i] == v){
                return i;
            }
        }
        return -1;
    }

    //前序与中序创建二叉树
    private static Node buildTree(char[] preorder,char[] inorder){
        if (preorder.length == 0){
            return null;
        }
        //1.根据前序，找到根的值，并且创建根节点
        char rootValue = preorder[0];
        Node root = new Node(rootValue);
        //2.在中序中找到根的值所在的下标1
        int leftSize = find2(inorder,rootValue);
        //3.切出左子树的前序和中序
        char[] leftPreorder = Arrays.copyOfRange(preorder,1,1 + leftSize);
        char[] leftInorder = Arrays.copyOfRange(inorder,0,leftSize);
        root.left = buildTree(leftPreorder,leftInorder);
        //4.切出右子树的前序和中序
        char[] rightPreorder = Arrays.copyOfRange(preorder,1 + leftSize,preorder.length);
        char[] rightInorder = Arrays.copyOfRange(inorder,leftSize + 1, preorder.length);
        root.right = buildTree(rightPreorder,rightInorder);
        return root;
    }

    //中序与后续创建二叉树
    private static Node buildTree1(char[] inorder,char[] postorder){
        if (inorder.length == 0){
            return null;
        }
        char rootValue = postorder[postorder.length-1];
        Node root = new Node(rootValue);
        int leftSize = find2(inorder,rootValue);

        char[] leftInorder = Arrays.copyOfRange(inorder,0,leftSize);
        char[] leftPostorder = Arrays.copyOfRange(postorder,0,leftSize);
        root.left = buildTree1(leftInorder,leftPostorder);

        char[] rightInorder = Arrays.copyOfRange(inorder,leftSize + 1,inorder.length);
        char[] rightPosrorder = Arrays.copyOfRange(postorder,leftSize,postorder.length - 1);
        root.right = buildTree1(rightInorder,rightPosrorder);

        return root;
    }

    public static void main(String[] args) {
        char[] preOrder = new char[]{'A','B','C','#','D','#','#','#','E'};
        CreateTreeReturnValue returnValue = createTree(preOrder);
        System.out.println(returnValue.used);
        Node n = find(returnValue.returnRoot,'E');

        char[] preorder = new char[]{'A','B','C','D','E'};
        char[] inorder = new char[]{'C','D','B','A','E'};
        char[] postorder = new char[]{'D','C','B','E','A'};
        //Node root = buildTree(preorder,inorder);
        Node root1 = buildTree1(inorder,postorder);
        find(root1,'E');
    }
}
