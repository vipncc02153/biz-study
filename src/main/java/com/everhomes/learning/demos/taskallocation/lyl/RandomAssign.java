package com.everhomes.learning.demos.taskallocation.lyl;

import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by Long on 2018/7/7.
 */
public class RandomAssign extends AbstractAssign{
    @Override
    protected Map assigned(List serverList, List taskList) {
        Map<String,List<String>> map = new HashMap<>();
        if (!CollectionUtils.isEmpty(serverList) && !CollectionUtils.isEmpty(taskList)) {
            Iterator taskIterator = taskList.iterator();
            while (taskIterator.hasNext()) {
                String task = (String) taskIterator.next();
                Random random = new Random();
                String server = (String) serverList.get(random.nextInt(serverList.size()));
                if (map.containsKey(server)) {
                    map.get(server).add(task);
                }else {
                    List<String> list = new ArrayList<>();
                    list.add(task);
                    map.put(server,list);
                }
            }
        }
        return map;
    }
}
