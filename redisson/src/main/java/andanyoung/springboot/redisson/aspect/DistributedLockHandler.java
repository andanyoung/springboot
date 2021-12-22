package andanyoung.springboot.redisson.aspect;

import andanyoung.springboot.redisson.annotation.DistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁解析器
 * https://github.com/redisson/redisson/wiki/8.-%E5%88%86%E5%B8%83%E5%BC%8F%E9%94%81%E5%92%8C%E5%90%8C%E6%AD%A5%E5%99%A8
 *
 * @author andanyang
 * @since 2021/12/22 11:06
 */
@Slf4j
@Aspect
@Component
public class DistributedLockHandler {

    @Autowired
    RedissonClient redissonClient;

    /**
     * 切面环绕通知
     *
     * @param joinPoint       ProceedingJoinPoint
     * @param distributedLock DistributedLock
     * @return Object
     */
    @Around("@annotation(distributedLock)")
    public Object around(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {
        log.debug("[开始]执行RedisLock环绕通知,获取Redis分布式锁开始");

        //获取超时时间并获取锁
        RLock lock = getLock(distributedLock);
        if (distributedLock.waitTimeOutSeconds() > 0) {
            boolean res = lock.tryLock(distributedLock.waitTimeOutSeconds(), distributedLock.expireSeconds(), TimeUnit.SECONDS);
            Assert.isTrue(res, "TRYLOCK WAIT TIMEOUT");
        } else {
            lock.lock(distributedLock.expireSeconds(), TimeUnit.SECONDS);
        }

        log.debug("获取Redis分布式锁[成功]，加锁完成，开始执行业务逻辑...");
        try {
            return joinPoint.proceed();
        } finally {
            lock.unlock();
            log.debug("释放Redis分布式锁[成功]，解锁完成，结束业务逻辑...");
        }
    }

    /**
     * 根据注解获取分布式锁
     *
     * @param distributedLock
     * @return
     */
    private RLock getLock(DistributedLock distributedLock) {

        String lockName = distributedLock.value();
        switch (distributedLock.lockModel()) {
            case FAIR:
                //公平锁
                return redissonClient.getFairLock(lockName);
            case READ:
                //读之前加读锁，读锁的作用就是等待该lockkey释放写锁以后再读
                RReadWriteLock readLock = redissonClient.getReadWriteLock(lockName);
                return readLock.readLock();
            case WRITE:
                //写之前加写锁，写锁加锁成功，读锁只能等待
                RReadWriteLock writeLock = redissonClient.getReadWriteLock(lockName);
                return writeLock.writeLock();
            case REENTRANT:
            default:
                //可重入锁
                return redissonClient.getLock(lockName);
        }
    }
}
