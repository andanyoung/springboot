package com.andanyang;

import com.andanyang.zookeeper.ZkClientFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author andanyang
 * @since 2023/4/14 16:13
 */
@RunWith(JUnit4.class)
public class CacheTest {


    private static final String PATH = "/curator-test";
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
    public void testPathCache() throws Exception {


        PathChildrenCache cache = new PathChildrenCache(client, PATH, true);
        cache.start();
        PathChildrenCacheListener cacheListener = (client1, event) -> {
            System.out.println("事件类型：" + event.getType());
            ChildData data = event.getData();
            if (null != data) {
                System.out.println("节点数据：" + data.getPath() + " = " + new String(data.getData()));
            }
        };
        cache.getListenable().addListener(cacheListener);
        client.create().creatingParentsIfNeeded().forPath(PATH + "/test01", "01".getBytes());
        Thread.sleep(10);
        client.create().creatingParentsIfNeeded().forPath(PATH + "/test02", "02".getBytes());
        Thread.sleep(10);
        client.setData().forPath(PATH + "/test01", "01_V2".getBytes());
        Thread.sleep(10);
        for (ChildData data : cache.getCurrentData()) {
            System.out.println("getCurrentData:" + data.getPath() + " = " + new String(data.getData()));
        }
        client.delete().forPath(PATH + "/test01");
        Thread.sleep(10);
        client.delete().forPath(PATH + "/test02");
        Thread.sleep(1000 * 5);
        cache.close();
        client.close();
        System.out.println("OK!");
    }

    @Test
    public void testNodeCache() throws Exception {

        client.create().creatingParentsIfNeeded().forPath(PATH);
        final NodeCache cache = new NodeCache(client, PATH);
        NodeCacheListener listener = () -> {
            ChildData data = cache.getCurrentData();
            if (null != data) {
                System.out.println("节点数据：" + new String(data.getData()));
            } else {
                System.out.println("节点被删除!");
            }
        };
        cache.getListenable().addListener(listener);
        cache.start();
        client.setData().forPath(PATH, "01".getBytes());
        Thread.sleep(100);
        client.setData().forPath(PATH, "02".getBytes());
        Thread.sleep(100);
        client.delete().deletingChildrenIfNeeded().forPath(PATH);
        Thread.sleep(1000 * 2);
        cache.close();
        client.close();
        System.out.println("OK!");
    }

    @Test
    public void testTreeCache() throws Exception {


        client.create().creatingParentsIfNeeded().forPath(PATH);

        TreeCache cache = new TreeCache(client, PATH);
        TreeCacheListener listener = (client1, event) -> {
            byte[] bytes = client.getData().forPath(PATH);
            System.out.println("bytes = " + bytes);
            System.out.println("事件类型：" + event.getType() +
                    " | 路径：" + (null != event.getData() ? event.getData().getPath() : null));
        };
        cache.getListenable().addListener(listener);
        cache.start();
        client.setData().forPath(PATH, "01".getBytes());
        Thread.sleep(100);
        client.setData().forPath(PATH, "02".getBytes());
        Thread.sleep(100);

        client.create().forPath(PATH + "/SubTree", "SubTree".getBytes());
        client.setData().forPath(PATH + "/SubTree", "00PATHSubTree".getBytes());
        Thread.sleep(100);
        client.delete().deletingChildrenIfNeeded().forPath(PATH);
        Thread.sleep(1000 * 2);
        cache.close();
        client.close();
        System.out.println("OK!");
    }
}
