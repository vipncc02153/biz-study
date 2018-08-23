package com.everhomes.learning.sample.distributelock;

import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ZkNamedLock  implements  NamedLock{

    private static final Logger LOGGER = LoggerFactory.getLogger(ZkNamedLock.class);
    private final String name;
    private int lockAcquireTimeoutSeconds;
    private String currentLockPath;
    private boolean throwOnError = true;
    private String rootPath;
    private ZooKeeper zooKeeper;

    public ZkNamedLock(ZkProvider zkProvider,String name, int lockAcquireTimeoutSeconds) {
        this.name = name;
        this.lockAcquireTimeoutSeconds = lockAcquireTimeoutSeconds;

        this.rootPath = zkProvider.getRootPath();
        this.zooKeeper = zkProvider.getZkClient();
    }


    @Override
    public NamedLock throwOnError(boolean value) {
        this.throwOnError = value;
        return this;
    }

    @Override
    public NamedLock setLockAcquireTimeoutSeconds(int lockAcquireTimeoutSeconds) {
        this.lockAcquireTimeoutSeconds = lockAcquireTimeoutSeconds;
        return this;
    }

    @Override
    public <T> Tuple<T, Boolean> enter(Callable<T> callback) {
        currentLockPath = createLockNode(rootPath.concat("/").concat(name));
        if (StringUtils.isBlank(currentLockPath))
            return new Tuple<>(null,false);
        try {
            boolean hasTheLock = waitToLock();
            if (hasTheLock){
                try {
                    return new Tuple<>(callback.call(),true);
                } catch (Exception e1) {
                    LOGGER.error("Exception when performing callback while holding named lock ",e1);
                    if (this.throwOnError)
                        throw new RuntimeException(e1);
                }
            }
        } catch (InterruptedException | KeeperException e) {
            LOGGER.error("Exception when performing callback while trying to acquire named lock ",e);
            if (this.throwOnError)
                throw new RuntimeException(e);
        } finally {
            unlock();
        }
        return new Tuple<>(null,false);
    }

    @Override
    public boolean tryEnter(Runnable callback) {
        currentLockPath = createLockNode(rootPath.concat("/").concat(name));
        if (StringUtils.isBlank(currentLockPath))
            return false;
        try{
            String preNode = findPreNode();
            if (preNode == null){
                try {
                    callback.run();
                    return true;
                }catch (Exception e1){
                    LOGGER.error("Exception when performing callback while holding named lock ",e1);
                    if (this.throwOnError)
                        throw new RuntimeException(e1);
                }
            }
        } catch (InterruptedException | KeeperException e) {
            LOGGER.error("Exception when trying to find preNode ",e);
            if (this.throwOnError)
                throw new RuntimeException(e);
        } finally {
            unlock();
        }
        return false;
    }

    /**
     * 等待锁直至超时
     * @return
     */
    private boolean waitToLock() throws KeeperException, InterruptedException {
        boolean haveTheLock = false;
        Long tryBeginTime = System.currentTimeMillis();
        while (!haveTheLock){
            String preNode = findPreNode();
            haveTheLock = preNode == null;
            if (!haveTheLock){
                String previousSequencePath = rootPath.concat("/").concat(preNode);
                final CountDownLatch latch = new CountDownLatch(1);
                final Watcher previousListener = new Watcher() {
                    public void process(WatchedEvent event) {
                        if (event.getType() == Event.EventType.NodeDeleted) {
                            latch.countDown();
                        }
                    }
                };

                zooKeeper.exists(previousSequencePath, previousListener);

                if (lockAcquireTimeoutSeconds > 0 ){
                    Long waitingTime = System.currentTimeMillis() - tryBeginTime;
                    if (waitingTime > lockAcquireTimeoutSeconds){
                        break;
                    }
                    latch.await(lockAcquireTimeoutSeconds, TimeUnit.MICROSECONDS);
                }else {
                    latch.await();
                }
            }
        }
        return haveTheLock;
    }

    /**
     * createLockNode用于在locker（basePath持久节点）下创建客户端要获取锁的[临时]顺序节点
     * @param path
     * @return
     */
    private String createLockNode(String path) {
        Stat stat = null;
        try {
            stat = zooKeeper.exists(rootPath, false);
            // 判断一下根目录是否存在
            if (stat == null) {
                zooKeeper.create(rootPath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            return zooKeeper.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        } catch (KeeperException | InterruptedException e) {
            LOGGER.error("createLockNode error exception = {}", e);
            if (this.throwOnError)
                throw new RuntimeException(e);
        }
        return null;
    }

    private void unlock(){
        try {
            zooKeeper.delete(currentLockPath, -1);
        } catch (Exception e) {
            LOGGER.error("unLock error", e);
            if (this.throwOnError)
                throw new RuntimeException(e);
        }
    }

    /**
     * 寻找当前节点的前一个节点 如果没有 说明当前节点是最小节点 返回Null
     * @return
     */
    private String findPreNode() throws KeeperException, InterruptedException {
        List<String> children = getSortedChildren();
        String sequenceNodeName = currentLockPath.substring(rootPath.length() + 1);
        int ourIndex = children.indexOf(sequenceNodeName);
        if (ourIndex < 0) {
            LOGGER.error("not find node:{}", sequenceNodeName);
            throw new RuntimeException("节点没有找到: " + sequenceNodeName);
        }

        return ourIndex == 0 ? null : children.get(ourIndex - 1);
    }

    /**
     * 取得锁的排序列表
     *
     * @return
     */
    private List<String> getSortedChildren() throws KeeperException, InterruptedException {
        List<String> children = null;
        children = zooKeeper.getChildren(rootPath, false);
        if (children != null && !children.isEmpty()) {
            Collections.sort(children, String::compareTo);
        }
        return children;
    }


}
