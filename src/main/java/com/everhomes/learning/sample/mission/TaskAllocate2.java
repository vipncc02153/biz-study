package com.everhomes.learning.sample.mission;

import java.util.List;

public class TaskAllocate2 extends TaskAllocate {

    public TaskAllocate2(){
        super();
    }

    public String alloc(List<Long> machines, List<Long> tasks){
        if (machines == null || machines.size() == 0)
            return "";
        if (tasks == null || tasks.size() == 0)
            return "";
        StringBuilder builder = new StringBuilder();
        String sperate = "";
        for (int i = tasks.size()-1;i>=0;i--){
            builder.append(sperate);
            builder.append(machines.get(i % machines.size()));
            builder.append("-").append(tasks.get(i));
            sperate = " ";
        }
        return builder.toString();
    }
}
