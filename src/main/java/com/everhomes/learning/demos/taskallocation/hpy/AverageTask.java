package com.everhomes.learning.demos.taskallocation.hpy;


import java.util.List;

public class AverageTask extends Strategy{

    public AverageTask(){
        strategyName = "AverageTask";
    }

    @Override
    public void appointTask(List<ServerDTO> servers, TaskDTO task){
        Integer minIndex = 0;
        Integer minSum = servers.get(0).getTaskSum();
        for(int i = 0; i < servers.size(); i++){
            if(minSum > servers.get(i).getTaskSum()){
                minIndex = i;
                minSum = servers.get(i).getTaskSum();
            }
        }
        doAddList(servers.get(minIndex),task);
    }
}
