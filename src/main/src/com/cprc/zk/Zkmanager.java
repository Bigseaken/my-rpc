package com.cprc.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * Created by withqianqian@163.com on 2017/7/27.
 */
public class Zkmanager {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ZooKeeper zooKeeper;

    private final static String RPC_ZK_SERVER = "/rpc_zk_server";

    private final Zkmanager zkmanager = new Zkmanager();

    private Zkmanager() {
    }

    private Zkmanager getInstance() {
        if (!existNode(RPC_ZK_SERVER))
            createNode();
        try {
            zooKeeper = new ZooKeeper("localhost", 9000, new Watcher() {
                public void process(WatchedEvent watchedEvent) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return zkmanager;
    }

    public void createNode(String path, String data) {
        try {
            if (!StringUtils.isEmpty(data) && !StringUtils.isEmpty(path)) {
                if (!RPC_ZK_SERVER.equals(path)) {
                    path += "/" + path;
                }
                zooKeeper.create(RPC_ZK_SERVER + path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.PERSISTENT);
            } else {
                logger.info("data is null");
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            close();
        }

    }

    private void createNode() {
        createNode("", "netty server -> 127.0.0.1:9090");
    }

    public void removeNode() {
    }

    /**
     * 判断是否存在该节点
     *
     * @param path
     * @return
     */
    public boolean existNode(String path) {
        try {
            Stat stat = zooKeeper.exists(path, true);
            if (stat == null) {
                return Boolean.FALSE;
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return Boolean.TRUE;
    }

    public void close() {
        if (zooKeeper != null) {

            try {
                zooKeeper.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
