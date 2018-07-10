package com.everhomes.learning.demos.beantojson.ycx.beantojson;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.everhomes.learning.sample.beantojson.CreateTestBean;

public class YcxBeanToJson {
	
	//判断是否是一个bean类
	public static boolean isBean(Object obj){
		try {
			Class className = obj.getClass();//获得实体类名
			Field[] fields = obj.getClass().getDeclaredFields();//获得属性
			for(Field field:fields){
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), className);
				Method getMethod = pd.getReadMethod();//获得get方法
			}
		}catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public static StringBuffer objToJSON(Object obj){
		StringBuffer jsonString = new StringBuffer("");
		try {
			Class className = obj.getClass();//获得实体类名
			Field[] fields = obj.getClass().getDeclaredFields();//获得属性
			//获得Object对象中的所有方法
			jsonString.append("{");
			for(Field field:fields){
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), className);
				Method getMethod = pd.getReadMethod();//获得get方法
				getMethod.invoke(obj);//此处为执行该Object对象的get方法
				//System.out.println(getMethod.getReturnType() + " - " + field.getName() + " - " + getMethod.invoke(obj));
				if(getMethod.invoke(obj) != null) {
					String key = "\"" + field.getName() + "\"";
					String value = "";
					Object sonObj = getMethod.invoke(obj);
					if(isBean(sonObj)) {//如果是一个bean类，递归
						value = objToJSON(sonObj).toString();
					}else {
						value = "\"" + getMethod.invoke(obj) + "\"";
					}
					jsonString.append(key + ":" + value + ",");
				}
			}
			if(jsonString.length() > 1) {
				jsonString.deleteCharAt(jsonString.length() - 1);//去掉最后一个逗号
			}
			jsonString.append("}");
		}catch(Exception e) {
			System.out.println(e);
		}
		return jsonString;
	}

	
	public static void main(String[] args) {
		Object obj = CreateTestBean.createTest1();
		System.out.println(objToJSON(obj));
	}

}
