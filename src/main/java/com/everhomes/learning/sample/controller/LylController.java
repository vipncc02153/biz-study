// @formatter:off
package com.everhomes.learning.sample.controller;

import com.everhomes.learning.sample.service.LylService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lyl")
public class LylController {

    @Autowired
    private LylService lylService;
    @RequestMapping("getInteger")
    public int getInteger(int key) {
        return this.lylService.getInteger(key);
    }
}
