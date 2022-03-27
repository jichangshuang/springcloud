package com.chinasoft.zookeeper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.zookeeper.*;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

public class BaseZookeeper {

    private ZooKeeper zooKeeper;

    /**
     * 超时时间
     */
    private static final int SESSION_TIME_OUT = 2000;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

//    @Override
//    public void process(WatchedEvent watchedEvent) {
//
//        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
//            System.out.println("Watch recieved event");
//            try {
//                countDownLatch.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }

    /**
     * 连接zookeeper
     *
     * @param host
     * @throws InterruptedException
     * @throws IOException
     */
    public void connectZookeeper(String host) throws InterruptedException, IOException {
        zooKeeper = new ZooKeeper(host, SESSION_TIME_OUT, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getType() == Event.EventType.None && watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    System.out.println("连接已建立");
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
        System.out.println("zookeeper connect success.");
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        BaseZookeeper bz = new BaseZookeeper();
        bz.connectZookeeper("localhost:2181");
        System.out.println();

        MyConfig myConfig = new MyConfig();
        myConfig.setKey("anyKey");
        myConfig.setName("anyName");
        byte[] bytes1 = "jcs".getBytes(StandardCharsets.UTF_8);

        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = objectMapper.writeValueAsBytes(myConfig);
        bz.zooKeeper.create("/myConfig", bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        bz.zooKeeper.getData("/myConfig", new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getType() == Event.EventType.NodeDataChanged && watchedEvent.getPath() != null && watchedEvent.getPath().equals("/myConfig")) {
                    System.out.println("Path:" +watchedEvent.getPath()+"数据发生了变化.");

                    MyConfig myConfig1 = null;
                    try {
                        byte[] data = bz.zooKeeper.getData("/myConfig", this, null);
                        myConfig1 = objectMapper.readValue(new String(data), MyConfig.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    }
                    System.out.println("变化后的数据为:"+myConfig1);
                }

            }
        }, null);
    }


}
