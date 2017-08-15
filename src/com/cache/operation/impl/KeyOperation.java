/*
 * @(#)KeyOperation.java 2016-6-14上午10:36:59
 * Redis缓存服务
 * Copyright 2016 Thuisoft, Inc. All rights reserved.
 * THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.cache.operation.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;

import com.cache.operation.AbstractOperation;
import com.cache.operation.IKeyOperation;
import com.cache.utils.ConvertUtil;

/**
 * KeyOperation
 * @author wangshq
 * @version 1.0
 *
 */
public class KeyOperation extends AbstractOperation implements IKeyOperation{

    /**
     * @param key
     * @param right
     */
    public KeyOperation(String key, int right) {
        super(key, right);
    }

    @Override
    public Long del(String... keys) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.del(getKeys(keys));
        } finally {
            jedis.close();
        }
    }


    @Override
    public byte[] dump(String key) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.dump(getKey(key));
        } finally {
            jedis.close();
        }
    }

    @Override
    public Boolean exists(String key) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.exists(getKey(key));
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long expire(String key, int seconds) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.expire(getKey(key),seconds);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long expireAt(String key, long unixTime) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.expireAt(getKey(key),unixTime);
        } finally {
            jedis.close();
        }
    }


    @Override
    public Long persist(String key) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.persist(getKey(key));
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long pexpire(String key, long milliseconds) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.pexpire(getKey(key),milliseconds);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long pexpireAt(String key, long millisecondsTimestamp) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.pexpireAt(getKey(key),millisecondsTimestamp);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long pttl(String key) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.pttl(getKey(key));
        } finally {
            jedis.close();
        }
    }

    @Override
    public String randomKey() {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.randomKey();
        } finally {
            jedis.close();
        }
    }

    @Override
    public String rename(String oldkey, String newkey) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.rename(getKey(oldkey),getKey(newkey));
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long renamenx(String oldkey, String newkey) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.renamenx(getKey(oldkey),getKey(newkey));
        } finally {
            jedis.close();
        }
    }

    @Override
    public String restore(String key, int ttl, byte[] serializedValue) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.restore(getKey(key),ttl,serializedValue);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long ttl(String key) {
        
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.ttl(getKey(key));
        } finally {
            jedis.close();
        }
    }

    @Override
    public String type(String key) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.type(getKey(key));
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T> List<T> sort(Class<T> clazz, String key) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return ConvertUtil.string2BeanList(clazz, jedis.sort(getKey(key)));
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T> List<T> sort(Class<T> clazz, String key, SortingParams sortingParameters) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return ConvertUtil.string2BeanList(clazz, jedis.sort(getKey(key),sortingParameters));
        } finally {
            jedis.close();
        }
    }

    @Override
    public synchronized long deleteHeadKeyDatas(String keyHead) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            Set<String> keys = jedis.keys(getKey(keyHead)+"*");
            int count = keys.size();
            String[] keySs = new String[count];
            int i =0;
            for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
                String key = iterator.next();
                keySs[i++] = key;
            }
            jedis.del(keySs);
            return count;
        } finally {
            jedis.close();
        }
    }
    
    @Override
    public Set<String> keys(String key) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.keys(getKey(key)+"*");
        } finally {
            jedis.close();
        }
    }

}
