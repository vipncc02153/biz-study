package com.everhomes.learning.demos.taskallocation.hmb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.CollectionUtils;

public class TaskDeal {

	public static final int BY_IP_HASH = 1;
	public static final int BY_COUNT_AVERAGE = 2;

	private int m;
	private int n;
	private List<String> servers;
	private List<Long> tasks;
	private Map<String, List<Long>> result = new HashMap<>();

	public TaskDeal(List<String> servers, List<Long> tasks) {

		this.servers = servers;
		this.tasks = tasks;

		m = null == servers ? 0 : servers.size();
		n = null == tasks ? 0 : tasks.size();
	}

	public void runWithStrategy(int strategy) {

		switch (strategy) {
		case BY_IP_HASH:

			byIpHash();
			
			break;

		case BY_COUNT_AVERAGE:

			byCountAvg();
			
			break;

		default:
			break;
		}

		return;
	}

	private void byCountAvg() {
		if (m == 0 || n == 0) {
			return;
		}

		for (int i = 0; i < m; i++) {

			List<Long> list = new ArrayList<>();
			for (int j = i; j < n; j += m) {
				list.add(tasks.get(j));
			}

			result.put(servers.get(i), list);
		}
	}

	private void byIpHash() {
		if (m == 0 || n == 0) {
			return;
		}

		// 初始化结果集
		for (String sever : servers) {
			result.put(sever, new ArrayList<>());
		}

		for (Long task : tasks) {
			
			int index = getIndexByTaskId(task);
			List<Long> list = result.get(servers.get(index));
			list.add(task);
		}

	}

	private int getIndexByTaskId(long taskId) {
		long nowTime = System.currentTimeMillis();
		long tmp = taskId + nowTime;
		return (tmp + "").hashCode() % m;
	}
	
	public void print() {
		for (Entry<String, List<Long>> item : result.entrySet()) {
			System.out.println("ip:" + item.getKey() + " ids:" + item.getValue());
		}
	}

}
