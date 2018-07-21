// @formatter:off
package com.everhomes.learning.sample.service;

import com.everhomes.learning.sample.provider.LylCacheProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LylServiceImpl implements LylService{

    @Autowired
    private LylCacheProvider lylCacheProvider;
    @Override
    public int getInteger(int i) {
        return this.lylCacheProvider.getInteger(i);
    }

    @Override
    public int updateInteger(int i) {
        return this.lylCacheProvider.updateInteger(i);
    }
}
