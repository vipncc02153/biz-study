package com.everhomes.learning.demos.taskallocation.djm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author djm
 * @date 2018年7月9日
 * @version 1.0
 *          <p>
 *          Description:
 *          在实际应用中有这样一个场景：有m台服务器，有n个任务，需要把这n个任务按一定策略分配给m台服务器来执行，请按以下要求实现该场景：
 *          1、使用List列表代表服务器（列表中每个元素为一个IP地址）； 2、使用List列表代表任务（列表中每个元素为任务ID）；
 *          3、分配的过程就是把IP地址与任务ID映射上的过程；
 *          4、策略要支持多个，如按IP地址随机、按任务数量平分等，策略需要支持可扩展（扩展时不用修改已经实现好的策略）；
 * 
 *          需要按照各自的理解来实现需求，不规定输入和输出的格式。代码中需要附上用来演示的main方法。
 *          </p>
 */
public class TaskAllocationTest {

	public static void main(String[] args) {
		List<Service> serverList = new ArrayList<>();
		List<Task> taskList = new ArrayList<>();

		// 添加服务器
		for (int i = 1; i < 11; i++) {
			Service service = new Service();
			service.setIP("10.1.10.3" + i);
			service.setServiceName("我是第"+i+"台服务器");
			serverList.add(service);
		}
		// 添加任务
		for (int i = 0; i < 500; i++) {
			Task task = new Task();
			task.setTaskId(i + 1);
			task.setTaskName("我是第"+i+"个任务");
			taskList.add(task);
		}

		// 随机分配策略：
		/*AbstractTaskAllocation randomTaskAllocation = new RandomTaskAllocation();
		randomTaskAllocation.doTaskAllocation(serverList, taskList);*/

		// 平均分配任务策略
		AbstractTaskAllocation averageTaskAllocation = new AverageTaskAllocation();
		averageTaskAllocation.doTaskAllocation(serverList, taskList);

	}
}
