package com.andanyang.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;

/**
 * @author andanyang
 * @since 2023/4/14 13:39
 */
public class ZkClientFactory {

    /**
     * @param connectionString    zk的连接地址
     * @param retryPolicy         重试策略
     * @param connectionTimeoutMs 连接
     * @param sessionTimeoutMs
     * @return CuratorFramework 实例
     */
    public static CuratorFramework createWithOptions(
            String connectionString,
            RetryPolicy retryPolicy,
            String namespace,
            int connectionTimeoutMs,
            int sessionTimeoutMs) {

        // builder 模式创建 CuratorFramework 实例
        return CuratorFrameworkFactory.builder()
                .connectString(connectionString)
                .retryPolicy(retryPolicy)
                .connectionTimeoutMs(connectionTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs)
                .namespace(namespace)
                // 其他的创建选项
                .build();
    }
}
