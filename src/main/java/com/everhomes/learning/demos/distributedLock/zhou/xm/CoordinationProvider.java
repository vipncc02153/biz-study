package com.everhomes.learning.demos.distributedLock.zhou.xm;

/**
 * @author feiyue
 * @className CoordinationProvider
 * @description
 * @date 2018/8/23
 **/
public interface CoordinationProvider {

    NameLock getNameLock(String lockName);
}
