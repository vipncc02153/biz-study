package com.everhomes.learning.sample.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class SampleServiceImpl implements SampleService {
    public SampleServiceImpl() {
    }
    
    public String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }
}
