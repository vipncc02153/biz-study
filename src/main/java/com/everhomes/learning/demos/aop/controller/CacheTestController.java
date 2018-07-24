package com.everhomes.learning.demos.aop.controller;

import com.everhomes.learning.demos.aop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author feiyue
 * @className CacheTestController
 * @description
 * @date 2018/7/21
 **/
@RestController
public class CacheTestController {

    @Autowired
    private UserService userService;

    @RequestMapping("/aop/findUserById")
    public User findUserById(String id){
        User user = userService.findUserById(id);
        return user;
    }

    @RequestMapping("/aop/findUserByNameAndAge")
    public User findUserByNameAndAge(String name, int age){
        User user = userService.findUserByNameAndAge(name, age);
        return user;
    }

    @RequestMapping("/aop/deleteUserById")
    public void deleteUserById(String id){
        userService.deleteUserById(id);
    }
}
