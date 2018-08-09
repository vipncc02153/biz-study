package com.everhomes.learning.demos.treeMap.hmb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/treeMapHmb")
public class RunController {

	@Autowired
	TreeMapService service;
	
    @RequestMapping("put")
	public String put(String key, String value) {
		service.put(key, value);
		return "success";
	}
}
