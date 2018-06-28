package com.everhomes.learning.core.beantojson.hlm.json;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

public class Test {

	public static void main(String[] args) {
		Dept dept = new Dept() ;
		dept.setDeptNo(1001);
		dept.setDeptName("研发部");
		dept.setDeptManager("刘德华");
		dept.setNum((byte) 0);
		dept.setBigDecimal(new BigDecimal(1000000));
		
		User user = new User();
		user.setAge(22);
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
	     Date birth = null;
		try {
			birth = dateFormat.parse("2010-10-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		user.setBirth(birth);
		user.setSex("男");
		user.setDept(dept);
		user.setName("小明");
		user.setAge(20);
		user.setIsManager(false);
		
		String userJSONString = JSONUtils.transferBean2JsonString(user);
		//找了一个别人写的方法来对比
		String Str = JSONObject.toJSONString(user);
		System.out.println(userJSONString);
		System.out.println(Str);
		

	}

}
