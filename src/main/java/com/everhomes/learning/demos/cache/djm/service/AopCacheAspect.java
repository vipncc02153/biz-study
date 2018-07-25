package com.everhomes.learning.demos.cache.djm.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;

//aop配合cache注解实现缓存，AOP缓存切面

@Component
@Aspect
public class AopCacheAspect {
	private static final Logger logger = LoggerFactory.getLogger(AopCacheAspect.class);
	// 为了线程安全,使用Collections.synchronizedMap(new HashMap());
	private static Map<String, Object> aopCahche = Collections.synchronizedMap(new HashMap<String, Object>());

	@Around(value = "@annotation(com.everhomes.learning.demos.cache.djm.service.GetCacheable)")
	public Object getCache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object result = null;
		String key = generateKey(proceedingJoinPoint);
		result = aopCahche.get(key);
		if (result == null) {
			result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
			aopCahche.put(key, result);
		}
		logger.info("get cache! key={},value={}", key, result);
		return result;
	}

	@Around("@annotation(com.everhomes.learning.demos.cache.djm.service.UpdateCacheEvict)")
	public Object evict(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object result = null;
		String key = generateKey(proceedingJoinPoint);
		result = aopCahche.get(key);
		if (result != null) {
			aopCahche.remove(key);
		}
		logger.info("evict cache! key={},result = {}", key, result);
		return proceedingJoinPoint.proceed();
	}

	@Around("@annotation(com.everhomes.learning.demos.cache.djm.service.ClearCache)")
	public void clear(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		aopCahche = Collections.synchronizedMap(new HashMap<String, Object>());
	}

	// 生成缓存 key
	private String generateKey(ProceedingJoinPoint pJoinPoint) {
		StringBuffer cacheKey = new StringBuffer();
		String type = pJoinPoint.getArgs().getClass().getSimpleName();
		cacheKey.append(StringUtils.join(pJoinPoint.getArgs(), "::"));
		cacheKey.append(type);
		return cacheKey.toString();
	}
}
