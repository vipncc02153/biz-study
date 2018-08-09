package com.everhomes.learning.demos.treeMap.hmb.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectCountTime {
	
	Logger LOGGER = LoggerFactory.getLogger(AspectCountTime.class);

	
    @Around(value = "@annotation(cacheAnnotation)")
	public void countTime(ProceedingJoinPoint point, TimeCountAble cacheAnnotation) throws Throwable {
		long begin = System.currentTimeMillis();
		point.proceed();
		long end = System.currentTimeMillis();
		LOGGER.error("method:"+ cacheAnnotation.key() +" total time :"+(end-begin)+"ms");
	}
	
	
}
