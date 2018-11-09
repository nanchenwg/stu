package com.seckill.common.redisLock;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RedissonLockUtil {
	
	@Autowired
	public RedissonClient redisson;
	
    static long DEFAULT_WAIT_TIME = 5;
    static long DEFAULT_TIMEOUT   = 10;
    static TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;
    
    /**
     * 设置值
     * @param objectName
     * @param value
     */
    public void set(String objectName,Object value){  
        RBucket<Object> bucket = getRBucket(objectName);
    	bucket.set(value);
    	//redisson.getBucket(objectName).set(value);
    } 
    
    /**
     * 获取值
     * @param objectName
     * @return
     */
    public Object get(String objectName){
    	RBucket<Object> bucket = getRBucket(objectName);
    	return bucket.get();
    }
    
    /** 
     * 获取字符串对象 
     * @param objectName 
     * @return 
     */  
    public <T> RBucket<T> getRBucket(String objectName){  
        RBucket<T> bucket=redisson.getBucket(objectName);  
        return bucket;  
    } 
    
    /**
     * 释放锁
     * @param lockName
     */
    public void unlock(String lockName) {
    	RLock lock = redisson.getLock(lockName);
        unlock(lock);
    }
    
    /**
     * 释放锁
     * @param lock
     */
    public void unlock(RLock lock) {
        if (lock != null && lock.isLocked()) {
        	lock.unlock();
        }
    }
    
    /**
     * 使用分布式锁，使用锁默认超时时间
     * @param lockName
     * @param fairLock 是否使用公平锁
     * @return
     */
    public void lock(String lockName, boolean fairLock){
    	lock(lockName, DEFAULT_TIMEOUT, DEFAULT_TIME_UNIT, fairLock);
    }
    
    /**
     * 使用分布式锁。自定义锁的超时时间
     * @param lockName
     * @param leaseTime  锁超时时间。超时后自动释放锁
     * @param timeUnit
     * @param fairLock   是否使用公平锁
     * @return
     */
    public void lock(String lockName, long leaseTime, TimeUnit timeUnit, boolean fairLock){
    	RLock lock = getLock(lockName, fairLock);
	    try {
			lock.lock(leaseTime, timeUnit);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 尝试分布式锁，使用锁默认等待时间、超时时间。
     * @param lockName
     * @param fairLock 是否使用公平锁
     * @return
     */
    public boolean tryLock(String lockName, boolean fairLock) {
        return tryLock(lockName, DEFAULT_WAIT_TIME, DEFAULT_TIMEOUT, DEFAULT_TIME_UNIT, fairLock);
    }
	
	/**
	 * 尝试分布式锁，自定义等待时间、超时时间。
	 * @param lockName
	 * @param waitTime  最多等待时间
	 * @param leaseTime 上锁后自动释放锁时间
	 * @param timeUnit  时间单位
	 * @param fairLock  是否使用公平锁
	 * @return
	 */
    public boolean tryLock(String lockName, long waitTime, long leaseTime, TimeUnit timeUnit, boolean fairLock) {
    	RLock lock = getLock(lockName,fairLock);
		try {
			return lock.tryLock(waitTime, leaseTime, timeUnit);
		} catch (InterruptedException e) {
			return false;
		}
    }
	
    /**
     * 获取锁
     * @param lockName 锁名称
     * @param fairLock 是否使用公平锁
     * @return
     */
    private RLock getLock(String lockName, boolean fairLock) {
        RLock lock;
        if (fairLock) {
            lock = redisson.getFairLock(lockName);
        } else {
            lock = redisson.getLock(lockName);
        }
        return lock;
    }
}
