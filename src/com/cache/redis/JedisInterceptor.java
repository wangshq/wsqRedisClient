package com.cache.redis;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;

import com.cache.client.CacheManagerClient;
import com.cache.contans.CacheContans;

/**
 * 代理Jedis类，在所有redis操作前操作<br>
 * 1、把key进行封装<br>
 * 2、判断读写权限<br>
 * 
 */
public class JedisInterceptor implements MethodInterceptor {

    /** 实际执行的对象 */
    private Jedis target;

    /** 注册key */
    private String registerKey;

    /** 模式：读写、只读等 */
    private int mode;

    /** 写方法 */
    private static final List<String> MODIFY_METHOD = new ArrayList<String>();

    /** 读方法 */
    private static final List<String> READ_METHOD = new ArrayList<String>();

    static {

        // set
        // 写方法
        MODIFY_METHOD.add("sadd");
        MODIFY_METHOD.add("smove");
        MODIFY_METHOD.add("spop");
        MODIFY_METHOD.add("srem");
        MODIFY_METHOD.add("spop");

        // 读方法
        READ_METHOD.add("scard");
        READ_METHOD.add("sdiff");
        READ_METHOD.add("sdiffstore");
        READ_METHOD.add("sinter");
        READ_METHOD.add("sinterstore");
        READ_METHOD.add("sismember");
        READ_METHOD.add("smembers");
        READ_METHOD.add("srandmember");
        READ_METHOD.add("sunion");
        READ_METHOD.add("sunionstore");

        // Hash
        // 写方法
        MODIFY_METHOD.add("hdel");
        MODIFY_METHOD.add("hincrBy");
        MODIFY_METHOD.add("hincrByFloat");
        MODIFY_METHOD.add("hmset");
        MODIFY_METHOD.add("hset");
        MODIFY_METHOD.add("hsetnx");
        // 读方法
        READ_METHOD.add("hexists");
        READ_METHOD.add("hget");
        READ_METHOD.add("hgetAll");
        READ_METHOD.add("hkeys");
        READ_METHOD.add("hlen");
        READ_METHOD.add("hmget");
        READ_METHOD.add("hvals");

        // key
        // 写方法
        MODIFY_METHOD.add("del");
        MODIFY_METHOD.add("dump");
        MODIFY_METHOD.add("expire");
        MODIFY_METHOD.add("expireAt");
        MODIFY_METHOD.add("persist");
        MODIFY_METHOD.add("pexpire");
        MODIFY_METHOD.add("pexpireAt");
        MODIFY_METHOD.add("pttl");
        MODIFY_METHOD.add("rename");
        MODIFY_METHOD.add("renamenx");
        MODIFY_METHOD.add("restore");
        MODIFY_METHOD.add("ttl");

        // 读方法
        READ_METHOD.add("exists");
        READ_METHOD.add("keys");
        READ_METHOD.add("randomKey");
        READ_METHOD.add("type");
        READ_METHOD.add("sort");

        // list
        // 写方法
        MODIFY_METHOD.add("lpop");
        MODIFY_METHOD.add("rpop");
        MODIFY_METHOD.add("lpush");
        MODIFY_METHOD.add("lpushx");
        MODIFY_METHOD.add("rpush");
        MODIFY_METHOD.add("rpushx");
        MODIFY_METHOD.add("rpoplpush");
        MODIFY_METHOD.add("lrem");
        MODIFY_METHOD.add("lset");
        MODIFY_METHOD.add("ltrim");

        // 读方法
        READ_METHOD.add("blpop");
        READ_METHOD.add("brpop");
        READ_METHOD.add("brpoplpush");
        READ_METHOD.add("lindex");
        READ_METHOD.add("llen");
        READ_METHOD.add("lrange");

        // Srring
        // 写方法
        MODIFY_METHOD.add("append");
        MODIFY_METHOD.add("bitop");
        MODIFY_METHOD.add("decr");
        MODIFY_METHOD.add("decrBy");
        MODIFY_METHOD.add("getSet");
        MODIFY_METHOD.add("incr");
        MODIFY_METHOD.add("incrBy");
        MODIFY_METHOD.add("incrByFloat");
        MODIFY_METHOD.add("mset");
        MODIFY_METHOD.add("msetnx");
        MODIFY_METHOD.add("psetex");
        MODIFY_METHOD.add("set");
        MODIFY_METHOD.add("setbit");
        MODIFY_METHOD.add("setex");
        MODIFY_METHOD.add("setnx");
        MODIFY_METHOD.add("setrange");
        MODIFY_METHOD.add("setex");
        MODIFY_METHOD.add("setObject");

        // 读方法
        READ_METHOD.add("bitcount");
        READ_METHOD.add("get");
        READ_METHOD.add("getbit");
        READ_METHOD.add("getrange");
        READ_METHOD.add("mget");
        READ_METHOD.add("strlen");
        READ_METHOD.add("getObject");

        // sortedSet
        // 写方法
        MODIFY_METHOD.add("zadd");
        MODIFY_METHOD.add("zincrby");
        MODIFY_METHOD.add("zrem");
        MODIFY_METHOD.add("zremrangeByRank");
        MODIFY_METHOD.add("zremrangeByScore");

        // 读方法
        READ_METHOD.add("zcard");
        READ_METHOD.add("zcount");
        READ_METHOD.add("zrange");
        READ_METHOD.add("zrangeByScore");
        READ_METHOD.add("zrank");
        READ_METHOD.add("zrevrange");
        READ_METHOD.add("zrevrangeByScore");
        READ_METHOD.add("zrevrank");
        READ_METHOD.add("zscore");

    }

    /**
     * 创建代理对象
     * 
     * @param target
     * @return
     */
    public Jedis newInstance(Jedis target, String registerKey, int mode) {
        this.mode = mode;
        this.registerKey = registerKey;
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Jedis.class);
        enhancer.setCallback(this);// 回调方法
        return (Jedis) enhancer.create(); // 创建代理对象
    }

    @Override
    public Object intercept(Object proxyObj, Method method, Object[] args,
            MethodProxy proxy) throws Throwable {

        // 是否需要操作
        if ((MODIFY_METHOD.contains(method.getName())
                || READ_METHOD.contains(method.getName())) && !StringUtils.equals("close", method.getName())) {
            // 如果是写操作,则必须切换成主redis
            if (MODIFY_METHOD.contains(method.getName())) {
                target.close();
                target = RedisPool.INSTANCE.getMasterJedis();
            }
            validate(method);// 验证模式
        }
        return proxy.invoke(target, args);
    }

    /**
     * 验证模式
     * 
     */
    private void validate(Method method) {
        Map<String, Integer> regMap = CacheManagerClient.getRegtedInfo();

        if (StringUtils.isBlank(registerKey)
                || !regMap.containsKey(registerKey)) {
            throw new RuntimeException("注册的key值不正确【" + registerKey + "】");
        }

        if (mode == CacheContans.MODE_READ_ONLY
                && MODIFY_METHOD.contains(method.getName())) {
            throw new RuntimeException("只读模式下不允许写操作【" + registerKey + "】");
        }
    }

}
