package com.everhomes.learning.demos.aop.controller;

import com.everhomes.learning.demos.aop.DataUtils.DataCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author feiyue
 * @className CacheInterceptor
 * @description
 * @date 2018/7/21
 **/
@Configuration
@Aspect
public class CacheInterceptor{

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheInterceptor.class);

    public CacheInterceptor() {
    }

    /**
     * 生成缓存 key
     */
    private Object generateKey(String value, String key, ProceedingJoinPoint invocation) {
        StringBuilder cacheKey = new StringBuilder(value);
        if("".equals(key)){
            if(null != invocation.getArgs()) {
                for (Object object : invocation.getArgs()) {
                    cacheKey.append("&");
                    String argType = object.getClass().getSimpleName();
                    cacheKey.append(argType+ ":");
                    cacheKey.append(object.toString());
                }
            }
            return cacheKey;
        }else{
            cacheKey.append("&").append(key).append("&");
            if(null != invocation.getArgs()) {
                for (Object object : invocation.getArgs()) {
                    String type = object.getClass().getSimpleName();
                    cacheKey.append(type+ ":");
                    cacheKey.append(object.toString());
                }
            }
        }
        return cacheKey;
    }

    /**
     * 放入或者获取缓存值
     */
    private Object getOrPutCache(ProceedingJoinPoint invocation, Object cacheKey) throws Throwable {
        Map<String, Object> map = DataCache.getMap();
        Object result = map.get(cacheKey.toString());
        if (null == result) {
            result = invocation.proceed();
            LOGGER.info("结果是：{}", result);
            map.put(cacheKey.toString(), result);
        }
        return result;
    }

    /**
     * 删除缓存
     */
    private void deleteCache(ProceedingJoinPoint invocation, Object cacheKey) {
        Map<String, Object> map = DataCache.getMap();
        if(map.get(cacheKey.toString())!= null)
            map.remove(cacheKey.toString());
    }

   /**
   * @author feiyue
   * @description 获取或者增加缓存的实现
   * @date  2018/7/23
   * @param
   * @return java.lang.Object
   **/
   @Around(value = "@annotation(com.everhomes.learning.demos.aop.controller.GetCache)")
   public Object getOrSetCache(ProceedingJoinPoint pjp) throws Throwable{
       MethodSignature ms = (MethodSignature) pjp.getSignature();
       Method method = ms.getMethod();
       Annotation[] annotations = method.getDeclaredAnnotations();
       if (null != annotations && annotations.length > 0) {
           Object key;
           Object obj;
           for(Annotation annotation : annotations){
               if(annotation instanceof GetCache){
                   GetCache getCache = (GetCache) annotation;
                   key = this.generateKey(getCache.value(), getCache.key(), pjp);
                   obj = getOrPutCache(pjp, key);
                   return obj;
               }
           }
       }
       return pjp.proceed();
   }

    /**
     * @author feiyue
     * @description 删除缓存的实现
     * @date  2018/7/23
     * @param
     * @return java.lang.Object
     **/
    @Around(value = "@annotation(com.everhomes.learning.demos.aop.controller.DeleteCache)")
    public Object deleteCache(ProceedingJoinPoint pjp) throws Throwable{
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        Method method = ms.getMethod();
        Annotation[] annotations = method.getDeclaredAnnotations();
        if (null != annotations && annotations.length > 0) {
            Object key;
            for(Annotation annotation : annotations){
                if(annotation instanceof DeleteCache){
                    DeleteCache deleteCache = (DeleteCache) annotation;
                    key = this.generateKey(deleteCache.value(), deleteCache.key(), pjp);
                    deleteCache(pjp, key);
                }
            }
        }
        return pjp.proceed();
    }


    /**
     * @author feiyue
     * @description 多个缓存的实现
     * @date  2018/7/23
     * @param
     * @return java.lang.Object
     **/
    @Around(value = "@annotation(com.everhomes.learning.demos.aop.controller.Caching)")
    public Object Caching(ProceedingJoinPoint pjp) throws Throwable{
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        Method method = ms.getMethod();
        Caching caching = method.getAnnotation(Caching.class);
        if(null != caching) {
            Object key;
            Object obj;
            GetCache[] abledCaches = caching.cacheAble();
            DeleteCache[] disabledCaches = caching.cacheDisable();
            if(abledCaches.length>0){
                for(GetCache getCache : abledCaches){
                    key = this.generateKey(getCache.value(), getCache.key(), pjp);
                    obj = this.getOrPutCache(pjp, key);
                    return obj;
                }
            }

            if(disabledCaches.length>0){
                for(DeleteCache deleteCache : disabledCaches){
                    key = this.generateKey(deleteCache.value(), deleteCache.key(), pjp);
                    deleteCache(pjp, key);
                }
            }
        }
        return pjp.proceed();
    }
}
