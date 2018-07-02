package com.everhomes.learning.demos.beantojson.zhouxm.taskallocation;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UtilsTest
 * @Description  服务器任务分配工具类的测试
 * @Author feiyue
 * @Date 2018/7/2
 **/
public class UtilsTest {

    public static void main(String[] args){
        List<String> serverList = new ArrayList<>();
        List<Integer> taskList = new ArrayList<>();
        serverList.add("10.1.110.10");
        serverList.add("10.1.110.20");

        taskList.add(7);
        taskList.add(9);
        taskList.add(3);
        taskList.add(6);

        TaskAllocationUtils.taskRandomAllocation(serverList, taskList);
        TaskAllocationUtils.taskAverageAllocation(serverList, taskList);
    }
}
