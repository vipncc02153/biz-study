package com.everhomes.learning.demos.treeMap.zhouxm;

/**
 * @author feiyue
 * @className Node
 * @description
 * @date 2018/8/7
 **/
public class Node {

    private int key;
    private Object value;
    private Node left;
    private Node right;
    private Node parent;

    public Node(int key, Object value){
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "key=" + key +
                ", value=" + value +
                ", 【left=" + left +
                "】, 【right=" + right +
                "】}";
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}

