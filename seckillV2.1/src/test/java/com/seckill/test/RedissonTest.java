package com.seckill.test;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

public class RedissonTest extends BaseTest {
    @Autowired
    private RedissonClient redissonClient;
 
 
    @Test
    public void testLock() throws Exception {
        Thread t1 = new MyThread();
        Thread t2 = new MyThread();
        t1.setName("t1");
        t2.setName("t2");
        t1.start();
        t2.start();
        //Thread.sleep(100 * 1000);
        Thread.sleep(1000);
    }
 
    public class MyThread extends Thread {
        @Override
        public void run() {
            try {
                LOGGER.info(getName() + "run start");
                RLock rLock = redissonClient.getLock("testLcok");
                if (rLock.tryLock(5, TimeUnit.SECONDS)) {
                    LOGGER.info(getName() + "get lock");
                    //Thread.sleep(10 * 1000);
                    Thread.sleep(1000);
                }
                LOGGER.info(getName() + "run over");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
 

}