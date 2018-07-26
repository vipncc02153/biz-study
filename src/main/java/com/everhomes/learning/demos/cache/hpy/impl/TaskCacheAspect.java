package com.everhomes.learning.demos.cache.hpy.impl;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class TaskCacheAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskCacheAspect.class);


    private static Map<String, Object> cacheMap = new HashMap<>();

    @Pointcut(value = "@annotation(com.everhomes.learning.demos.cache.hpy.DoTaskCache)")
    public void toParam(){

    }

    @After("toParam()")
    public void doTaskCache(JoinPoint point) {

        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        LOGGER.info("className = {}",className);
        LOGGER.info("methodName = {}",methodName);

        Object[] method_args = point.getArgs();


        System.out.println("创建了一个缓存，key=" + method_args[0] + " : value=" + method_args[1]);
        cacheMap.put((String)method_args[0],method_args[1]);
    }


    @Pointcut(value = "@annotation(com.everhomes.learning.demos.cache.hpy.GetTaskCache)")
    public void getParam(){

    }

    @Around("getParam()")
    public Object getTaskCache(ProceedingJoinPoint pjp) throws Throwable {

        Object[] method_args = pjp.getArgs();

        LOGGER.info("尝试一个缓存，key=" + method_args[0]);
        if(cacheMap.containsKey(method_args[0])){
            LOGGER.info("获取缓存成功");
            return cacheMap.get(method_args[0]);
        }else{
            return pjp.proceed();
        }
    }

    @Pointcut(value = "@annotation(com.everhomes.learning.demos.cache.hpy.DeleteTaskCache)")
    public void deleteParam(){

    }

    @After("deleteParam()")
    public void deleteTaskCache(JoinPoint point){

        Object[] method_args = point.getArgs();

        LOGGER.info("尝试删除缓存，key=" + method_args[0]);
        if(cacheMap.containsKey(method_args[0])){
            LOGGER.info("获取缓存成功");
            cacheMap.remove(method_args[0]);
        }else{
            LOGGER.error("未找到" + method_args[0]);
        }
    }



}
