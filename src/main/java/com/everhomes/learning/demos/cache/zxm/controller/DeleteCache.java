package com.everhomes.learning.demos.cache.zxm.controller;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DeleteCache {

    String value();

    String key() default "";

}
