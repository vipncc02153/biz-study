package com.everhomes.learning.sample.service;

public  interface SampleService {    
    String getCurrentTime();

    String getCache();

    String flushCache();

    void getLock();
}
