package com.everhomes.learning.demos.aop.DataUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author feiyue
 * @className DataCache
 * @description
 * @date 2018/7/21
 **/
public class DataCache {

    /**
     * 定义一个 ConcurrentHashMap 用来存放缓存
     */
    private static Map<String, Object> map = new ConcurrentHashMap<>();

    public static Map<String, Object> getMap() {
        return map;
    }

    public static void setMap(Map<String, Object> map) {
        DataCache.map = map;
    }
}
