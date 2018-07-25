package com.everhomes.learning.demos.cache.zxm.controller;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface GetCache {
    String value();

    String key() default "";

    int expireSeconds() default 0;
}
