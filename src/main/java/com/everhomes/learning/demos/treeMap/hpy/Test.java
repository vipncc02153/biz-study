package com.everhomes.learning.demos.treeMap.hpy;

import java.util.Random;

public class Test {
    public static void main(String[] args) throws Exception {
        BiTreeNode biTree = new BiTreeNode();
        Random ran=new Random();
        int[] array = {84,15,95,71,45,154,84,71,35,42,02,98,44,35,66,15,81,48,99,34};

        for(int i = 0; i < 20; i++) {
            Long key = (long)array[i];
            //Long key = (long)ran.nextInt(10000);
            BiTreeNode biTreeNode = new BiTreeNode();
            biTreeNode.setKey(key);
            biTreeNode.setValue(key);

            BiTreeNode backup = biTree;
            System.out.println(key);
             if(i == 0){
                biTree = biTreeNode;
            }else{
                biTree = biTree.inNode(biTreeNode);
            }
            if(biTree == null){
                 biTree = backup;
                System.out.println("重复数据，重置biTree, " + biTree.getKey());
            }

        }

    }
}
