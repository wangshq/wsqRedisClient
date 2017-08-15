/*
 * @(#)HashMapOperation.java 2016年4月18日 下午5:31:24 summer-cmpt-bmap-jredis
 * Copyright 2016 Thuisoft, Inc. All rights reserved. THUNISOFT
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.cache.operation.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

import com.cache.operation.AbstractOperation;
import com.cache.operation.IHashOperation;
import com.cache.utils.ConvertUtil;


/**
 * HashMapOperation
 * @author wangshq
 * @version 1.0
 *
 */
public class HashOperation extends AbstractOperation implements IHashOperation {
    
    /**
     * @param key
     * @param right
     */
    public HashOperation(String key, int right) {
        super(key, right);
    }

    @Override
    public Long hdel(String key, String... fields) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.hdel(getKey(key), fields);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Boolean hexists(String key, String field) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.hexists(getKey(key), field);
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T> T hget(Class<T> clazz, String key, String field) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return ConvertUtil.string2Bean(jedis.hget(getKey(key), field), clazz);
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T> Map<String, T> hgetAll(Class<T> clazz, String key) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return ConvertUtil.string2BeanMap(jedis.hgetAll(getKey(key)), clazz);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long hincrBy(String key, String field, long value) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.hincrBy(getKey(key),field,value);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Double hincrByFloat(String key, String field, double value) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.hincrByFloat(getKey(key),field,value);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Set<String> hkeys(String key) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.hkeys(getKey(key));
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long hlen(String key) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.hlen(getKey(key));
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T> List<T> hmget(Class<T> clazz, String key, String... fields) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return ConvertUtil.string2BeanList(clazz, jedis.hmget(getKey(key),fields));
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T> String hmset(String key, Map<String, T> hash) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.hmset(getKey(key),ConvertUtil.bean2StringMap(hash));
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long hset(String key, String field, Object value) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.hset(getKey(key),field, ConvertUtil.bean2String(value));
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long hsetnx(String key, String field, Object value) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.hsetnx(getKey(key),field, ConvertUtil.bean2String(value));
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T> List<T> hvals(Class<T> clazz,String key) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return ConvertUtil.string2BeanList(clazz, jedis.hvals(getKey(key)));
        } finally {
            jedis.close();
        }
    }


}
