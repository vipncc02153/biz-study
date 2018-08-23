package com.everhomes.learning.sample.distributelock;

public interface CoordinationProvider {

    NamedLock getNamedLock(String name);
}
