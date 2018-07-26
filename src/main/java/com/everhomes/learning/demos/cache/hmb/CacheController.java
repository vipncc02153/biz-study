package com.everhomes.learning.demos.cache.hmb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cacheHmb")
public class CacheController {
	
	@Autowired
	CacheService service;
	
    @RequestMapping("findName")
	public String findName(Long userId) {
    	System.out.println("in findName userId："+userId);
		return service.findName(userId);
	}
	
    @RequestMapping("deleteCache")
	public void deleteCache(Long userId) {
    	System.out.println("in deleteCache userId："+userId);
		 service.deleteCache(userId);
	}
	
}
