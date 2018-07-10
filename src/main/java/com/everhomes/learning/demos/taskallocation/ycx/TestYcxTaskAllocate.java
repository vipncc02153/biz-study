package com.everhomes.learning.demos.taskallocation.ycx;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestYcxTaskAllocate {
	
	public static void main(String[] args) {
		List<String> serverList = new ArrayList<String>();
		List<String> taskList = new ArrayList<String>();
		
		serverList.add("10.1.10.61");
		serverList.add("10.1.10.62");
		serverList.add("10.1.10.63");
		serverList.add("10.1.10.64");
		serverList.add("10.1.10.65");
		serverList.add("10.1.10.66");
		serverList.add("10.1.10.67");
		serverList.add("10.1.10.68");
		serverList.add("10.1.10.69");
		serverList.add("10.1.10.70");
		
		for(int i = 1;i <= 25;i++) {
			taskList.add(String.valueOf(i));
		}
		
		Map<String, String> serverTaskMapping = YcxTackAllocateUtil.randomAllocateTaskByIp(serverList, taskList);
		System.out.println("==========按IP地址随机分配任务==========");
		for(Map.Entry<String, String> entry : serverTaskMapping.entrySet()) {
			System.out.println("task = " + entry.getKey() + " , " + "server = " + entry.getValue());
		}
		
		System.out.println("==========按任务数量平分等==========");
		serverTaskMapping = YcxTackAllocateUtil.avgAllocateByTaskCount(serverList, taskList);
		for(Map.Entry<String, String> entry : serverTaskMapping.entrySet()) {
			System.out.println("task = " + entry.getKey() + " , " + "server = " + entry.getValue());
		}
	}

}
