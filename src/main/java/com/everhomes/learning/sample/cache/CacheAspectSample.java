package com.everhomes.learning.sample.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class CacheAspectSample {
    private static final Logger logger = LoggerFactory.getLogger(CacheAspectSample.class);
    private static final Map<String,Object> map = new HashMap<>();

//    @Pointcut(value = "@within(com.everhomes.learning.sample.cache.Cacheable)")
//    private void getCacheMethod(){}
//
//    @Pointcut(value = "@within(com.everhomes.learning.sample.cache.CacheEvict)")
//    private void flushCacheMethod(){}

    @Around(value = "@annotation(cacheAnnotation)")
    public Object getCache(ProceedingJoinPoint joinPoint,Cacheable cacheAnnotation) throws Throwable{
        Object result = null;
        String key = getKey(joinPoint,cacheAnnotation.key());
        result = map.get(key);
        if (result == null){
            result = joinPoint.proceed();
            if (result != null) {
                map.put(key, result);
                logger.info("store value the key is {} the value is{}",key,result);
            }
        }

        return result;
    }

    @Around(value = "@annotation(evictAnnotation)")
    public Object flushCache(ProceedingJoinPoint joinPoint,CacheEvict evictAnnotation) throws Throwable{
        String key = getKey(joinPoint,evictAnnotation.key());
        if (map.get(key) != null)
            map.remove(key);
        logger.info("flush cache the key is {}",key);
        return joinPoint.proceed();
    }

    private String getKey(ProceedingJoinPoint joinPoint ,String key){
        return key + StringUtils.join(joinPoint.getArgs(),'_');
    }
}
