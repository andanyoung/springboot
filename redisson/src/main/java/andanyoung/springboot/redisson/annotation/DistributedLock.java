package andanyoung.springboot.redisson.annotation;

import andanyoung.springboot.redisson.constant.LockModel;

import java.lang.annotation.*;

/**
 * 基于 Redisson分布式锁注解
 *
 * @author andanyang
 * @since 2021/12/22 11:05
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DistributedLock {

    /**
     * REENTRANT(可重入锁),FAIR(公平锁),MULTIPLE(联锁),REDLOCK(红锁),READ(读锁), WRITE(写锁),
     * AUTO(自动模式,当参数只有一个.使用 REENTRANT 参数多个 MULTIPLE)
     */
    LockModel lockModel() default LockModel.REENTRANT;

    /**
     * 分布式锁名称
     *
     * @return String
     */
    String value() default "";

    /**
     * 锁超时时间,默认三十秒
     * If expireSeconds is -1, hold the lock until explicitly unlocked.
     *
     * @return int
     */
    int expireSeconds() default 30;

    /**
     * 最多等待x秒
     * If waitSeconds <0 0, no wait TimeOut
     *
     * @return
     */
    int waitTimeOutSeconds() default 0;
}
