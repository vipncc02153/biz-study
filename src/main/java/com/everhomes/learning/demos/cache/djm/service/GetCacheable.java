package com.everhomes.learning.demos.cache.djm.service;

import java.lang.annotation.*;

//自定义缓存注解（String、hash）

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented

public @interface GetCacheable {

}
