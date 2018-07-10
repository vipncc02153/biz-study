/**
 * <p>Title: AbstractTaskAllocation</p>
 * @author djm
 * @date 2018年7月9日
 * @version 1.0
 * <p>Description: </p>  
 */
package com.everhomes.learning.demos.taskallocation.djm;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <p>
 * Title: AbstractTaskAllocation
 * </p>
 * 
 * @author djm
 * @date 2018年7月9日
 * @version 1.0
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
public abstract class AbstractTaskAllocation {

	/**
	 * 具体的整个过程
	 */
	protected void doTaskAllocation(List<Service> serverList, List<Task> taskList) {
		// 1、准备阶段
		this.preparation(serverList, taskList);
		// 2、把IP地址与任务ID映射上阶段
		Map<Object, String> serviceTaskMapping = this.setMapping(serverList, taskList);
		// 3、结果返回阶段
		this.getResult(serviceTaskMapping);
	}

	/**
	 * 1、准备阶段
	 */
	public abstract void preparation(List<Service> serverList, List<Task> taskList);

	/**
	 * 2、把IP地址与任务ID映射上阶段
	 * 
	 * @param taskList
	 * @param serverList
	 */
	public abstract Map<Object, String> setMapping(List<Service> serverList, List<Task> taskList);

	/**
	 * 3、结果返回阶段
	 */
	public void getResult(Map<Object, String> serviceTaskMapping){
		// 分配状态查询
		for (Entry<Object, String> entrySet : serviceTaskMapping.entrySet()) {
			System.out.println("服务器IP: " + entrySet.getValue() + " === 任务ID: " + entrySet.getKey());
		}
	}
}
