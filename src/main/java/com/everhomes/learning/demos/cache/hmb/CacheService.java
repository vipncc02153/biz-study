package com.everhomes.learning.demos.cache.hmb;


public interface CacheService {

	String findName(Long userId);

	void deleteCache(Long userId);

}
