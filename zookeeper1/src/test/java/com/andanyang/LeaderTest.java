package com.andanyang;

import com.andanyang.zookeeper.ZkClientFactory;
import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author andanyang
 * @since 2023/4/14 16:51
 */
@RunWith(JUnit4.class)
public class LeaderTest {

    private static final String PATH = "/curator-test";
    private static final int CLIENT_QTY = 10;
    CuratorFramework client;

    //@Before
    //public void before() {
    //
    //    ExponentialBackoffRetry retryPolicy =
    //            new ExponentialBackoffRetry(100, 3);
    //    client = ZkClientFactory.createWithOptions(
    //            "192.168.1.13:2181", retryPolicy, null, 3000, 20000);
    //
    //    client.start();
    //}

    public CuratorFramework getClient() {
        ExponentialBackoffRetry retryPolicy =
                new ExponentialBackoffRetry(100, 3);
        CuratorFramework client = ZkClientFactory.createWithOptions(
                "192.168.1.13:2181", retryPolicy, null, 3000, 20000);
        //client.start();
        return client;
    }

    @Test
    public void testLeaderLatch() throws Exception {

        List<CuratorFramework> clients = Lists.newArrayList();

        List<LeaderLatch> examples = Lists.newArrayList();

        try {
            for (int i = 0; i < CLIENT_QTY; i++) {

                CuratorFramework client = getClient();
                clients.add(client);
                LeaderLatch latch = new LeaderLatch(client, PATH, "Client #" + i);
                latch.addListener(new LeaderLatchListener() {

                    @Override
                    public void isLeader() {
                        // TODO Auto-generated method stub
                        System.out.println("I am Leader");
                    }

                    @Override
                    public void notLeader() {
                        // TODO Auto-generated method stub
                        System.out.println("I am not Leader");
                    }
                });
                examples.add(latch);
                client.start();
                latch.start();
            }
            Thread.sleep(1000);
            LeaderLatch currentLeader = null;
            do {
                for (LeaderLatch latch : examples) {
                    if (latch.hasLeadership()) {
                        currentLeader = latch;
                        System.out.println("current leader is " + currentLeader.getId());
                    }
                }
            } while (currentLeader == null);


            System.out.println("release the leader " + currentLeader.getId());
            currentLeader.close();
            //currentLeader.start();

            Thread.sleep(5000);

            for (LeaderLatch latch : examples) {
                if (latch.hasLeadership()) {
                    currentLeader = latch;
                }
            }
            System.out.println("current leader is " + currentLeader.getId());
            System.out.println("release the leader " + currentLeader.getId());
        } finally {
            for (LeaderLatch latch : examples) {
                if (null != latch.getState() && latch.getState() != LeaderLatch.State.CLOSED)
                    CloseableUtils.closeQuietly(latch);
            }
            for (CuratorFramework client : clients) {
                CloseableUtils.closeQuietly(client);
            }
        }
    }


    @Test
    public void testLeaderSelector() throws Exception {
        List<CuratorFramework> clients = Lists.newArrayList();
        List<LeaderSelectorAdapter> examples = Lists.newArrayList();

        try {
            for (int i = 0; i < CLIENT_QTY; i++) {
                CuratorFramework client
                        = getClient();
                clients.add(client);
                LeaderSelectorAdapter selectorAdapter = new LeaderSelectorAdapter(client, PATH, "Client #" + i);
                examples.add(selectorAdapter);
                client.start();
                selectorAdapter.start();
            }
            System.out.println("Press enter/return to quit\n");
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } finally {
            System.out.println("Shutting down...");
            for (LeaderSelectorAdapter exampleClient : examples) {
                CloseableUtils.closeQuietly(exampleClient);
            }
            for (CuratorFramework client : clients) {
                CloseableUtils.closeQuietly(client);
            }
        }

    }
}
