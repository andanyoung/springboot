package andanyoung.springboot.redisson.controller;

import andanyoung.springboot.redisson.annotation.DistributedLock;
import andanyoung.springboot.redisson.constant.LockModel;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author andanyang
 * @since 2021/12/22 10:38
 */
@RestController
public class LockController {

    @Autowired
    private RedissonClient redissonClient;

    @GetMapping(value = "/redisson/{key}")
    public String getLock(@PathVariable("key") String lockKey) {

        RLock lock = redissonClient.getLock(lockKey);
        try {
            lock.lock();
            Thread.sleep(10000);
        } catch (Exception e) {
            return e.getMessage();
        } finally {
            lock.unlock();
        }
        return "已解锁" + LocalDateTime.now();
    }

    @GetMapping(value = "/getLockREENTRANT/{key}")
    @DistributedLock("getLockREENTRANT")
    public String getLockREENTRANT(@PathVariable("key") String lockKey) throws InterruptedException {
        Thread.sleep(10000);
        return "I get REENTRANT lock" + LocalDateTime.now();
    }

    @GetMapping(value = "/getLockFAIR/{key}")
    @DistributedLock(value = "getLockFAIR", lockModel = LockModel.FAIR)
    public String getLockFAIR(@PathVariable("key") String lockKey) throws InterruptedException {
        Thread.sleep(10000);
        return "I get FAIR lock" + LocalDateTime.now();
    }

    @GetMapping(value = "/getLockREAD/{key}")
    @DistributedLock(value = "RReadWriteLock", lockModel = LockModel.READ)
    public String getLockFREAD(@PathVariable("key") String lockKey) throws InterruptedException {
        System.out.println("I get READ lock" + LocalDateTime.now() + "Thread id" + Thread.currentThread().getId());
        Thread.sleep(10000);
        return "I get READ lock" + LocalDateTime.now();
    }

    @GetMapping(value = "/getLockWRITE/{key}")
    @DistributedLock(value = "RReadWriteLock", lockModel = LockModel.WRITE)
    public String getLockWRITE(@PathVariable("key") String lockKey) throws InterruptedException {
        System.out.println("I get READ lock" + LocalDateTime.now() + "Thread id" + Thread.currentThread().getId());
        Thread.sleep(10000);
        return "I get WRITE lock" + LocalDateTime.now();
    }

    @GetMapping(value = "/el/{key}")
    @DistributedLock("#lockKey")
    public String el(@PathVariable("key") String lockKey) throws InterruptedException {
        Thread.sleep(10000);
        return "I get REENTRANT lock" + LocalDateTime.now();
    }
}
