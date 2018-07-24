package com.everhomes.learning.demos.cache.zxm.controller;

import java.lang.annotation.*;

/**
 * @author feiyue
 * @className Caching
 * @description
 * @date 2018/7/23
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Caching {

    GetCache[] cacheAble() default {};

    DeleteCache[] cacheDisable() default {};
}
