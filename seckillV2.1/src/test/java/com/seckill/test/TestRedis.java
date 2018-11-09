package com.seckill.test;

import java.io.IOException;

import org.junit.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seckill.common.redisLock.RedissonLockUtil;
import com.seckill.pojo.SuccessKill;

public class TestRedis {
    @Test
    public void test() throws InterruptedException {
        ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext-redis.xml");
        RedisTemplate<String, Object> redisTemplate = appCtx.getBean("redisTemplate",RedisTemplate.class);
        //添加一个 key 
        ValueOperations<String, Object> value = redisTemplate.opsForValue();
        value.set("lp", "hello word");
        //获取 这个 key 的值
        System.out.println(value.get("lp"));
        
        
        /*//添加 一个 hash集合
        HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("name", "lp");
        map.put("age", "26");
        hash.putAll("lpMap", map);
        //获取 map
        System.out.println(hash.entries("lpMap"));
        //添加 一个 list 列表
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush("lpList", "lp");
        list.rightPush("lpList", "26");
        //输出 list
        System.out.println(list.range("lpList", 0, 1));
        //添加 一个 set 集合
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add("lpSet", "lp");
        set.add("lpSet", "26");
        set.add("lpSet", "178cm");
        //输出 set 集合
        System.out.println(set.members("lpSet"));
        //添加有序的 set 集合
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add("lpZset", "lp", 0);
        zset.add("lpZset", "26", 1);
        zset.add("lpZset", "178cm", 2);
        //输出有序 set 集合
        System.out.println(zset.rangeByScore("lpZset", 0, 2));*/
    }
    
    @Test
    public void testRedisson() throws JsonParseException, JsonMappingException, IOException{

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext-redisson.xml");
		RedissonClient redisson = (RedissonClient) applicationContext.getBean("redissonClient");
		// 首先获取redis中的key-value对象，key不存在没关系
		RBucket<String> keyObject = redisson.getBucket("1000seckill_successList");
		// 如果key存在，就设置key的值为新值value
		// 如果key不存在，就设置key的值为value
		
		String json = keyObject.get();
		ObjectMapper om = new ObjectMapper();
		SuccessKill[] readValue = om.readValue(json, SuccessKill[].class);
		System.out.println(readValue.length);
		
		//keyObject.set("value1111");
		System.out.println(redisson.getBucket("key"));
		
		/*RedissonLockUtil redissonUtil = (RedissonLockUtil) applicationContext.getBean("redissonLockUtil");
		System.out.println(redissonUtil);
		System.out.println("----------------------");
		System.out.println(redissonUtil.redisson);
		System.out.println("----------------------");
		redissonUtil.set("key", "value11111111");
		System.out.println(redissonUtil.get("key"));*/
    }
}
