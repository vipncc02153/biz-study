package com.everhomes.learning.demos.distributedLock.zhou.xm;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


/**
 * @author feiyue
 * @className ZkNameLock
 * @description
 * @date 2018/8/20
 **/
public class ZkNameLock implements NameLock, Watcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZkNameLock.class);
    private final ZkBasedCoordinationProvider provider;
    private final String name;
    private int lockAcquireTimeoutSeconds;
    private boolean throwOnError = true;
    // 等待的前一个锁
    private String WAIT_LOCK;
    // 当前锁
    private String CURRENT_LOCK;
    private ZooKeeper zk = null;
    // 根节点
    private String ROOT_LOCK = "/root";

    // 计数器
    private CountDownLatch countDownLatch;

    @Value("${zk.zkUrl}")
    private String config;

    public ZkNameLock(ZkBasedCoordinationProvider provider, String name, int lockAcquireTimeoutSeconds) {
        init();
        this.provider = provider;
        this.name = name;
        this.lockAcquireTimeoutSeconds = lockAcquireTimeoutSeconds;
    }

    private void init(){
        try {
            // 连接zookeeper
            zk = new ZooKeeper(config, lockAcquireTimeoutSeconds, null);
            Stat stat = zk.exists(ROOT_LOCK, false);
            if (stat == null) {
                // 如果根节点不存在，则创建根节点
                zk.create(ROOT_LOCK, "root".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public NameLock throwOnError(boolean value) {
        this.throwOnError = value;
        return this;
    }

    @Override
    public NameLock setLockAcquireTimeoutSeconds(int var1) {
        this.lockAcquireTimeoutSeconds = var1;
        return this;
    }

    @Override
    public <T> Tuple<T, Boolean> enter(Callable<T> var1) {
        CURRENT_LOCK = createLockNode(ROOT_LOCK.concat("/").concat(name));
        try {
            boolean hasTheLock = waitForLock(WAIT_LOCK, lockAcquireTimeoutSeconds);
            if (hasTheLock){
                try {
                    return new Tuple<>(var1.call(), true);
                } catch (Exception e1) {
                    LOGGER.error("Exception when performing callback while holding named lock ",e1);
                }
            }
        } catch (InterruptedException | KeeperException e) {
            LOGGER.error("Exception when performing callback while trying to acquire named lock ",e);
        } finally {
            unlock();
        }
        return new Tuple<>(null,false);
    }

    @Override
    public boolean tryEnter(Runnable var1) {

        // 1 在指定节点创建临时顺序节点
        CURRENT_LOCK = createLockNode(ROOT_LOCK.concat("/").concat(name));
        // 取所有子节点
        List<String> subNodes;
        try {
            subNodes = zk.getChildren(ROOT_LOCK, false);
            List<String> lockObjects = new ArrayList<>();
            for(String node : subNodes){
                String lockName = node.substring(ROOT_LOCK.length() + 1);
                if(name.equals(lockName))
                    lockObjects.add(node);
            }
            Collections.sort(lockObjects);

            // 如果当前节点为最小节点，则获得锁
            if(CURRENT_LOCK.equals(ROOT_LOCK.concat("/").concat(lockObjects.get(0)))){
                try {
                    var1.run();
                    return true;
                }catch (Exception e1){
                    LOGGER.error("Exception when performing callback while holding named lock ", e1);
                }
            }
            // 若不是最小节点，则找到自己的前一个节点
            String prevNode = CURRENT_LOCK.substring(CURRENT_LOCK.lastIndexOf("/") + 1);
            WAIT_LOCK = lockObjects.get(Collections.binarySearch(lockObjects, prevNode) - 1);
        } catch (Exception e2){
            LOGGER.error("try enter exception:{}", e2);
        }finally {
            unlock();
        }
        return false;
    }

    private String createLockNode(String concat) {
        Stat stat = null;
        try {
            stat = zk.exists(ROOT_LOCK, false);
            if(null == stat)
                zk.create(ROOT_LOCK, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            return zk.create(concat, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL );
        }catch (Exception e){
            LOGGER.error("create node exception:{}", e);
            System.out.println(e);
        }
        return null;
    }

    // 等待锁
    private boolean waitForLock(String prev, int waitTime) throws KeeperException, InterruptedException {
        Stat stat = zk.exists(ROOT_LOCK + "/" + prev, true);

        if (stat != null) {
            System.out.println(Thread.currentThread().getName() + "等待锁 " + ROOT_LOCK + "/" + prev);
            this.countDownLatch = new CountDownLatch(1);
            // 计数等待，若等到前一个节点消失，则precess中进行countDown，停止等待，获取锁
            this.countDownLatch.await(waitTime, TimeUnit.MILLISECONDS);
            this.countDownLatch = null;
            System.out.println(Thread.currentThread().getName() + " 等到了锁");
        }
        return true;
    }

    // 节点监视器
    @Override
    public void process(WatchedEvent event) {
        if (this.countDownLatch != null) {
            this.countDownLatch.countDown();
        }
    }

    public void unlock() {
        try {
            System.out.println("释放锁 " + CURRENT_LOCK);
            zk.delete(CURRENT_LOCK, -1);
            CURRENT_LOCK = null;
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
}
