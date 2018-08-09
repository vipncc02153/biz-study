package com.everhomes.learning.demos.treeMap.hlm;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class TestTreeMap {

    public static void main(String[] args){

       // final  int[] list = {8,6,5,7,10,15,9,12,2,1};
        //final int[] list = {12,45,5,16,10,1,9,8,20,14,88,46,123,123};
        final int[] list = {1,2,3,4,5,6,7,8,9,10,11,12,13};
       // final int[] list = {13,12,11,10,9,8,7,6,5,4,3,2,1};


        MyTreeMap mtm = new MyTreeMap();
        for(int i :list){
            mtm.put(i,"小明"+i);
        }

        List<Object> i = mtm.listNode();
        for(Object o : i){
            System.out.println(o);
        }
        System.out.println(mtm.get(5));
        System.out.println(mtm.get(8));
        System.out.println(mtm.getSize());
        System.out.println(list.length);


    }

    class testNode {
        public  int key ;
        public  String value ;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}

