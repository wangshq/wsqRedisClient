package com.cache.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * RedisPoolQueue
 * @author wangshq
 * @version 1.0
 *
 */
public class RedisPoolQueue {

    private List<JedisPool> jedisPools = new ArrayList<JedisPool>();
    
    private int index = 0;
    
    private AtomicInteger size = new AtomicInteger(0);
    
    /**
     * @return the size
     */
    public AtomicInteger getSize() {
        return size;
    }


    private final Log logger = LogFactory.getLog(RedisPoolQueue.class);
    
    
    private Set<JedisPool> errorPools = new HashSet<JedisPool>();
    
    
    /**
     * 刷新缓存的时间间隔，单位毫秒
     */
    private long REFRESH_POOL_TIME_INTERVAL = 600000;
    

    public synchronized void putJedisPool(JedisPool jedisPool) {
        jedisPools.add(jedisPool);
        size.getAndIncrement();
    }
    
    private synchronized JedisPool removeJedisPool(int mode) {
        size.getAndDecrement();
        JedisPool jedisPool = jedisPools.remove(mode);
        return jedisPool;
    }
    
    
    public Jedis getJedis() {
        int mode = getMode();
        
        try {
            JedisPool jedisPool = jedisPools.get(mode);
            Jedis jedis = jedisPool.getResource();
            return jedis;
        } catch (Exception e) {
            JedisPool jedisPool = removeJedisPool(mode);
            errorPools.add(jedisPool);
            logger.error("从redis链表中获取连接出错",e);
            throw new RuntimeException("从redis链表中获取连接出错", e);
        }
    }

    /**
     * @return
     */
    public int getMode() {
        if(index >2000){
            index = 0;
        }
        if(size.get() == 0){
            throw new RuntimeException("还没有初始化redis连接池，请检查！");
        }
        
        int mode = (index++)%size.get();
        return mode;
    }

    
    public Jedis getMasterJedis(){
        JedisPool jedisPool = null;
        try {
            jedisPool = jedisPools.get(0);
            Jedis jedis = jedisPool.getResource();
            return jedis;
        } catch (Exception e) {
            logger.error("从master redis中获取连接出错",e);
            throw new RuntimeException("从master redis中获取连接出错", e);
        }
    }
    
    
    // 第二种方法：设定指定任务task在指定延迟delay后进行固定延迟peroid的执行  
    // schedule(TimerTask task, long delay, long period)  
    public void refreshConnect() {
        Timer timer = new Timer("redis刷新可用的连接的定时任务");  
        timer.schedule(new TimerTask() {
            public void run() {
                logger.info("refresh redis pool");

                for (Iterator<JedisPool> iterator = errorPools.iterator(); iterator.hasNext();) {
                    JedisPool jedisPool = iterator.next();
                    Jedis jedis = null;
                    try {
                        jedis = jedisPool.getResource();
                        if (jedis != null) {
                            putJedisPool(jedisPool);
                            iterator.remove();
                        }
                    } catch (Exception e) {
                        logger.error("获取redis连接出错！", e);
                    } finally {
                        jedis.close();
                    }
                }
            
            }  
        }, 60000, REFRESH_POOL_TIME_INTERVAL);  
    }
}
