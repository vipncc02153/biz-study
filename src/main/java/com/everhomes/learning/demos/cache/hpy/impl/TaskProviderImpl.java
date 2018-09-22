package com.everhomes.learning.demos.cache.hpy.impl;

import com.everhomes.learning.demos.cache.hpy.DeleteTaskCache;
import com.everhomes.learning.demos.cache.hpy.DoTaskCache;
import com.everhomes.learning.demos.cache.hpy.GetTaskCache;
import com.everhomes.learning.demos.cache.hpy.TaskProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TaskProviderImpl implements TaskProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskProvider.class);

    @Override
    @DoTaskCache()
    public void saveTask(String key, Object clazz) {

        LOGGER.info("provider 方法:" + key + " :" + clazz);
    }

    @Override
    @GetTaskCache()
    public Object getTask(String key) {
        LOGGER.info("未获取到缓存，执行provider 方法");
        return 123;
    }

    
    @Override
    @DeleteTaskCache()
    public void deleteTask(String key) {
        LOGGER.info("provider 方法，删除：" + key);
    }
}
