package com.everhomes.learning.demos.cache.hpy.impl;

import com.everhomes.learning.demos.cache.hpy.DoTaskCache;
import com.everhomes.learning.demos.cache.hpy.GetTaskCache;
import com.everhomes.learning.demos.cache.hpy.TaskProvider;
import org.springframework.stereotype.Component;

@Component
public class TaskProviderImpl implements TaskProvider {
    @Override
    @DoTaskCache()
    public void saveTask(String key, Object clazz) {

        System.out.println("provider 方法:" + key + " :" + clazz);
    }

    @Override
    @GetTaskCache()
    public Object getTask(String key) {
        System.out.println("未获取到缓存");
        return 123;
    }
}
