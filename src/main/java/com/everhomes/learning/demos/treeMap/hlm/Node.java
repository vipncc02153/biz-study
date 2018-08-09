package com.everhomes.learning.demos.treeMap.hlm;

/**
 * 节点：
 * value 存值
 * left 左子节点
 * right 右子节点
 * father 父节点（父节点为空，那表明该节点是根节点）
 * color 颜色，
 */
public class Node {

    private Object key ;
    private Object value ;
    private boolean color ;
    private Node left ;
    private Node right ;
    private Node parent ;

    public static final boolean BLANK = false ;
    public static final boolean RED = true ;
    public static final boolean LEFT = false ;
    public static final boolean RIGHT = true ;

    public Node() { }

    public Node(Object key, Object value) {
        this.key = key;
        this.value = value;
        this.color = BLANK ;
    }

    public Node(Object key, boolean color, Node left, Node right, Node parent) {
        this.key = key;
        this.color = color;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Node(Object key) {
        this.key = key;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public boolean getColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
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

    @Override
    public String toString(){
        return "k:"+ this.key+";v:"+this.value;
    }
}
