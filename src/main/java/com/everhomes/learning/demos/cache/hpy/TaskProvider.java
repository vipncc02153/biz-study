package com.everhomes.learning.demos.cache.hpy;

public interface TaskProvider {

    void saveTask(String key, Object clazz);

    Object getTask(String key);

    void deleteTask(String key);
}
