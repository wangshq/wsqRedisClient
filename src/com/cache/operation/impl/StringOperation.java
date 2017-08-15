/*
 * @(#)StringValueOperation.java 2016年4月19日 下午3:25:44 summer-cmpt-bmap-jredis
 * Copyright 2016 Thuisoft, Inc. All rights reserved. THUNISOFT
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.cache.operation.impl;

import java.util.Collection;
import java.util.List;

import redis.clients.jedis.BitOP;
import redis.clients.jedis.Jedis;

import com.cache.operation.AbstractOperation;
import com.cache.operation.IStringOperation;
import com.cache.utils.ConvertUtil;

/**
 * StringValueOperation
 * @author wujch
 * @version 1.0
 *
 */
public class StringOperation extends AbstractOperation implements IStringOperation {

    /**
     * @param key
     * @param right
     */
    public StringOperation(String key, int right) {
        super(key, right);
    }

    @Override
    public void setObject(String key, Object value) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            jedis.set(getKey(key), ConvertUtil.bean2String(value));
        } finally {
            jedis.close();
        }
    }

    @Override
    public void set(String key, String value) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            jedis.set(getKey(key), value);
        } finally {
            jedis.close();
        }
    }

    @Override
    public String get(String key) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.get(getKey(key));
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T> T getObject(String key, Class<T> clazz) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return ConvertUtil.string2Bean(jedis.get(getKey(key)), clazz);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long append(String key, String value) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.append(getKey(key),value);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long bitcount(String key, long start, long end) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.bitcount(getKey(key),start,end);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long bitop(BitOP op, String destKey, String... srcKeys) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.bitop(op,getKey(destKey),getKeys(srcKeys));
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long decr(String key) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.decr(getKey(key));
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long decrBy(String key, long integer) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.decrBy(getKey(key),integer);
        } finally {
            jedis.close();
        }
    }

    @Override
    public boolean getbit(String key, long offset) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.getbit(getKey(key),offset);
        } finally {
            jedis.close();
        }
    }

    @Override
    public String getrange(String key, long startOffset, long endOffset) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.getrange(getKey(key),startOffset,endOffset);
        } finally {
            jedis.close();
        }
    }

    @Override
    public String getSet(String key, Object value) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.getSet(getKey(key),ConvertUtil.bean2String(value));
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long incr(String key) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.incr(getKey(key));
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long incrBy(String key, long integer) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.incrBy(getKey(key),integer);
        } finally {
            jedis.close();
        }
    }

    @Override
    public double incrByFloat(String key, double value) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.incrByFloat(getKey(key),value);
        } finally {
            jedis.close();
        }
    }

    @Override
    public List<String> mget(String... keys) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.mget(getKeys(keys));
        } finally {
            jedis.close();
        }
    }

    @Override
    public String mset(String... keysvalues) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.mset(getKeys(keysvalues));
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long msetnx(String... keysvalues) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.msetnx(getKeys(keysvalues));
        } finally {
            jedis.close();
        }
    }

    @Override
    public String psetex(String key, long milliseconds, String value) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.psetex(getKey(key),milliseconds,value);
        } finally {
            jedis.close();
        }
    }

    @Override
    public boolean setbit(String key, long offset, boolean value) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.setbit(getKey(key),offset,value);
        } finally {
            jedis.close();
        }
    }

    @Override
    public boolean setbit(String key, long offset, String value) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.setbit(getKey(key),offset,value);
        } finally {
            jedis.close();
        }
    }

    @Override
    public String setex(String key, int seconds, String value) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.setex(getKey(key),seconds,value);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long setnx(String key, String value) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.setnx(getKey(key),value);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long setrange(String key, long offset, String value) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.setrange(getKey(key),offset,value);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long strlen(String key) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.strlen(getKey(key));
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T> Collection<T> mgetObject(String[] rawKeys, Class<T> clazz) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return ConvertUtil.string2BeanList(clazz, jedis.mget(getKeys(rawKeys)));
        } finally {
            jedis.close();
        }
    }

}
