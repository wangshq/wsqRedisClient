package com.test.cglib;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisProxyTest {

    private static JedisPool jedisPool;

    public static void main(String[] args) {
        init();
        Jedis jedis = jedisPool.getResource();
        test(jedis);
        jedisPool.returnResourceObject(jedis);
    }

    private static void test(Jedis jedis) {
        // CglibProxy proxy = new CglibProxy();
        // Jedis je = (Jedis) proxy.getProxy(Jedis.class);

        JedisProxyCglib jp = new JedisProxyCglib();
        Jedis je = jp.getInstance(jedis);
        je.set("uuu11", "x");
        je.set("uuu12", "x2");
        je.set("uuu13", "x2");
    }

    private static void init() {

        int maxAcive = 200;
        int maxIdle = 200;
        int maxWait = 1000000;

        int port = 6399;

        boolean testOnBorrow = true;
        int timeout = 100000;
        String IP = "172.16.33.14";
        String AUTH = "";

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxAcive);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWait);
        config.setTestOnBorrow(testOnBorrow); // "redis.pool.testOnBorrow"
        if (StringUtils.isBlank(AUTH)) {
            jedisPool = new JedisPool(config, IP, port, timeout);
        } else {
            jedisPool = new JedisPool(config, IP, port, timeout, AUTH);
        }

    }

}
