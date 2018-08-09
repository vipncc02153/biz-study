package com.everhomes.learning.sample.bst;

import java.util.Comparator;
import java.util.TreeMap;

public class BinarySortTree<K,V> {
    private BSTNode<K,V> root;
    private final Comparator<? super K> comparator;
    private int size = 0;

    public BinarySortTree() {
        comparator = null;
    }

    public BinarySortTree(Comparator<? super K> comparator) {
        this.comparator = comparator;
    }

    public int size() {
        return size;
    }

    public void put(K key, V value){
        BSTNode<K,V> t = root;
        if (t == null){
            root = new BSTNode<>(key,value,null);

            size ++;
            return;
        }
        Comparator<? super K> cpr = comparator;
        BSTNode<K,V> parent;
        int cmp;
        if (cpr != null){
            do {
                parent = t;
                cmp = cpr.compare(key, t.getKey());
                if (cmp < 0)
                    t = t.getLeftChild();
                else if (cmp > 0)
                    t = t.getRightChild();
                else {
                    t.setValue(value);
                    return;
                }
            }while (t != null);
        }else {
            if (key == null)
                throw new NullPointerException();
            Comparable<? super K> k = (Comparable<? super K>) key;
            do {
                parent = t;
                cmp = k.compareTo(t.getKey());
                if (cmp < 0)
                    t = t.getLeftChild();
                else if (cmp > 0)
                    t = t.getRightChild();
                else {
                    t.setValue(value);
                    return;
                }
            }while (t != null);
        }

        BSTNode<K,V> e = new BSTNode<>(key,value,parent);
        if (cmp < 0)
            parent.setLeftChild(e);
        else
            parent.setRightChild(e);
        size++;

    }

    public V get(Object key){
        BSTNode<K,V> p = getNodeByKey(key);
        return (p==null ? null : p.getValue());
    }

    public void remove(Object key){
        BSTNode<K,V> p = getNodeByKey(key);
        if (p == null)
            return;
        deleteNode(p);
    }

    private void deleteNode(BSTNode<K,V> p){
        size --;
        if (p.getLeftChild() != null && p.getRightChild() != null) {
            BSTNode<K,V> s = successor(p);
            p.setKey(s.getKey());
            p.setValue(s.getValue());
            p = s;
        }
        BSTNode<K,V> replacement = p.getLeftChild() != null ? p.getLeftChild() : p.getRightChild();

        if (replacement != null){
            replacement.setParent(p.getParent());
            if (p.getParent() == null)
                root = replacement;
            else if (p == p.getParent().getLeftChild())
                p.getParent().setLeftChild(replacement);
            else
                p.getParent().setRightChild(replacement);

            p.setRightChild(null);
            p.setLeftChild(null);
            p.setParent(null);

        }else if (p.getParent() == null)
            root = null;
        else {
            if (p == p.getParent().getLeftChild())
                p.getParent().setLeftChild(null);
            else if (p == p.getParent().getRightChild())
                p.getParent().setRightChild(null);
            p.setParent(null);
        }

    }

    private BSTNode<K,V> successor(BSTNode<K,V> p){
        if (p == null)
            return null;
        else if (p.getRightChild() != null){
            BSTNode<K,V> t = p.getRightChild();
            while (t.getLeftChild() != null)
                t = t.getLeftChild();
            return t;
        }
        return null;
    }

    private BSTNode<K,V> getNodeByKey(Object key){
        if (comparator != null)
            getNodeUsingComparator(key);
        if (key == null)
            throw new NullPointerException();
        Comparable<? super K> k = (Comparable<? super K>) key;
        BSTNode<K,V> p = root;
        while (p != null) {
            int cmp = k.compareTo(p.getKey());
            if (cmp < 0)
                p = p.getLeftChild();
            else if (cmp > 0)
                p = p.getRightChild();
            else
                return p;
        }
        return null;
    }

    final BSTNode<K,V> getNodeUsingComparator(Object key){
        K k = (K) key;
        Comparator<? super K> cpr = comparator;
        if (cpr != null) {
            BSTNode<K,V> p = root;
            while (p != null){
                int cmp = cpr.compare(k, p.getKey());
                if (cmp < 0)
                    p = p.getLeftChild();
                else if (cmp > 0)
                    p = p.getRightChild();
                else
                    return p;
            }
        }
        return null;
    }

}
