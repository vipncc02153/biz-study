package com.everhomes.learning.demos.aop.provider;

import com.everhomes.learning.demos.aop.controller.Caching;
import com.everhomes.learning.demos.aop.controller.DeleteCache;
import com.everhomes.learning.demos.aop.controller.GetCache;
import com.everhomes.learning.demos.aop.controller.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author feiyue
 * @className UserProviderImpl
 * @description
 * @date 2018/7/21
 **/
@Component
public class UserProviderImpl implements UserProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserProviderImpl.class);

    @GetCache(value="findUser-byId", key="id")
    @Override
    public User findUserById(String id) {
        LOGGER.info("查询id:{}, 没有走缓存",id);
        User user = new User();
        user.setId("1");
        user.setAge(18);
        user.setName("李四");
        return user;
    }

    @Override
    @Caching(cacheDisable = {
            @DeleteCache(value = "findUser-byId", key = "id"),
            @DeleteCache(value = "findUser-byIdAndName")
    })
    public void deleteUserById(String id) {
        LOGGER.info("删除id:{} 的User 缓存", id);
        LOGGER.info("删除findUser-byIdAndName 的缓存");
    }

    @Override
    @GetCache(value="findUser-byIdAndName")
    public User findUserByNameAndAge(String name, int age) {
        LOGGER.info("查询name:{}, age:{}, 没有走缓存", name, age);
        User user = new User();
        user.setId("2");
        user.setAge(18);
        user.setName("张三");
        return user;
    }

    @Override
    public List<User> findAllUser() {
        return null;
    }

    @Override
    public User findUserByName(String name) {
        return null;
    }
}
