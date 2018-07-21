// @formatter:off
package com.everhomes.learning.demos.cache.lyl;


import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class CacheAspect {
    private static final Logger logger = LoggerFactory.getLogger("METHOD_CACHE");
    private static Map<String, Object> cacheMap = new LinkedHashMap();

    @Around("@annotation(com.everhomes.learning.demos.cache.lyl.Cacheable)")
    public Object cache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        String key = getCacheKey(proceedingJoinPoint);
        result = cacheMap.get(key);
        if (result == null) {
            result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
            if (result == null) {
                logger.info("value is empty,key={}",key);
            }else {
                logger.info("cache key,key={},value={}",key,result);
                cacheMap.put(key,result);
            }
        }
        logger.info("get cache! key={},value={}",key,result);
        return result;
    }

    @Around("@annotation(com.everhomes.learning.demos.cache.lyl.CacheEvict)")
    public Object evict(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        String key = getCacheKey(proceedingJoinPoint);
        result = cacheMap.get(key);
        if (result != null) {
            cacheMap.remove(key);
        }
        logger.info("evict cache! key={},result = {}",key,result);
        return proceedingJoinPoint.proceed();
    }


    /**
     * 根据类名、方法名和参数值获取唯一的缓存键
     * @return 格式为 "包名.类名.方法名.参数类型.参数值"
     */
    private String getCacheKey(ProceedingJoinPoint joinPoint) {
        return String.format("%s.%s",
                joinPoint.getSignature().toString().split("\\s")[1], StringUtils.join(joinPoint.getArgs(), ","));
    }

}
