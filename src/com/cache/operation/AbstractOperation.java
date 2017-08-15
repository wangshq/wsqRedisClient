package com.cache.operation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;

import com.cache.client.CacheManagerClient;
import com.cache.contans.CacheContans;
import com.cache.redis.JedisInterceptor;
import com.cache.redis.RedisPool;
import com.cache.utils.KeyUtils;

/**
 * 
 * AbstractOperation
 * 
 * @author wangshq
 * @version 1.0
 * 
 */
public abstract class AbstractOperation {

    protected final Log log = LogFactory.getLog(AbstractOperation.class);

    // key值
    protected String registerKey = null;
    protected int mode = CacheContans.MODE_READ_AND_WRITE;

    public AbstractOperation(String key, int mode) {
        this.registerKey = key;
        this.mode = mode;
    }

    /**
     * 方便使用
     * 
     * @param key
     * @return
     */
    protected String getKey(String key) {
        return KeyUtils.generateKey(registerKey, key);
    }

    protected String[] getKeys(String... keys) {
        return KeyUtils.generateKeys(registerKey, keys);
    }

    /**
     * 获取Jedis代理类<br>
     * 
     * 这个代理类在和redis命令一样的读写方法前都加了操作，具体哪些方法参见
     * 
     * @see {@link com.cache.redis.JedisInterceptor }
     *      及其建议使用operation中的方法，如果非要使用特殊方法，请先查看是否代理过该方法，谨慎！！
     * 
     * @param registerKey
     *            注册的key
     * @return
     * @author 张卫东
     */
    protected static Jedis getJedisProxy(String registerKey) {
        int mode = CacheManagerClient.getRegtedInfo().get(registerKey);
        Jedis jedis = RedisPool.INSTANCE.getJedis();
        return new JedisInterceptor().newInstance(jedis, registerKey, mode);
    }

}
