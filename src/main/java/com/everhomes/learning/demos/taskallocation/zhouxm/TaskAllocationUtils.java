package com.everhomes.learning.demos.taskallocation.zhouxm;


import java.util.*;

/**
 * @ClassName TaskAllocationUtils
 * @Description 服务器任务分配的工具类
 * @Author feiyue
 * @Date 2018/7/2
 **/
public class TaskAllocationUtils {

    /**
    * @Author feiyue
    * @Description  服务器任务任意分配
    * @Date  2018/7/2
    * @Param [serverList, taskList]
    * @return void
    **/
    public static void taskRandomAllocation(List<String> serverList, List<Integer> taskList){

        /** 定义一个 map 存放服务器和任务的对应关系*/
        Map<String, String> serverTaskMap = new HashMap<>();
        int serverCount = serverList.size();
        Random random = new Random();
        for(Integer index : taskList){
            int serverIndex = random.nextInt(serverCount);
            String oldValue = serverTaskMap.get(serverList.get(serverIndex));
            if(oldValue!=null)
                serverTaskMap.put(serverList.get(serverIndex), oldValue + " " + index);
            else
                serverTaskMap.put(serverList.get(serverIndex), "" + index);
        }

        /** 查看任务分配的结果*/
        for(Map.Entry<String, String> entrySet :serverTaskMap.entrySet()){
            System.out.println("服务器IP: " + entrySet.getKey() + " -- 任务ID: " + entrySet.getValue());
        }
    }

    /**
    * @Author feiyue
    * @Description 服务器任务均分
    * @Date  2018/7/2
    * @Param [serverList, taskList]
    * @return void
    **/
    public static void taskAverageAllocation(List<String> serverList, List<Integer> taskList){

        /** 定义一个 map 存放服务器和任务的对应关系*/
        Map<String, String> serverTaskMap = new HashMap<>();
        int serverCount = serverList.size();
        int taskCount = taskList.size();

        /** 针对任务的索引按照服务器数量取模，将结果映射到对应服务器的索引*/
        for(int index = 0; index <taskCount; index++){
            int resultIndex = index % serverCount;
            String oldValue = serverTaskMap.get(serverList.get(resultIndex));
            if(oldValue!=null)
                serverTaskMap.put(serverList.get(resultIndex),oldValue + taskList.get(index)+" ");
            else
                serverTaskMap.put(serverList.get(resultIndex),taskList.get(index)+" ");
        }

        /** 查看任务分配的结果*/
        for(Map.Entry<String, String> entrySet :serverTaskMap.entrySet()){
            System.out.println("服务器IP: " + entrySet.getKey() + " -- 任务ID: " + entrySet.getValue());
        }
    }
}
