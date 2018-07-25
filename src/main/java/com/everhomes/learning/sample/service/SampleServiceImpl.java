package com.everhomes.learning.sample.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.everhomes.learning.sample.provider.SampleCacheProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SampleServiceImpl implements SampleService {
    private static final Logger logger = LoggerFactory.getLogger(SampleServiceImpl.class);

    @Autowired
    private SampleCacheProviderImpl sampleCacheProvider;

    public SampleServiceImpl() {
    }
    
    public String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }

    @Override
    public String getCache() {
        String response = sampleCacheProvider.getCache("test",100,true);
        logger.info("getCache response : {}",response);
        return response;
    }

    @Override
    public String flushCache() {
        sampleCacheProvider.flushCache("test",100,true);
        return null;
    }


}
