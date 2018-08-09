package com.everhomes.learning.sample.beantojson;


import com.everhomes.learning.sample.segmenttree.SegmentTree;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BeanToJson {

    public String toJson(Object object){
        StringBuilder build=new StringBuilder();
        build.append("{");
        Class cla=null;
        try {
            //反射加载类
            cla=Class.forName(object.getClass().getName());
        } catch (ClassNotFoundException e) {
            System.out.println(object.getClass().toString().concat(" 未找到这个类"));
            e.printStackTrace();
            return null;
        }
        //获取java类的变量
        Field[] fields=getAllDeclaredFields(cla);
        StringBuffer methodname=new StringBuffer();
        String separate="";
        for (Field field : fields){

            methodname.append("get");
            methodname.append(field.getName().substring(0,1).toUpperCase());
            methodname.append(field.getName().substring(1));

            Method method=null;
            try {
                //获取java的get方法
                method=cla.getMethod(methodname.toString());
            } catch (NoSuchMethodException | SecurityException e) {
                methodname.setLength(0);
                continue;
            }

            try {
                //执行get方法，获取变量参数的直。
                Object invoke = method.invoke(object);
                if (invoke != null) {
                    build.append(separate);
                    build.append("\"");
                    build.append(field.getName());
                    build.append("\":");
                    build.append(parseBeanElement(invoke,invoke.getClass()));
                    separate=",";
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            methodname.setLength(0);

        }
        build.append("}");
        return build.toString();
    }

    private static Field[] getAllDeclaredFields(Class<? extends Object> clazz){
        List<Field> allFields = new ArrayList<>();

        Class<? extends Object> currentClz = clazz;

        while(currentClz != null){
            Collections.addAll(allFields, currentClz.getDeclaredFields());
            currentClz = currentClz.getSuperclass();
        }
        return allFields.toArray(new Field[allFields.size()]);

    }

    private String parseBeanElement(Object value , Class<?> type){
        if (!Object.class.isAssignableFrom(type)){ //java基础类型
            return value.toString();
        }
        if (Number.class.isAssignableFrom(type)){
            return value.toString();  //数字类型
        }
        if (type == String.class || type == StringBuilder.class){
            return "\""+value.toString()+"\""; //字符串
        }
        if (type == Boolean.class){
            return value.toString(); //布尔型
        }

        return toJson(value);
    }

    public static void main(String [] args){
       Double i = 1.0;
       int j = 2;




    }
}
