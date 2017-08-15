package com.cache.redis;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.cache.utils.PropsUtil;

/**
 * 用枚举实现的jedis连接池的单例
 * JedisPool
 * @author wangshq
 * @version 1.0
 *
 */
public enum RedisPool {

    INSTANCE;

    private final Log LOG = LogFactory.getLog(RedisPool.class);

    /** Redis的端口号 */
    private final int PORT = 6379;

    /**
     * 可用连接实例的最大数目，默认值为8; 如果赋值为-1，则表示不限制；
     * 如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
     */
    private final int MAX_ACTIVE = 300;

    /**
     * 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
     */
    private final int MAX_IDLE = 200;

    /**
     * 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。 如果超过等待时间，则直接抛出JedisConnectionException；
     */
    private final int MAX_WAIT = 1000000;

    /**
     * 客户端连接过期时间
     */
    private final int TIMEOUT = 10000;

    /**
     * 先简单实现,后续逐步改造成jediscluster
     */
    private RedisPoolQueue poolQueue = new RedisPoolQueue();
    
    

    /**
     * 通过配置文件来设置数据源
     */
    public void setJedisPoolByConfig() {
        if (poolQueue != null && poolQueue.getSize().get() > 0) {
            // 如果redis已经实例化过了,则不再重复实例化redis
            return;
        }
        try {
            String maxAcive = PropsUtil.getConfigKey("redis.pool.maxActive");
            String maxIdle = PropsUtil.getConfigKey("redis.pool.maxIdle");
            String maxWait = PropsUtil.getConfigKey("redis.pool.maxWait");
            String port = PropsUtil.getConfigKey("redis.port");
            String testOnBorrow = PropsUtil
                    .getConfigKey("redis.pool.testOnBorrow");
            String timeout = PropsUtil.getConfigKey("redis.timeout");
            String IP = PropsUtil.getConfigKey("redis.ip");
            /** Redis服务器密码 */
            String AUTH = PropsUtil.getConfigKey("redis.password");
            // 设置redis的连接池
            setJedisPool(IP, port, AUTH, timeout, maxAcive, maxIdle, maxWait,
                    Boolean.valueOf(testOnBorrow));
        } catch (Exception e) {
            LOG.warn("从配置文件初始化Redis连接池失败,需要自行初始化Redis连接池", e);
        }
    }

    /**
     * 设置redis的连接池
     * @param maxAcive redis连接池的最大连接数
     * @param maxIdle redis连接池最大能够保持idel状态的对象数
     * @param maxWait 当池内没有返回对象时，最大等待时间
     * @param port redis服务的端口号
     * @param testOnBorrow 当调用borrow Object方法时，是否进行有效性检查（true时不检查）
     * @param timeout 客户端连接过期时间
     * @param IP redis服务的IP地址
     * @param AUTH 密码
     */
    public void setJedisPool(String IP, String port, String AUTH,
            String timeout, String maxAcive, String maxIdle, String maxWait,
            boolean testOnBorrow) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(getIntProperty(maxAcive, MAX_ACTIVE));
        config.setMaxIdle(getIntProperty(maxIdle, MAX_IDLE));
        config.setMaxWaitMillis(getIntProperty(maxWait, MAX_WAIT));
        // "redis.pool.testOnBorrow"
        config.setTestOnBorrow(testOnBorrow);
        AUTH = StringUtils.isBlank(AUTH) ? null : AUTH;

        String[] ips = IP.split(";");
        String[] ports = port.split(";");
        for (int i = 0; i < ips.length; i++) {
            poolQueue.putJedisPool(new JedisPool(config, ips[i], getIntProperty(
                ports[i], PORT), getIntProperty(timeout, TIMEOUT), AUTH));
        }
    }


    /**
     * 设置redis的连接池（使用默认配置，默认配置见）
     * @param port redis服务的端口号
     * @param IP redis服务的IP地址
     * @param AUTH 密码
     */
    public void setJedisPool(String IP, String port, String AUTH) {
        setJedisPool(IP, port, AUTH, null, null, null, null, true);
    }

    private static int getIntProperty(String property, int defaults) {
        if (StringUtils.isBlank(property)) {
            return defaults;
        }
        return Integer.parseInt(property);
    }

    /**
     * 默认第一个是master
     * @return
     */
    public Jedis getMasterJedis() {
        return poolQueue.getMasterJedis();
    }

    /**
     * @return
     */
    public Jedis  getJedis() {
        return poolQueue.getJedis();
    }

}