package com.everhomes.learning.core.beantojson.zst.beantojson;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        BeanToJson beanToJson = new BeanToJson();
        TestBean1<TestBean1> bean = new TestBean1();
        bean.setBigDecimal1(new BigDecimal(10));
        bean.setLong1(10l);
        bean.setBool1(true);
        bean.setBool2(false);
        bean.setByte1((byte)1);
        bean.setByte2((byte)2);

        bean.setObject(new BeanToJson());


        TestBean2 bean2 = new TestBean2();
        bean2.setString1("1111");
        bean2.setS("2222");
        Gson gson = new GsonBuilder().create();

        try {
            System.out.println(com.everhomes.learning.core.beantojson.huangPY.beantojson.BeanToJson.beanToJson(CreateTestBean.createTest1()));
            System.out.println(gson.toJson(CreateTestBean.createTest1()));
        }catch (Exception e){

        }
    }
}
