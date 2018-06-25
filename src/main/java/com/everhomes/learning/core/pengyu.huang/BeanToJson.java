package com.everhomes.learning.core.pengyu.huang;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ss on 2018/6/24.
 */
public class BeanToJson {
    public static String MapToJson(Object bean){
        //Map<String,String> map = beanToJson(bean);
        Map<String,String> map = beanToJsonWithSelfUnit(bean);
        if(!map.isEmpty()) {
            String json = "{";
            Integer index = 0;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                json += entry.getKey() + ":" + entry.getValue();
                if(index++ != map.entrySet().size()-1){
                    json += ", ";
                }
            }
            json += "}";
            return json;
        }else{
            return "undefine";
        }

    }
    public static Map<String,String> beanToJson(Object bean){
        Class<? extends Object> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Map<String, String> result = new HashMap<>();

        PropertyDescriptor pd = null;//new PropertyDescriptor(field.getClass(),clazz);

        Method getMethod = null;//pd.getReadMethod();

        try{
            for (Field field: fields) {
                pd = new PropertyDescriptor(field.getName(), clazz);

                if (null != pd) {

                    getMethod = pd.getReadMethod();
                    Object invoke = getMethod.invoke(bean);
                    invokeToResult(result,invoke,field);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static Map<String,String> beanToJsonWithSelfUnit(Object bean){
        Class<? extends Object> clazz = bean.getClass();
        Field[] fields = AllFieldAndReadMethod.getAllDeclaredFields(clazz);
        Map<String, Method> methods = AllFieldAndReadMethod.getAllMethod(clazz);
        Map<String, String> result = new HashMap<>();


        try{
            for(Field field : fields){
                Method method = methods.get(field.getName());
                Object invoke = method.invoke(bean);
                invokeToResult(result,invoke,field);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private static void invokeToResult(Map<String,String> result,Object invoke,Field field){
        if (invoke != null) {
            if(isNumber(field)) {
                result.put("\"" + field.getName() + "\"", invoke.toString());
            }else{
                result.put("\"" + field.getName() + "\"", "\"" +invoke.toString() + "\"");
            }
        }
    }

    private static Boolean isNumber(Field field){
        if(field.getGenericType().toString().equals("class java.math.BigDecimal")
                || field.getGenericType().toString().equals("class java.lang.Long")
                || field.getGenericType().toString().equals("class java.lang.Byte")
                || field.getGenericType().toString().equals("class java.lang.Integer")
                || field.getGenericType().toString().equals("class java.lang.Double")
                || field.getGenericType().toString().equals("class java.lang.Float")
                || field.getGenericType().toString().equals("class java.lang.Short")
                || field.getGenericType().toString().equals("int")
                || field.getGenericType().toString().equals("short")
                || field.getGenericType().toString().equals("byte")
                || field.getGenericType().toString().equals("float")
                || field.getGenericType().toString().equals("long")
                || field.getGenericType().toString().equals("double")) {
            return true;
        }else{
            return false;
        }
    }
}
