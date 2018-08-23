package com.everhomes.learning.sample.distributelock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ZkCoordinationProvider implements CoordinationProvider {

    private static final int DEFAULT_LOCK_ACQUIRE_TIMEOUT = 86400;

    @Autowired
    private ZkProvider zkProvider;

    @Override
    public NamedLock getNamedLock(String name) {
        return new ZkNamedLock(this.zkProvider,name,DEFAULT_LOCK_ACQUIRE_TIMEOUT);
    }
}
