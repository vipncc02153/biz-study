package com.everhomes.learning.demos.cache.zxm.service;

import com.everhomes.learning.demos.cache.zxm.controller.User;

import java.util.List;

/**
 * @author feiyue
 * @className UserService
 * @description
 * @date 2018/7/21
 **/
public interface UserService {

    User findUserById(String id);
    User findUserByName(String name);
    List<User> findAllUser();
    User findUserByNameAndAge(String name, int age);
    void deleteUserById(String id);
}
