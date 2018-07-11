package com.everhomes.learning.demos.taskallocation.lyl;

import java.util.*;

/**
 * Created by Long on 2018/7/7.
 */
public class test {
    public static void main(String[] args) {
        List<String> serverList = new ArrayList<>();
        serverList.add("server1");
        serverList.add("server2");
        serverList.add("server3");
        serverList.add("server4");
        List<String> taskList = new ArrayList<>();
        taskList.add("task1");
        taskList.add("task2");
        taskList.add("task3");
        taskList.add("task4");
        taskList.add("task5");
        taskList.add("task6");
        taskList.add("task7");
        taskList.add("task8");
        taskList.add("task9");
        taskList.add("task10");
        AbstractAssign abstractAssign = new RandomAssign();
        Map<String,List<String>> map = abstractAssign.assigned(serverList,taskList);
        Set<String> key = map.keySet();
        Iterator keyIte = key.iterator();
        while (keyIte.hasNext()) {
            String server = (String) keyIte.next();
            List<String> tasks = map.get(server);
            for (String task : tasks) {
                System.out.println(server+":"+task);
            }
        }
    }
}
