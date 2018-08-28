package com.everhomes.learning.demos.distributedLock.djm;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZKLock implements Watcher {

	private int threadId;
	private ZooKeeper zk = null;
	private String selfPath;
	private String waitPath;
	private String CURRENT_THREAD;
	private static final String GROUP_PATH = "/disLocks";
	private static final String SUB_PATH = "/disLocks/sub";
	private static final String CONNECTION_STRING = "10.1.10.41:2181";

	private static final int THREAD_NUM = 10;
	// 确保连接zk成功；
	private CountDownLatch connectedSemaphore = new CountDownLatch(1);
	// 确保所有线程运行结束；
	private static final CountDownLatch threadSemaphore = new CountDownLatch(THREAD_NUM);

	private static final Logger LOG = LoggerFactory.getLogger(ZKLock.class);

	public ZKLock(int id) {
		this.threadId = id;
		CURRENT_THREAD = "【第" + threadId + "个线程】";
	}

	public static void main(String[] args) {
		for (int i = 0; i < THREAD_NUM; i++) {
			final int threadId = i + 1;
			new Thread() {
				@Override
				public void run() {
					try {
						ZKLock dc = new ZKLock(threadId);
						dc.createZKConnection(CONNECTION_STRING, 1000);
						// GROUP_PATH不存在的话，由一个线程创建即可；

						synchronized (threadSemaphore) {
							dc.createPath(GROUP_PATH, "该节点由线程" + threadId + "创建", true);
						}
						dc.getLock();
					} catch (Exception e) {
						LOG.error("【第" + threadId + "个线程】 抛出的异常：");
						e.printStackTrace();
					}
				}
			}.start();
		}
		try {
			threadSemaphore.await();
			LOG.info("所有线程运行结束!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建节点
	 * 
	 * @param path
	 *            节点path
	 * @param data
	 *            初始数据内容
	 * @return
	 */
	public boolean createPath(String path, String data, boolean needWatch)
			throws KeeperException, InterruptedException {
		if (zk.exists(path, needWatch) == null) {
			LOG.info(CURRENT_THREAD + "节点创建成功, Path: "
					+ this.zk.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)
					+ ", content: " + data);
		}
		return true;
	}

	/**
	 * 创建ZK连接
	 */
	public void createZKConnection(String connectString, int sessionTimeout) throws IOException, InterruptedException {
		zk = new ZooKeeper(connectString, sessionTimeout, this);
		connectedSemaphore.await();
	}

	/**
	 * 获取锁
	 */
	private void getLock() throws KeeperException, InterruptedException {
		try {
			selfPath = zk.create(SUB_PATH, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		} catch (KeeperException e) {
			e.printStackTrace();
		}

		LOG.info(CURRENT_THREAD + "创建锁路径:" + selfPath);

		if (checkMinPath()) {
			// 如果是最小节点获得锁
			getLockSuccess();
		}
	}

	/**
	 * 获取锁成功
	 */
	public void getLockSuccess() throws KeeperException, InterruptedException {
		if (zk.exists(this.selfPath, false) == null) {
			LOG.error(CURRENT_THREAD + "本节点已不在了...");
			return;
		}
		LOG.info(CURRENT_THREAD + "获取锁成功！");

		// 处理业务逻辑
		Thread.sleep(2000);
		unlock();
	}

	/**
	 * 释放锁
	 */
	public void unlock() {

		LOG.info(CURRENT_THREAD + "删除本节点：" + selfPath);

		try {
			zk.delete(this.selfPath, -1);

			releaseConnection();
			threadSemaphore.countDown();

			System.out.println(CURRENT_THREAD + "节点的锁已经释放了！");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭ZK连接
	 */
	public void releaseConnection() {
		if (this.zk != null) {
			try {
				this.zk.close();
			} catch (InterruptedException e) {
			}
		}
		LOG.info(CURRENT_THREAD + "释放连接");
	}

	/**
	 * 检查自己是不是最小的节点
	 * 
	 * @return
	 */
	public boolean checkMinPath() throws KeeperException, InterruptedException {
		List<String> subNodes = zk.getChildren(GROUP_PATH, false);
		Collections.sort(subNodes);
		int index = subNodes.indexOf(selfPath.substring(GROUP_PATH.length() + 1));
		switch (index) {
		case -1: {
			LOG.error(CURRENT_THREAD + "节点已不在了..." + selfPath);
			return false;
		}
		case 0: {
			LOG.info(CURRENT_THREAD + "节点可以获得锁了" + selfPath);
			return true;
		}
		default: {
			this.waitPath = GROUP_PATH + "/" + subNodes.get(index - 1);
			LOG.info(CURRENT_THREAD + "获取子节点中，排在我前面的" + waitPath);
			try {
				zk.getData(waitPath, true, new Stat());
				return false;
			} catch (KeeperException e) {
				if (zk.exists(waitPath, false) == null) {
					LOG.info(CURRENT_THREAD + "子节点中，排在前面的" + waitPath + "已丢失");
					return checkMinPath();
				} else {
					throw e;
				}
			}
		}
		}
	}

	@Override
	public void process(WatchedEvent event) {
		if (event == null) {
			return;
		}

		Event.KeeperState keeperState = event.getState();
		Event.EventType eventType = event.getType();

		if (Event.KeeperState.SyncConnected == keeperState) {
			if (Event.EventType.None == eventType) {
				LOG.info(CURRENT_THREAD + "成功连接ZK服务器");
				connectedSemaphore.countDown();
			} else if (event.getType() == Event.EventType.NodeDeleted && event.getPath().equals(waitPath)) {
				LOG.info(CURRENT_THREAD + "排前面的节点丢失，判断该节点是否可以获得锁？");
				try {
					if (checkMinPath()) {
						getLockSuccess();
					}
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else if (Event.KeeperState.Disconnected == keeperState) {
			LOG.info(CURRENT_THREAD + "与ZK服务器断开连接");
		} else if (Event.KeeperState.AuthFailed == keeperState) {
			LOG.info(CURRENT_THREAD + "权限检查失败");
		} else if (Event.KeeperState.Expired == keeperState) {
			LOG.info(CURRENT_THREAD + "会话失效");
		}
	}
}
