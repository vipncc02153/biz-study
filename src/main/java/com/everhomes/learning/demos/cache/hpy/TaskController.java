package com.everhomes.learning.demos.cache.hpy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("task")
public class TaskController {
    @Autowired
    TaskService taskService;

    @RequestMapping(value = "saveTask")
    @ResponseBody
    public String addTask() {
        taskService.saveTask("No.1", 1);
        return "OK";
    }

    @RequestMapping(value = "getCacheSuccess")
    @ResponseBody
    public Object getCacheSuccess() {
        return taskService.getTask("No.1");
    }

    @RequestMapping(value = "getCacheDefeat")
    @ResponseBody
    public Object getCacheDefeat() {
        return taskService.getTask("No.2");
    }

    @RequestMapping(value = "deleteCache")
    @ResponseBody
    public Object deleteCache() {
        taskService.deleteTask("No.1");
        return "OK";
    }
}
