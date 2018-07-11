package com.everhomes.learning.demos.taskallocation.lyl;

import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by Long on 2018/7/7.
 */
public class AverageAssign extends AbstractAssign{
    @Override
    protected Map assigned(List serverList, List taskList) {
        Map<String,List<String>> map = new HashMap<>();
        if (!CollectionUtils.isEmpty(serverList) && !CollectionUtils.isEmpty(taskList)) {
            int serverSize = serverList.size();
            for(int i=0;i<taskList.size();i ++) {
                String task = (String) taskList.get(i);
                String server = (String)serverList.get(i%serverSize);
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
