package com.andanyang;

import com.andanyang.zookeeper.ZkClientFactory;
import junit.framework.TestCase;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit test for simple App.
 */
@RunWith(JUnit4.class)
public class AppTest
        extends TestCase {

    CuratorFramework client;

    @Before
    public void before() {

        ExponentialBackoffRetry retryPolicy =
                new ExponentialBackoffRetry(100, 3);
        client = ZkClientFactory.createWithOptions(
                "192.168.1.13:2181", retryPolicy, null, 3000, 20000);

        client.start();
    }


    @Test
    public void testCreate() throws Exception {

        String path = "/testcurator/" + System.currentTimeMillis();
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT).forPath(path, "init".getBytes());
        //Thread.sleep(1000);
        System.out.println("success create znode " + path);
    }

    @Test
    public void testDelete() throws Exception {
        client.delete().deletingChildrenIfNeeded().forPath("/testcurator");
    }

    @Test
    public void testUpdate() throws Exception {
        String path = "/zk-test0000000002";
        // 普通更新
        client.setData().forPath(path, "新内容".getBytes());
        // 指定版本更新
        client.setData().withVersion(1).forPath(path);
    }

    @Test
    public void testGet() throws Exception {
        //client.delete().deletingChildrenIfNeeded().forPath("/testcurator");
        System.out.println(System.currentTimeMillis());
        String path = "/zk-test0000000002";
        byte[] bytes = client.getData().forPath(path);
        String s = new String(bytes);
        System.out.println("s = " + s);
        System.out.println(System.currentTimeMillis());

        Stat stat = new Stat();
        byte[] bytes1 = client.getData().storingStatIn(stat).forPath(path);
        s = new String(bytes);
        System.out.println("s = " + s);

        System.out.println("stat = " + stat);
        System.out.println(System.currentTimeMillis());


    }

    @Test
    public void testWatcher() throws Exception {
        String path = "/zk-test0000000002";

        Watcher w = watchedEvent -> System.out.println("监听到的变化 watchedEvent = " + watchedEvent);

        byte[] content = client.getData()
                .usingWatcher(w).forPath(path);
        do {
            Thread.sleep(10000);
        } while (true);
    }
}
