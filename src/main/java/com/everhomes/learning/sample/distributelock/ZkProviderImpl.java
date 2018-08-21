package com.everhomes.learning.sample.distributelock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.management.RuntimeErrorException;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Component
@EnableConfigurationProperties(ZookeeperProperties.class)
public class ZkProviderImpl implements ZkProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZkProviderImpl.class);

    private final ZookeeperProperties properties;
    private ZooKeeper zk;
    private String rootPath;

    public ZkProviderImpl(ZookeeperProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public void init(){
        try {
            rootPath = properties.getRootPath();
            zk = new ZooKeeper(properties.getZkUrl(), properties.getSessionTimeout(), null);
            Stat stat = zk.exists(properties.getRootPath(), false);
            if (stat == null) {
                // 如果根节点不存在，则创建根节点
                zk.create(properties.getRootPath(), null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

        }catch (IOException | InterruptedException | KeeperException e){
            LOGGER.error("Initializing zookeeper failed",e);
        }
    }

    @Override
    public ZooKeeper getZkClient() {
        return this.zk;
    }

    @Override
    public String getRootPath() {
        return this.rootPath;
    }
}
