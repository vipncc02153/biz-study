// @formatter:off
package com.everhomes.learning.sample.provider;

import com.everhomes.learning.demos.cache.lyl.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class LylCacheProviderImpl implements LylCacheProvider{

    @Cacheable
    @Override
    public int getInteger(int i) {
        return i+1;
    }
}
