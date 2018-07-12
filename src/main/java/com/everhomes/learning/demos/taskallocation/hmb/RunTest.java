package com.everhomes.learning.demos.taskallocation.hmb;

import java.util.ArrayList;
import java.util.List;

public class RunTest {

	public static void main(String[] args) {

		int m = 11; // 服务器个数
		int n = 1000; // 任务个数
 
		// 输入
		List<String> servers = getSevers(m);
		List<Long> tasks = getTasks(n);
		TaskDeal deal = new TaskDeal(servers, tasks);

		// 执行
		deal.runWithStrategy(TaskDeal.BY_COUNT_AVERAGE);

		// 输出
		deal.print();
	}

	private static List<Long> getTasks(int num) {

		int base = 100;
		List<Long> tasks = new ArrayList<>(num);
		for (long i = 0; i < num; i++) {
			tasks.add(base + i);
		}
		return tasks;
	}

	private static List<String> getSevers(int num) {

		int base = 10000;

		List<String> severs = new ArrayList<>(num);
		for (int i = 0; i < num; i++) {
			severs.add(toIp(base + i));
		}

		return severs;
	}

	private static String toIp(int number) {
		String part4 = (number & 0xF) + "";
		String part3 = ((number >>> 4) & 0xF) + "";
		String part2 = ((number >>> 8) & 0xF) + "";
		String part1 = ((number >>> 12) & 0xF) + "";
		return part1 + "." + part2 + "." + part3 + "." + part4;
	}

}
