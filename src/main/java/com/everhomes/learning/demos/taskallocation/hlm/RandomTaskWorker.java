package com.everhomes.learning.demos.taskallocation.hlm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Random;


public class RandomTaskWorker extends TaskWorker {

     Log log = LogFactory.getLog(RandomTaskWorker.class);
    /**
     * 按IP地址随机
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

        for(int i=0 ; i<this.taskList.size() ; i++){
             Random rnd = new Random();
            taskMap.put(this.taskList.get(i) ,serviceList.get(rnd.nextInt(serviceList.size())));
        }

    }





}
