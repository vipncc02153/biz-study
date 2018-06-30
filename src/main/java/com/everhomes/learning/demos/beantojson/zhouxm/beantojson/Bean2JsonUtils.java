package com.everhomes.learning.demos.beantojson.zhouxm.beantojson;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @ClassName Bean2JsonUtils
 * @Description Bean 转 Json 的工具类
 * @Author feiyue
 * @Date 2018/6/30
 **/
public class Bean2JsonUtils {

    public static String bean2Json(Object object) throws Exception{

        Class clz = object.getClass();
        StringBuilder jsonString = new StringBuilder("{");

        /** 获取所有字段 */
        Field[] fields = clz.getDeclaredFields();
        int i = 0;
        for(Field field : fields){
            field.setAccessible(true);
            String key = field.getName();

            /** getMethod 只能获取 public 方法*/
            Method method = clz.getMethod("get"+key.substring(0,1).toUpperCase()+key.substring(1));
            Object value = method.invoke(object);
            if(value!=null && !isBean(value)){
                if(i==0)
                    jsonString.append("\"").append(key).append("\"").append(":").append("\"").append(value.toString()).append("\"");
                else
                    jsonString.append(",").append("\"").append(key).append("\"").append(":").append("\"").append(value.toString()).append("\"");
                i++;
            }else{
                if(i==0)
                    jsonString.append(bean2Json(value));
                else
                    jsonString.append(",").append(bean2Json(value));
                i++;
            }
        }
        return jsonString.append("}").toString();
    }
    
    /**
    * @Author feiyue
    * @Description  判断某个对象是否是 Bean
    * @Date  2018/6/30
    * @Param [object]
    * @return boolean
    **/
    public static boolean isBean(Object object){
        Class clz = object.getClass();
        Field[] fields = clz.getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            String key = field.getName();
            try{
                Method getMethod = clz.getMethod("get"+key.substring(0,1).toUpperCase()+key.substring(1));
                Method setMethod = clz.getMethod("get"+key.substring(0,1).toUpperCase()+key.substring(1));
            }catch (NoSuchMethodException e){
                return false;
            }
        }
        return true;
    }
}
