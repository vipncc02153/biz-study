package com.everhomes.learning.demos.cache.hpy.impl;

import com.everhomes.learning.demos.cache.hpy.TaskProvider;
import com.everhomes.learning.demos.cache.hpy.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskProvider taskProvider;

    @Override
    public void saveTask(String key, Object clazz){
        System.out.println("开始saveTask");
        taskProvider.saveTask(key, clazz);
    }

    @Override
    public Object getTask(String key){
        System.out.println("开始getTask");
        return taskProvider.getTask(key);
    }

}
