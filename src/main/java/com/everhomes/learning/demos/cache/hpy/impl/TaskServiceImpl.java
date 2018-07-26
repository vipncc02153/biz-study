package com.everhomes.learning.demos.cache.hpy.impl;

import com.everhomes.learning.demos.cache.hpy.TaskProvider;
import com.everhomes.learning.demos.cache.hpy.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    TaskProvider taskProvider;

    @Override
    public void saveTask(String key, Object clazz){
        LOGGER.info("开始saveTask");
        taskProvider.saveTask(key, clazz);
    }

    @Override
    public Object getTask(String key){
        LOGGER.info("开始getTask");
        return taskProvider.getTask(key);
    }

    @Override
    public void deleteTask(String key){
        LOGGER.info("开始deleteTask");
        taskProvider.deleteTask(key);
    }

}
