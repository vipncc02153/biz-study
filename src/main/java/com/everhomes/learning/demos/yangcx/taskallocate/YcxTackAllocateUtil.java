package com.everhomes.learning.demos.yangcx.taskallocate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class YcxTackAllocateUtil {
	
	/**
	 * 按IP地址随机分配任务
	 * @param serverList
	 * @param taskList
	 */
	public static Map<String, String> randomAllocateTaskByIp(List<String> serverList, List<String> taskList) {
		//Map<String, String> serverTaskMapping = new HashMap<>();
		Map<String, String> serverTaskMapping = new LinkedHashMap<>();//用于排序
		java.util.Random r = new java.util.Random();
		if(serverList != null && serverList.size() != 0 && taskList != null && taskList.size() != 0) {
			for(String task : taskList) {
				int index = r.nextInt(serverList.size());//产生一个随机索引
				serverTaskMapping.put(task, serverList.get(index));
			}
		}
		return serverTaskMapping;
	}
	
	
	/**
	 * 按任务数量平分等
	 * @param serverList
	 * @param taskList
	 */
	public static Map<String, String> avgAllocateByTaskCount(List<String> serverList, List<String> taskList) {
		//Map<String, String> serverTaskMapping = new HashMap<>();
		Map<String, String> serverTaskMapping = new LinkedHashMap<>();//用于排序
		if(serverList != null && serverList.size() != 0 && taskList != null && taskList.size() != 0) {
			int taskCount = taskList.size();
			int serverCount = serverList.size();
			int avgTaskCount = taskCount / serverCount;
			int mod = taskCount % serverCount;//求余
			int taskIndex = 0;
			for(int i = 0;i < serverList.size();i++) {
				String server = serverList.get(i);
				for(int j = taskIndex; j < taskIndex + avgTaskCount;j++) {
					serverTaskMapping.put(taskList.get(j), server);
				}
				taskIndex += avgTaskCount;
				if(i < mod) {
					serverTaskMapping.put(taskList.get(taskIndex), server);
					taskIndex++;
				}
			}
		}
		return serverTaskMapping;
	}

}
