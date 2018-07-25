package com.everhomes.learning.demos.cache.hlm.aop;


import com.everhomes.learning.demos.cache.hlm.bo.AopBo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

//用于声明这是一个spring 管理配置的bean 
@Configuration
//声明这是一个切面类
@Aspect
public class AopConfig {

	ConcurrentHashMap<String ,Object> cache = new ConcurrentHashMap<String ,Object>();

	Logger log = LoggerFactory.getLogger(AopConfig.class);

	//声明了一个表达式，描述要织入的目标的特性，如下样子就是想说com.learn.hlm.provider.包下的类都会被织入该切面
	@Around("execution(*  com.everhomes.learning.demos.cache.hlm.provider.*.get*(..)) ||" +
			" execution(*  com.everhomes.learning.demos.cache.hlm.provider.*.find*(..))")
	public Object storeCache(final ProceedingJoinPoint pjp) throws Throwable{
		try {
			log.info("查询缓存中是否有结果！");
			Object[] os = pjp.getArgs();
			String methodName = pjp.getSignature().getName() ;
			StringBuffer key = new StringBuffer(methodName.substring(0,3)) ;
			if(os != null && os.length >0){
				for(Object o : os){
					if(o == null ) continue ;
					key.append(o.toString());
				}
			}
			Object obj = cache.get(key.toString()) ;
			if(obj != null){
				log.info("缓存命中结果！");
				return obj ;
			}
		//调用原来方法		
		 obj = pjp.proceed();
			if(obj == null){
				return obj ;
			}
		 log.info("对查询结果进行缓存！");
			cache.put(key.toString() , obj) ;
		return obj ;
		
		} catch (Throwable e) {
          //不处理异常，将其抛给调用者
			throw e ;
		}
	}

	//清除缓存
	@Around("execution(*  com.everhomes.learning.demos.cache.hlm.provider.*.delete*(..)) || " +
			"execution(*  com.everhomes.learning.demos.cache.hlm.provider.*.update*(..))")
	public Object clearCache(final ProceedingJoinPoint pjp) throws Throwable{

		Object[] os = pjp.getArgs();
		if(os != null && os.length >0){
			AopBo bo = (AopBo)os[0] ;
			String methodName = pjp.getSignature().getName() ;
			//删除
			if(methodName.contains("delete") ||methodName.contains("update")){
				String key1 = "get"+bo.getId() ;
				String key2 = "fin";
				cache.remove(key1) ;
				log.info("清除缓存 key:"+key1);
				Set<String> set = cache.keySet();
				for(String str : set){
					if(str.contains(key2)){
						cache.remove(str) ;
						log.info("清除缓存 key:"+str);
					}
				}
			}
		}

		//调用原来方法
		Object obj = pjp.proceed();

		return obj ;

	}
}
