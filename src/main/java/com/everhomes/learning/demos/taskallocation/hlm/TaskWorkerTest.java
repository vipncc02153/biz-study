package com.everhomes.learning.demos.taskallocation.hlm;

import java.util.ArrayList;
import java.util.List;

public class TaskWorkerTest {


    public static void main(String[] args){


        TaskWorker tk = new AverageTaskWorker();
        TaskWorker rtk = new RandomTaskWorker();
        List<Long> taskList = getTask(31);
        List<String> serviceList = getService(15 ) ;

        tk.buildTask(taskList).buildService(serviceList) ;
        tk.processed();
        System.out.println("<---------我是华丽的分割线------------->");
        rtk.buildTask(taskList).buildService(serviceList) ;
        rtk.processed();

    }



   public static List<Long> getTask(int taskSize){
       if(taskSize <1){
           return null ;
       }
       List<Long> taskList = new ArrayList<>();
       for(int i=0 ; i <taskSize ;i++ ){
           taskList.add(new Long(i)) ;

       }
       return taskList ;
   }

    public static List<String> getService(int ServiceSize){
       if(ServiceSize <1){
           return null ;
       }
        List<String> serviceList = new ArrayList<>();
        for(int i=0 ; i <ServiceSize ;i++ ){
            serviceList.add("服务器00"+ i) ;

        }
        return serviceList ;
    }
}
