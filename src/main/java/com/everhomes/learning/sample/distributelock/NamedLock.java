package com.everhomes.learning.sample.distributelock;

import java.util.concurrent.Callable;

public interface NamedLock {

    NamedLock throwOnError(boolean value);

    NamedLock setLockAcquireTimeoutSeconds(int lockAcquireTimeoutSeconds);

    <T> Tuple<T, Boolean> enter(Callable<T> callback);

    boolean tryEnter(Runnable callback);
}
