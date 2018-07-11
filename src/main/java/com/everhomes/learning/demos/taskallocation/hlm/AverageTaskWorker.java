package com.everhomes.learning.demos.taskallocation.hlm;

import java.util.HashMap;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class AverageTaskWorker extends TaskWorker {

     Log log = LogFactory.getLog(AverageTaskWorker.class);
    /**
     * 分配任务方式为按任务数量平均分
     * 1.当任务数量比服务器数量少时，一台服务器分一个任务；
     * 2.当任务数据比服务器数量多时，平分的
     */
    @Override
    void allocation() {

        if(this.serviceList == null || this.taskList == null ||
            serviceList.size()<0 || taskList.size() <0){
                log.error("service or task do not exist");
                return ;
        }
        if(this.taskMap==null) {
            this.taskMap = new HashMap<>();
        }
        if(serviceList.size() >= taskList.size()){

            int cur = 0;
            for(Long taskKey :taskList){
                taskMap.put(taskKey ,serviceList.get(cur));
                cur ++ ;
            }
        }
        else{

            for(Long taskKey : taskList){
                int left = taskKey.intValue() % serviceList.size() ;
                taskMap.put(taskKey ,serviceList.get(left));
            }
        }

    }





}
