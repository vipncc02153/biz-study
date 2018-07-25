package com.everhomes.learning.sample.provider;

import com.everhomes.learning.sample.cache.CacheEvict;
import com.everhomes.learning.sample.cache.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class SampleCacheProviderImpl {

    @Cacheable(key = "cahce1")
    public String getCache(String s,Integer int1,Boolean bool){
       Double num =  Math.random()*1000;
       Integer response = num.intValue();
        return response.toString();
    }

    @CacheEvict(key = "cahce1")
    public void flushCache(String s,Integer int1,Boolean bool){

    }
}
