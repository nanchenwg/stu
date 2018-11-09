package com.seckill.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
 
/**
 * 功能介绍: 基础测试类，用于继承
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-redisson.xml"})
@Transactional
public class BaseTest {
    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);
 
    @Test
    public void doBaseTest() {
        LOGGER.info("doBaseTest");
    }

}