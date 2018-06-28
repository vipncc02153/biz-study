package com.everhomes.learning.core.huangPY.beantojson;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class BeanToJson {
    /**
     * <ul>
     *     <li>方法一：采用获取类中所有属性的get方法后，再由get方法获取属性的值，操作略复杂，但侵入性低</li>
     *     <li>实际上PropertyDescriptor可以用于管理类的get和set方法，为了加深对映射的理解写了一个field与method的映射方法</li>
     * </ul>
     * @param bean
     * @return
     */
    public static String beanToJson(Object bean){
        Class<? extends Object> clazz = bean.getClass();
        Field[] fields = getAllDeclaredFields(clazz);
        Map<String, Method> methods = getAllMethod(clazz);
        StringBuilder json = new StringBuilder("{");

        try{
            for(Field field : fields){
                Method method = methods.get(field.getName());
                Object invoke = method.invoke(bean);
                json.append(invokeToResult(invoke, field));

            }

        }catch(Exception e){
            e.printStackTrace();
        }

        json = new StringBuilder(json.substring(0, json.length() - 2));
        json.append("}");

        return json.toString();
    }

    /**
     * <ul>
     *     <li>方法二：直接用Field采用强制授权方式获取类中的私有属性，效率较高但是具有侵入性，稳定性也未知</li>
     * </ul>
     * @param testBean1
     * @return
     */
    public static String beanToJson2(Object testBean1){
        Field[] fields = getAllDeclaredFields(testBean1.getClass());
        StringBuilder json = new StringBuilder("{");
        for(Field field : fields){
            try {
                field.setAccessible(true);
                Object invoke = field.get(testBean1);
                json.append(invokeToResult2(invoke, field));
            }catch(Exception e){

                e.printStackTrace();
            }
        }
        json = new StringBuilder(json.substring(0, json.length() - 2));
        json.append("}");
        return json.toString();
    }

    private static String invokeToResult(Object invoke,Field field){
        if (invoke != null ) {
            if(isNumber(field)) {
                return "\"" + field.getName() + "\"" + ":" + invoke.toString() + ", ";
            }else if(isBean(invoke)){
                return "\"" + field.getName() + "\"" + ":" + beanToJson(invoke) + ", ";
            }else if(field.getGenericType().toString().equals("class java.util.Date")){
                return "\"" + field.getName() + "\"" + ":" + "\"" + formatDateObj(invoke) + "\"" + ", ";
            }else{
                return "\"" + field.getName() + "\"" + ":" + "\"" + invoke.toString() + "\"" + ", ";
            }
        }
        return "";
    }

    private static String invokeToResult2(Object invoke,Field field){
        if (invoke != null ) {
            if(isNumber(field)) {
                return "\"" + field.getName() + "\"" + ":" + invoke.toString() + ", ";
            }else if(isBean(invoke)){
                return "\"" + field.getName() + "\"" + ":" + beanToJson2(invoke) + ", ";
            }else if(field.getGenericType().toString().equals("class java.util.Date")){
                return "\"" + field.getName() + "\"" + ":" + "\"" + formatDateObj(invoke) + "\"" + ", ";
            }else{
                return "\"" + field.getName() + "\"" + ":" + "\"" + invoke.toString() + "\"" + ", ";
            }
        }
        return "";
    }

    private static String formatDateObj(Object invoke){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置显示格式
        return dateFormat.format(invoke);
    }

    private static Boolean isNumber(Field field){
        return field.getGenericType().toString().equals("class java.math.BigDecimal")
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
                || field.getGenericType().toString().equals("double");
    }

    private static Map<String,Method> getAllMethod(Class<? extends Object> clazz){
        Map<String,Method> allMethods = new HashMap<>();

        Field[] fields = getAllDeclaredFields(clazz);
        Method[] allMethod = clazz.getMethods();

        for(Field field: fields){
            String fieldReadMethodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            for(Method method: allMethod){
                if(method.getName().equals(fieldReadMethodName)){
                    allMethods.put(field.getName(),method);
                }
            }
        }

        return allMethods;
    }

    /**
     * <ul>
     *     <li>如果使用Class.getDeclaredFields()方法则只能获取当前子类中的属性，故写一个工具类用于获取类中包括其父类属性在内的所有属性</li>
     * </ul>
     * @param clazz
     * @return
     */
    private static Field[] getAllDeclaredFields(Class<? extends Object> clazz){
        List<Field> allFields = new ArrayList<>();

        Class<? extends Object> currentClz = clazz;

        while(currentClz != null){
            Collections.addAll(allFields, currentClz.getDeclaredFields());
            currentClz = currentClz.getSuperclass();
        }
        return allFields.toArray(new Field[allFields.size()]);

    }

    /**
     * <ul>
     *     <li>判断当前属性是不是javabean对象，如果是则将会将该属性也json化</li>
     *     <li>判断依据为该属性的类中只含有set，get，toString方法</li>
     * </ul>
     * @param bean
     * @return
     */
    private static Boolean isBean(Object bean){
        Class<? extends Object> clazz = bean.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods)
        {
            String name = method.getName();

            if (!name.startsWith("set") && !name.startsWith("get") && !name.startsWith("toString")) {
                return false;
            }
        }
        return true;
    }
}
