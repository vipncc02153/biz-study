package com.everhomes.learning.demos.treeMap.lyl;

import java.util.*;

/**
 * Created by Long on 2018/7/7.
 */
public class test {
    public static void main(String[] args) {
        SimpleTreeMap<Integer,String> map = new SimpleTreeMap<>();
        for (int i=0;i<10;i++) {
            map.put(i,"value"+i);
        }
        System.out.println(map.get(2));
    }
}
