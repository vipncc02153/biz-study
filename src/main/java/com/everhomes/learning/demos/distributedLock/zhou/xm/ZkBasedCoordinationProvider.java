package com.everhomes.learning.demos.distributedLock.zhou.xm;

/**
 * @author feiyue
 * @className ZkBasedCoordinationProvider
 * @description
 * @date 2018/8/23
 **/
public class ZkBasedCoordinationProvider implements CoordinationProvider{

    private static final int DEFAULT_LOCK_ACQUIRE_TIMEOUT = 86400;

    public ZkBasedCoordinationProvider() {
    }

    @Override
    public NameLock getNameLock(String lockName) {
        return new ZkNameLock(this, lockName, DEFAULT_LOCK_ACQUIRE_TIMEOUT);
    }
}
