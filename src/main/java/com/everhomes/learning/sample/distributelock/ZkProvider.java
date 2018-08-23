package com.everhomes.learning.sample.distributelock;

import org.apache.zookeeper.ZooKeeper;

public interface ZkProvider {

    ZooKeeper getZkClient();

    String getRootPath();
}
