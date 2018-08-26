package com.everhomes.learning.demos.distributedLock.zhou.xm;

import java.util.concurrent.Callable;

/**
 * @author feiyue
 * @description
 * @date 2018/8/23
 **/
public interface NameLock {

    NameLock throwOnError(boolean var1);

    NameLock setLockAcquireTimeoutSeconds(int var1);

    <T> Tuple<T, Boolean> enter(Callable<T> var1);

    boolean tryEnter(Runnable var1);
}
