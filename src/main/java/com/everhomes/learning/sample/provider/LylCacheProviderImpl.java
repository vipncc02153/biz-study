// @formatter:off
package com.everhomes.learning.sample.provider;

import com.everhomes.learning.demos.cache.lyl.CacheEvict;
import com.everhomes.learning.demos.cache.lyl.Cacheable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LylCacheProviderImpl implements LylCacheProvider{
    private static final Logger LOGGER = LoggerFactory.getLogger(LylCacheProviderImpl.class);

    @Override
    @Cacheable
    public int getInteger(int i) {
        LOGGER.info("not use cache");
        return i+1;
    }

    @Override
    @CacheEvict
    public int updateInteger(int i) {
        return i+2;
    }

}
