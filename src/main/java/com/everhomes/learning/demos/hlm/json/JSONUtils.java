package com.everhomes.learning.demos.hlm.json;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

public class JSONUtils {
			
	private static final Log logger = LogFactory.getLog(JSONUtils.class);
	
	/**
	 * 返回  {"deptName":"研发部","deptNo":1001,"deptManager":"小刚"}   样子的字符串
	 * @param obj
	 * @return
	 */
	public static String transferBean2JsonString(Object obj){
		//获取Class 对象
		Class<? extends Object> clzz = obj.getClass();
		//获取属性数组
		Field[] fieldArray = clzz.getDeclaredFields();
		//容器
		StringBuffer sb = new StringBuffer();
		//循环
		for(Field field : fieldArray){
			String name = field.getName();
			//System.out.println(name);
			logger.info("name :"+name);
			Type type = field.getGenericType();
			//System.out.println(type);
			logger.info("type :"+type);
			try {
				Method method = clzz.getMethod("get"+name.substring(0, 1).toUpperCase()+name.substring(1), null);
				Object returnValue = method.invoke(obj, null);
				if(returnValue != null){
					if(isBaseType(type)){						
						sb.append(buildString(addStringMark(name,String.class) , addStringMark(returnValue,type)));
						logger.info("returnValue :"+returnValue);
					}else{
						String returnStr =transferBean2JsonString(returnValue);
						sb.append(buildString(addStringMark(name,String.class) ,addStringMark(returnStr,type)));
						logger.info("returnValue :"+returnStr);
					}
				}
			} catch (NoSuchMethodException e) {

				e.printStackTrace();
			} catch (SecurityException e) {

				e.printStackTrace();
			} catch (IllegalAccessException e) {

				e.printStackTrace();
			} catch (IllegalArgumentException e) {

				e.printStackTrace();
			} catch (InvocationTargetException e) {

				e.printStackTrace();
			}							
		}//for end
		
		return buildObjectString(sb);		
	}
	
	/**
	 * 判断基础类型
	 * @param type
	 * @return
	 */
	public static boolean isBaseType(Type type){
		
		if(type == null) return false ; 
		if(type.getTypeName().indexOf("String")>=0 ){
			return true ;
		}
		else if(type.getTypeName().indexOf("Integer")>=0){
			return true ;
		}
		else if(type.getTypeName().indexOf("Long")>=0){
			return true ;
		}
		else if(type.getTypeName().indexOf("Double")>=0){
			return true ;
		}
		else if(type.getTypeName().indexOf("Float")>=0){
			return true ;
		}
		else if(type.getTypeName().indexOf("Date")>=0){
			return true ;
		}
		else if(type.getTypeName().indexOf("int")>=0){
			return true ;
		}
		else if(type.getTypeName().indexOf("long")>=0){
			return true ;
		}
		else if(type.getTypeName().indexOf("double")>=0){
			return true ;
		}
		else if(type.getTypeName().indexOf("float")>=0){
			return true ;
		}
		return false;
	}
	
	public static String  buildString(Object name ,Object returnValue){
		
		if(returnValue==null || StringUtils.isEmpty(name)){
			return null;
		}
		return name+":"+returnValue.toString()+"," ;
	}
	
	/**
	 * 为某个对象加上大括号
	 * @param sb
	 * @return
	 */
	public static String buildObjectString(StringBuffer sb){
		if(sb == null || StringUtils.isEmpty(sb.toString())) return "";
		String returnStr = sb.toString();
		returnStr = returnStr.substring(0, returnStr.length()-1);
		return "{"+returnStr+"}";
		
	}
	
	/**
	 * 为String 类型的值为双引号
	 * @return
	 */
	public static Object addStringMark(Object value,Type type){
		if(value!=null && type.getTypeName().indexOf("String")>=0){
			return "\""+value + "\"" ;
		}
		return value;
	}
	
}
