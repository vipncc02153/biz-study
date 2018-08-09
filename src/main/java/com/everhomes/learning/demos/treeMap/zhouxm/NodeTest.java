package com.everhomes.learning.demos.treeMap.zhouxm;

/**
 * @author feiyue
 * @className NodeTest
 * @description
 * @date 2018/8/7
 **/
public class NodeTest {

    public static void main(String[] args){
        Node node0 = new Node(0, "00");
        Node node1 = new Node(1, "11");
        Node node2 = new Node(2, "22");
        Node node3 = new Node(3, "33");
        Node node4 = new Node(4, "44");
        Node node5 = new Node(5, "55");
        MyMap map = new MyMap(node2);
        map.put(node0);
        map.put(node1);
        map.put(node4);
        map.put(node5);
        System.out.println(map.get(2));

        map.put(node3);
        System.out.println(map.get(2));

        map.remove(0);
        System.out.println(map.get(2));
    }
}
