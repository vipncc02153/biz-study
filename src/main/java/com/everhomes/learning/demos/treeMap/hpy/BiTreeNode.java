package com.everhomes.learning.demos.treeMap.hpy;

public class BiTreeNode {

    private BiTreeNode lChild;
    private BiTreeNode rChild;
    private Long key;
    private Object value;
    private Integer high;


    public BiTreeNode inNode(BiTreeNode node) throws Exception{
        System.out.println("开始插入,当前节点key为：" + this.key + ", 插入节点key为：" + node.key);

        BiTreeNode root;
        if(node.getKey() > this.key){
            if(this.rChild != null){
                rChild = rChild.inNode(node);
                if(rChild == null)
                    return null;
            }else{
                rChild = node;
                if(lChild != null) {
                    high = rChild.high >= lChild.high ? rChild.high + 1 : lChild.high + 1;
                }
                else{
                    high = 1;
                }
                return this;
            }

        }else if(node.getKey() < this.key){
            if(this.lChild != null){
                lChild = lChild.inNode(node);
                if(lChild == null)
                    return null;
            }else{
                lChild = node;
                if(rChild != null) {
                    high = rChild.high >= lChild.high ? rChild.high + 1 : lChild.high + 1;
                }
                else{
                    high = 1;
                }
                return this;
            }
        }else{
            return null;
        }
        if(lChild != null && rChild != null) {
            high = rChild.high >= lChild.high ? rChild.high + 1 : lChild.high + 1;
        }else if(lChild != null && rChild == null){
            high = lChild.high + 1;
        }else if(lChild == null && rChild != null){
            high = rChild.high + 1;
        }else{
            high = 0;
        }
        if(rChild != null && lChild != null) {
            if (lChild.high - rChild.high == -2) {
                root = lRotate();
                return root;
            } else if (lChild.high - rChild.high == -2) {
                root = rRotate();
                return root;
            } else {
                return this;
            }
        }else{
            if(lChild != null && lChild.high == 1){
                root = rRotate();
                return root;
            }
            if(rChild != null && rChild.high == 1){
                root = lRotate();
                return root;
            }
            return this;
        }
    }

    private BiTreeNode lRotate(){
        System.out.println("开始左旋转,当前节点key为：" + this.key);

        Integer balanceGene;
        if(this.lChild != null) {
            balanceGene = this.lChild.high - this.rChild.high;
        }else{
            balanceGene = rChild.high + 1;
        }
        Integer rChildBalanceGene;
        if(this.rChild.rChild == null){
            rChildBalanceGene = 1;
        }else if(this.rChild.lChild == null){
            rChildBalanceGene = -1;
        }else {
            rChildBalanceGene = this.rChild.lChild.high - this.rChild.rChild.high;
        }
        if(balanceGene < 0 && rChildBalanceGene > 0){
            BiTreeNode temp = this.rChild.lChild;
            if(temp==null || this.rChild == null){
                System.out.println("null");
            }
            temp.rChild = this.rChild;
            temp.rChild.lChild = null;
            this.rChild = temp;

        }

        BiTreeNode temp = this.rChild;
        this.rChild = null;
        temp.lChild = this;


        return temp;

    }

    private BiTreeNode rRotate(){
        System.out.println("开始右旋转,当前节点key为：" + this.key );
        Integer balanceGene;
        if(this.rChild != null) {
            balanceGene = this.lChild.high - this.rChild.high;
        }else{
            balanceGene = lChild.high + 1;
        }
        Integer lChildBalanceGene;
        if(this.lChild.rChild == null){
            lChildBalanceGene = 1;
        }else if(this.lChild.lChild == null){
            lChildBalanceGene = -1;
        }else {
            lChildBalanceGene = this.lChild.lChild.high - this.lChild.rChild.high;
        }
        if(balanceGene > 0 && lChildBalanceGene < 0){
            BiTreeNode temp = this.lChild.rChild;
            temp.lChild = this.lChild;
            temp.lChild.rChild = null;
            this.lChild = temp;
        }

        BiTreeNode temp = this.lChild;
        this.lChild = null;
        temp.rChild = this;
        return temp;

    }


    public BiTreeNode(){
        key = 0l;
        high = 0;
    }


    public BiTreeNode getlChild() {
        return lChild;
    }

    public void setlChild(BiTreeNode lChild) {
        this.lChild = lChild;
    }

    public BiTreeNode getrChild() {
        return rChild;
    }

    public void setrChild(BiTreeNode rChild) {
        this.rChild = rChild;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Integer getHigh() {
        return high;
    }

    public void setHigh(Integer high) {
        this.high = high;
    }
}
