package com.andanyang;

import com.andanyang.zookeeper.ZkClientFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author andanyang
 * @since 2023/4/14 17:16
 */
@RunWith(JUnit4.class)
public class LockTest {

    private static final int QTY = 5;
    private static final String PATH = "/curator/LOCK";
    CuratorFramework client;

    @Before
    public void before() {

        ExponentialBackoffRetry retryPolicy =
                new ExponentialBackoffRetry(100, 3);
        client = ZkClientFactory.createWithOptions(
                "192.168.1.13:2181,192.168.1.13:2182,192.168.1.13:2183", retryPolicy, null, 3000, 20000);

        client.start();
    }

    @Test
    public void testShareLock1() throws Exception {

        InterProcessMutex lock = new InterProcessMutex(client, PATH);

        lock.acquire();
        Thread.sleep(10000);
        System.out.println(PATH + " get the lock");
    }

    @Test
    public void testShareLock2() throws Exception {

        InterProcessMutex lock = new InterProcessMutex(client, PATH);

        boolean acquire = lock.acquire(-1, TimeUnit.SECONDS);
        Thread.sleep(10000);
        System.out.println(2 + " get the lock " + acquire);
    }

    private InterProcessMutex createLock() {
        return new InterProcessMutex(client, PATH);
    }


    @Test
    public void testShareLock() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(QTY);
        ExecutorService service = Executors.newFixedThreadPool(QTY);
        List<InterProcessMutex> locks = new ArrayList<>();
        for (int i = 0; i < QTY; ++i) {
            int finalI = i;
            Runnable runnable = () -> {
                InterProcessMutex lock = createLock();
                locks.add(lock);

                boolean acquire = false;
                try {
                    lock.acquire(10000, TimeUnit.SECONDS);
                    System.out.println("acquire Lock and run  " + finalI + " time:" + System.currentTimeMillis());
                    Thread.sleep(10000);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        lock.release();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                System.out.println("acquire Lock end : " + finalI + " time:" + System.currentTimeMillis());
                countDownLatch.countDown();
            };


            service.execute(runnable);
            //service.execute(runnable);
        }

        countDownLatch.await();
    }

    private InterProcessSemaphoreMutex createInterProcessSemaphoreMutexLock() {
        return new InterProcessSemaphoreMutex(client, PATH);
    }

    @Test
    public void testInterProcessSemaphoreMutex() throws InterruptedException {


        CountDownLatch countDownLatch = new CountDownLatch(QTY);

        ExecutorService service = Executors.newFixedThreadPool(QTY);
        List<InterProcessSemaphoreMutex> locks = new ArrayList<>();
        for (int i = 0; i < QTY; ++i) {
            int finalI = i;
            Runnable runnable = () -> {
                InterProcessSemaphoreMutex lock = createInterProcessSemaphoreMutexLock();
                locks.add(lock);

                boolean acquire = false;
                try {
                    lock.acquire(10000, TimeUnit.SECONDS);
                    //lock.acquire(10000, TimeUnit.SECONDS);
                    System.out.println("acquire Lock and run  " + finalI + " time:" + System.currentTimeMillis());
                    Thread.sleep(10000);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        lock.release();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                System.out.println("acquire Lock end : " + finalI + " time:" + System.currentTimeMillis());
                countDownLatch.countDown();
            };


            service.execute(runnable);
            service.execute(runnable);
        }

        countDownLatch.await();
    }

    @Test
    public void testReadWriteLock() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(QTY);

        ExecutorService service = Executors.newFixedThreadPool(QTY);
        List<InterProcessReadWriteLock> locks = new ArrayList<>();
        for (int i = 0; i < QTY; ++i) {
            int finalI = i;
            Runnable runnable = () -> {
                InterProcessReadWriteLock lock = new InterProcessReadWriteLock(client, PATH);
                InterProcessReadWriteLock.ReadLock readLock = lock.readLock();
                InterProcessReadWriteLock.WriteLock writeLock = lock.writeLock();
                locks.add(lock);

                boolean acquire = false;
                try {
                    if (readLock.acquire(-1, TimeUnit.SECONDS)) {
                        System.out.println("acquire readLock and run  " + finalI + " time:" + System.currentTimeMillis());
                        readLock.release();
                        Thread.sleep(1000);
                        System.out.println("acquire readLock end  " + finalI + " time:" + System.currentTimeMillis());
                    }
                    if (finalI / 2 == 0 && writeLock.acquire(-1, TimeUnit.SECONDS)) {
                        System.out.println("acquire writeLock and run  " + finalI + " time:" + System.currentTimeMillis());
                        Thread.sleep(1000);
                        System.out.println("acquire writeLock end  " + finalI + " time:" + System.currentTimeMillis());
                    }
                    System.out.println("acquire Lock and run  " + finalI + " time:" + System.currentTimeMillis());
                    Thread.sleep(10000);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                System.out.println("acquire Lock end : " + finalI + " time:" + System.currentTimeMillis());
                countDownLatch.countDown();
            };


            service.execute(runnable);
            //service.execute(runnable);
        }

        countDownLatch.await();
    }


    @Test
    public void testInterProcessSemaphore() throws Exception {
        InterProcessSemaphoreV2 interProcessSemaphoreV2 = new InterProcessSemaphoreV2(client, PATH, QTY);

        ExecutorService service = Executors.newFixedThreadPool(QTY);
        CountDownLatch countDownLatch = new CountDownLatch(2);


        Runnable runnable1 = () -> {
            Collection<Lease> acquire = null;
            try {
                acquire = interProcessSemaphoreV2.acquire(2, 1000000, TimeUnit.SECONDS);
                System.out.println("acquire = runnable1" + acquire);
                Thread.sleep(10000);

                interProcessSemaphoreV2.returnAll(acquire);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            countDownLatch.countDown();
        };

        Runnable runnable2 = () -> {
            Collection<Lease> acquire = null;
            try {
                acquire = interProcessSemaphoreV2.acquire(1, 1000000, TimeUnit.SECONDS);
                System.out.println("acquire = runnable2" + acquire);
                Thread.sleep(10000);
                interProcessSemaphoreV2.returnAll(acquire);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            countDownLatch.countDown();
        };

        Runnable runnable3 = () -> {
            Collection<Lease> acquire = null;
            try {
                acquire = interProcessSemaphoreV2.acquire(QTY, 1000000, TimeUnit.SECONDS);
                System.out.println("acquire = runnable3" + acquire);
                Thread.sleep(10000);
                interProcessSemaphoreV2.returnAll(acquire);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            countDownLatch.countDown();
        };


        service.execute(runnable1);
        Thread.sleep(1000);
        service.execute(runnable2);
        Thread.sleep(1000);
        service.execute(runnable3);

        countDownLatch.await();
    }
}
