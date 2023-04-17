package com.andanyang;

import com.andanyang.zookeeper.ZkClientFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMultiLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author andanyang
 * @since 2023/4/17 15:31
 */
public class MultiSharedLockDemo {

    private static final String PATH1 = "/examples/locks1";
    private static final String PATH2 = "/examples/locks2";

    private static final String PATH = "/curator-test";
    static CuratorFramework client;

    public static void main(String[] args) throws Exception {


        before();

        InterProcessLock lock1 = new InterProcessMutex(client, PATH1);
        InterProcessLock lock2 = new InterProcessSemaphoreMutex(client, PATH2);

        InterProcessMultiLock lock = new InterProcessMultiLock(Arrays.asList(lock1, lock2));

        if (!lock.acquire(10, TimeUnit.SECONDS)) {
            throw new IllegalStateException("could not acquire the lock");
        }
        System.out.println("has got all lock");

        System.out.println("has got lock1: " + lock1.isAcquiredInThisProcess());
        System.out.println("has got lock2: " + lock2.isAcquiredInThisProcess());

        try {
            //access resource exclusively
            System.out.println("lock = " + lock);
            Thread.sleep(1000);
        } finally {
            System.out.println("releasing the lock");
            lock.release(); // always release the lock in a finally block
        }
        System.out.println("has got lock1: " + lock1.isAcquiredInThisProcess());
        System.out.println("has got lock2: " + lock2.isAcquiredInThisProcess());
    }

    public static void before() {

        ExponentialBackoffRetry retryPolicy =
                new ExponentialBackoffRetry(100, 3);
        client = ZkClientFactory.createWithOptions(
                "192.168.1.13:2181", retryPolicy, null, 3000, 20000);

        client.start();
    }
}
