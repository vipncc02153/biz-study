package com.everhomes.learning.demos.cache.zxm.provider;

import com.everhomes.learning.demos.cache.zxm.controller.Caching;
import com.everhomes.learning.demos.cache.zxm.controller.DeleteCache;
import com.everhomes.learning.demos.cache.zxm.controller.GetCache;
import com.everhomes.learning.demos.cache.zxm.controller.User;

import java.util.List;

/**
 * @author feiyue
 * @className UserProvider
 * @description
 * @date 2018/7/21
 **/
public interface UserProvider {

    @GetCache(value="findUser-byId", key="id")
    User findUserById(String id);

    @Caching(cacheDisable = {
            @DeleteCache(value = "findUser-byId", key = "id"),
            @DeleteCache(value = "findUser-byIdAndName")
    })
    void deleteUserById(String id);

    @GetCache(value="findUser-byIdAndName")
    User findUserByNameAndAge(String name, int age);

    List<User> findAllUser();

    User findUserByName(String name);
}
