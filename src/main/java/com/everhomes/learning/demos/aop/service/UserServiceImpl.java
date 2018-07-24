package com.everhomes.learning.demos.aop.service;

import com.everhomes.learning.demos.aop.controller.User;
import com.everhomes.learning.demos.aop.provider.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author feiyue
 * @className UserServiceImpl
 * @description
 * @date 2018/7/21
 **/
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserProvider userProvider;

    @Override
    public User findUserById(String id) {
        User user = userProvider.findUserById(id);
        return user;
    }

    @Override
    public User findUserByName(String name) {
        User user = userProvider.findUserByName(name);
        return user;
    }

    @Override
    public List<User> findAllUser() {
        List<User> userList = userProvider.findAllUser();
        return userList;
    }

    @Override
    public User findUserByNameAndAge(String name, int age) {
        User user = userProvider.findUserByNameAndAge(name, age);
        return user;
    }

    @Override
    public void deleteUserById(String id) {
        userProvider.deleteUserById(id);
    }
}
