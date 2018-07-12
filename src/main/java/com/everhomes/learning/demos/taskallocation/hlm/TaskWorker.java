package com.everhomes.learning.demos.taskallocation.hlm;


import java.util.*;

public abstract class TaskWorker {

    public List<String> serviceList ;
    public List<Long> taskList ;
    public Map<Long , String > taskMap ;

    public TaskWorker(){

    }
    public TaskWorker(List<String> serviceList ,List<Long> taskList){

        this.serviceList = serviceList ;
        this.taskList = taskList ;
        this.taskMap = new HashMap<>() ;
    }

    public TaskWorker buildService(List<String> serviceList){

        if(this.serviceList == null){
            this.serviceList = new ArrayList<>() ;
            this.serviceList.addAll(serviceList);
        }else{
            this.serviceList.addAll(serviceList);
        }
        return this ;
    }

    public TaskWorker buildTask(List<Long> taskList){

        if(this.taskList == null){
            this.taskList = new ArrayList<>() ;
            this.taskList.addAll(taskList);
        }else{
            this.taskList.addAll(taskList);
        }
        return this ;
    }
    /**
     * 分配任务
     */
    abstract  void allocation();

    /**
     * 任务执行简化为迭代MAP 打印中其中的k - v
     * 分析其中的任务ID及服务器ID 来看分配策略是否正确
     */
    public void excute(){

        if(this.taskMap == null){
            System.out.println("无任务！");
        }
        else{

            Set<Long > set = this.taskMap.keySet();
            for(Long key : set  ){
                System.out.println("任务ID为："+key+"--->执行它的服务器为："+taskMap.get(key));

            }
        }
    }


    /**
     * 总调度方法
     */
    public final void processed(){

        allocation() ;
        excute() ;

    }


}
