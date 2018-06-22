package com.everhomes.learning.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everhomes.learning.sample.service.SampleService;

@RestController
@RequestMapping("/sample")
public class SampleController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class);
	
    @Autowired
    private SampleService sampleService;
    
    @RequestMapping("getCurrentTime")
    public String getCurrentTime() {
        String time = sampleService.getCurrentTime();
        if(LOGGER.isDebugEnabled()) {
        	LOGGER.debug("Get current time, time={}", time);
        }
        
        return time;
    }
}
