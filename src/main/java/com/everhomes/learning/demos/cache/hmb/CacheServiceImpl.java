package com.everhomes.learning.demos.cache.hmb;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;


@Component
public class CacheServiceImpl implements CacheService{

	@Override
	@Cacheable(cacheNames = {"user"})
	public String findName(Long userId) {
		System.out.println("not using cache");
		return "name-"+userId;
	}

	@Override
	@CacheEvict(value="user")
	public void deleteCache(Long userId) {
		System.out.println("delete cache");
	}

}
