/**
 * <p>Title: RandomTaskAllocation</p>
 * @author djm
 * @date 2018年7月9日
 * @version 1.0
 * <p>Description: </p>  
 */
package com.everhomes.learning.demos.taskallocation.djm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * <p>
 * Title: RandomTaskAllocation
 * </p>
 * 
 * @author djm
 * @date 2018年7月9日
 * @version 1.0
 *          <p>
 *          Description:
 *          </p>
 */
public class RandomTaskAllocation extends AbstractTaskAllocation {

	// 进行一些参数校验之类的
	@Override
	public void preparation(List<Service> serverList, List<Task> taskList) {
		if (serverList == null || serverList.size() < 1) {
			System.out.println("没有可用的服务器！");
		}
		if (taskList == null || taskList.size() < 1) {
			System.out.println("暂无需要处理的任务！");
		}
	}

	// 设置服务器和任务之间的隐射关系
	@Override
	public Map<Object, String> setMapping(List<Service> serverList, List<Task> taskList) {

		// map key_value 的形式存储服务器和任务的映射关系 id : ip
		Map<Object, String> serviceTaskMapping = new HashMap<Object, String>();
		// 设置的是随机事件 0或者1 各50%，保证随机分配性
		// 服务器的数量，就是任务分配的概率
		int serverCount = serverList.size();
		Random r = new Random();
		for (int i = 0; i < taskList.size(); i++) {
			Integer serverId = r.nextInt(serverCount);
			serviceTaskMapping.put(taskList.get(i).getTaskId(), serverList.get(serverId).getIP());
		}

		return serviceTaskMapping;
	}
	/*@Override
	public void getResult(Map<Object, String> serviceTaskMapping) {
		// 分配狀態
		for (Entry<Object, String> entrySet : serviceTaskMapping.entrySet()) {
			System.out.println("服务器IP: " + entrySet.getValue() + " -- 任务ID: " + entrySet.getKey());
		}
	}*/
}
