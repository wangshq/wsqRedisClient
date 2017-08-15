package com.cache.lock;

import java.util.List;

import com.cache.client.CacheManagerClient;
import com.cache.operation.IKeyOperation;
import com.cache.operation.IStringOperation;

/**
 * 用redis可以做分布式锁的功能
 * RedisLock
 * @author wangshq
 * @version 1.0
 *
 */
public class RedisLock {
    /** 锁持有超时时间，防止线程在入锁以后，无限的执行下去，让锁无法释放 */
    private long expireMsecs = 120000; //2分钟

    /**默认锁住的key */
    private String lockKey = null;

    public String regKey = null;

    /**
     * 声明注册的key值 , 和锁的key值
     */
    public RedisLock(String regKey, String lockNameString) {
        this.regKey = regKey;
        this.lockKey = lockNameString;
    }

    /**
     * 声明注册的key值，锁的key值，锁持有时间，循环等待的总时间，循环等待每次的时间
     */
    public RedisLock(String regKey, String lockNameString, long expireMsecs) {
        this.regKey = regKey;
        this.lockKey = lockNameString;
        this.expireMsecs = expireMsecs;
    }

    /**
     * 获取锁
     * @param stringOperation
     * @param lockKey
     * @return
     * @throws InterruptedException
     */
    public boolean tryLock(){
        IStringOperation stringOperation = CacheManagerClient.getStringOperation(this.regKey);
        return getLock(stringOperation);
    }

    /**
     * 循环重试获取锁
     * @param sleepTime 循环一次等待的时间
     * @param timeoutMsecs 循环等待的总时间
     * @return
     * @throws InterruptedException
     */
    public boolean tryLocak(long sleepTime, long timeoutMsecs) throws InterruptedException {
        IStringOperation stringOperation = CacheManagerClient.getStringOperation(this.regKey);
        while (timeoutMsecs >= 0) {
            boolean isSuc = getLock(stringOperation);
            if (isSuc) {
                return true;
            }
            timeoutMsecs -= sleepTime;
            Thread.sleep(sleepTime);
        }

        return false;
    }

    /**
     * 循环重试获取锁
     * @throws InterruptedException 
     * 
     */
    public boolean lock() throws InterruptedException {
        IStringOperation stringOperation = CacheManagerClient.getStringOperation(this.regKey);
        while (true) {
            boolean isSuc = getLock(stringOperation);
            if (isSuc) {
                return true;
            }
            Thread.sleep(50);
        }
    }

    /**
     * 释放锁
     * @param lockKey
     * @return
     * @throws InterruptedException
     */
    public void unlock(){
        IStringOperation stringOperation = CacheManagerClient.getStringOperation(this.regKey);
        IKeyOperation keyOperation = CacheManagerClient.getKeyOperation(this.regKey);
        String currentValueStr = stringOperation.get(lockKey);
        if(currentValueStr.startsWith("\"")){
            currentValueStr = currentValueStr.substring(1, currentValueStr.length()-1);
        }
        if (currentValueStr != null && Long.parseLong(currentValueStr) > System.currentTimeMillis()) {
            keyOperation.del(lockKey);
        }
    }

    /**
     * 获得锁
     * @param stringOperation
     * @return
     */
    private boolean getLock(IStringOperation stringOperation) {
        long expires = System.currentTimeMillis() + expireMsecs + 1;
        String expiresStr = String.valueOf(expires); //锁到期时间

        if (stringOperation.setnx(lockKey, expiresStr) == 1) {
            return true;
        }

        String currentValueStr = stringOperation.get(lockKey); //redis里的时间
        //判断是否为空，不为空的情况下，如果被其他线程设置了值，则第二个条件判断是过不去的
        // lock is expired
        if(currentValueStr.startsWith("\"")){
            currentValueStr = currentValueStr.substring(1, currentValueStr.length()-1);
        }
        if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
            //获取上一个锁到期时间，并设置现在的锁到期时间，
            //只有一个线程才能获取上一个线上的设置时间，因为jedis.getSet是同步的
            String oldValueStr = stringOperation.getSet(lockKey, expiresStr);

            if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                //如过这个时候，多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，他才有权利获取锁
                return true;
            }
        }
        return false;
    }

    /**
     * 获取所有的锁
     * @param locks
     * @return
     * @throws InterruptedException
     */
    public static synchronized boolean allAcquire(List<RedisLock> locks) throws InterruptedException {
        for (RedisLock lock : locks) {
            if (!lock.tryLock()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 释放所有的锁
     * @param locks
     * @throws InterruptedException
     */
    public static void allRelease(List<RedisLock> locks) throws InterruptedException {
        for (RedisLock lock : locks) {
            lock.unlock();
        }
    }
    
    /**
     * 判断是否上锁了
     * @return
     */
    public boolean isLockExist() {
        IKeyOperation keyOperation = CacheManagerClient.getKeyOperation(this.regKey);
        if(keyOperation.exists(this.lockKey)){
            IStringOperation stringOperation = CacheManagerClient.getStringOperation(this.regKey);
            String currentValueStr = stringOperation.get(lockKey); //redis里的时间
            //判断是否为空，不为空的情况下，如果被其他线程设置了值，则第二个条件判断是过不去的
            // lock is expired
            if(currentValueStr.startsWith("\"")){
                currentValueStr = currentValueStr.substring(1, currentValueStr.length()-1);
            }
            if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
                return true;
            }
        }
        return false;
    }
}
