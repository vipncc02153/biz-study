// @formatter:off
package com.everhomes.learning.demos.beantojson.lyl.beantojson;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class BeanToJson {

    private List<String> type = Arrays.asList("java.lang.Integer","int","java.lang.Long","long","java.lang.Boolean","boolean",
            "java.lang.Double","double","java.lang.Byte","byte","java.lang.Float","float","java.lang.Char","char",
            "java.lang.Short","short","java.lang.String","java.math.BigDecimal","java.lang.StringBuilder");

    public <T> String beantoJson(T object) {
        if (object == null) {
            return "{}";
        }
        Field[] fields = object.getClass().getDeclaredFields();
        StringBuilder json = new StringBuilder();
        json.append("{");
        for (Field field :fields) {
            field.setAccessible(true);
            String name = field.getName();
            String typeName = field.getType().getName();
            Object value = null;
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (value == null) {
                continue;
            }
            if (type.contains(typeName)) {
                json.append("\"").append(name).append("\":");
                json.append(value);
                json.append(",");
            } else{
                json.append("\"").append(name).append("\":");
                json.append(beantoJson(value));
                json.setCharAt(json.length() -1 ,'}');
                json.append(",");
            }
        }
        json.setCharAt(json.length() -1 ,'}');
        return json.toString();
    }

}
