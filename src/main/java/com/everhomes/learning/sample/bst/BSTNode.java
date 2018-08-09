package com.everhomes.learning.sample.bst;

public class BSTNode<K,V> {
    private K key;
    private V value;
    private BSTNode<K,V> leftChild;
    private BSTNode<K,V> rightChild;
    private BSTNode<K,V> parent;

    public BSTNode(K key,V value,BSTNode<K,V> parent){
        this.key = key;
        this.value = value;
        this.parent = parent;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BSTNode))
            return false;
        BSTNode<?,?> e = (BSTNode<?,?>) obj;
        return valEquals(e.getKey(),key) && valEquals(e.getValue(),value);
    }

    static final boolean valEquals(Object o1, Object o2) {
        return (o1==null ? o2==null : o1.equals(o2));
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public BSTNode<K, V> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(BSTNode<K, V> leftChild) {
        this.leftChild = leftChild;
    }

    public BSTNode<K, V> getRightChild() {
        return rightChild;
    }

    public void setRightChild(BSTNode<K, V> rightChild) {
        this.rightChild = rightChild;
    }

    public BSTNode<K, V> getParent() {
        return parent;
    }

    public void setParent(BSTNode<K, V> parent) {
        this.parent = parent;
    }
}
