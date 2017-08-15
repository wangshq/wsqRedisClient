package com.cache.operation.impl;

import java.util.List;

import redis.clients.jedis.Jedis;

import com.cache.operation.AbstractOperation;
import com.cache.operation.IListOperation;
import com.cache.utils.ConvertUtil;

/**
 * ListOperation
 * @author wangshq
 * @version 1.0
 *
 */
public class ListOperation extends AbstractOperation implements IListOperation {

    /**
     * @param key
     * @param right
     */
    public ListOperation(String key, int right) {
        super(key, right);
    }

    @Override
    public <T> List<T> blpop(Class<T> clazz, int time, String... key) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return ConvertUtil.string2BeanList(clazz, jedis.blpop(time, getKeys(key)));
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T> List<T> brpop(Class<T> clazz, int time, String... key) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return ConvertUtil.string2BeanList(clazz, jedis.brpop(time, getKeys(key)));
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T> T brpoplpush(String source, String destination, int timeout, Class<T> cls) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return ConvertUtil.string2Bean(jedis.brpoplpush(getKey(source), getKey(destination),timeout),cls);
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T> T lindex(String key, long index, Class<T> clazz) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return ConvertUtil.string2Bean(jedis.lindex(getKey(key), index),clazz);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long llen(String key) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.llen(getKey(key));
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T> T lpop(Class<T> clazz, String key) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return ConvertUtil.string2Bean(jedis.lpop(getKey(key)),clazz);
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T> T rpop(Class<T> clazz, String key) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return ConvertUtil.string2Bean(jedis.rpop(getKey(key)),clazz);
        } finally {
            jedis.close();
        }
    }

    
    @Override
    public <T> Long lpush(String key, T... obj) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.lpush(getKey(key),ConvertUtil.bean2StringArray(obj));
        } finally {
            jedis.close();
        }
    }

    
    @Override
    public <T> Long lpushx(String key, T... obj) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.lpushx(getKey(key),ConvertUtil.bean2StringArray(obj));
        } finally {
            jedis.close();
        }
    }

    
    @Override
    public <T> Long rpush(String key, T... obj) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.rpush(getKey(key),ConvertUtil.bean2StringArray(obj));
        } finally {
            jedis.close();
        }
    }

    
    @Override
    public <T> Long rpushx(String key, T... obj) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.rpushx(getKey(key),ConvertUtil.bean2StringArray(obj));
        } finally {
            jedis.close();
        }
    }

    
    @Override
    public <T> T rpoplpush(Class<T> cls, String srckey, String destKey) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return ConvertUtil.string2Bean(jedis.rpoplpush(srckey,destKey),cls);
        } finally {
            jedis.close();
        }
    }

    
    @Override
    public <T> List<T> lrange(String key, long start, long end, Class<T> calzz) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return ConvertUtil.string2BeanList(calzz, jedis.lrange(getKey(key),start,end));
        } finally {
            jedis.close();
        }
    }

    
    @Override
    public Long lrem(String key, long count, Object value) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.lrem(getKey(key),count,ConvertUtil.bean2String(value));
        } finally {
            jedis.close();
        }
    }

    
    @Override
    public String lset(String key, long index, Object value) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.lset(getKey(key),index,ConvertUtil.bean2String(value));
        } finally {
            jedis.close();
        }
    }

    
     
    @Override
    public String ltrim(String key, long start, long end) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.ltrim(getKey(key),start,end);
        } finally {
            jedis.close();
        }
    }


}
