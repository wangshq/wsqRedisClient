/*
 * @(#)SortedSetOperation.java 2016-6-14下午06:13:24
 * Redis缓存服务
 * Copyright 2016 Thuisoft, Inc. All rights reserved.
 * THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.cache.operation.impl;

import java.util.Set;

import redis.clients.jedis.Jedis;

import com.cache.operation.AbstractOperation;
import com.cache.operation.ISortedSetOperation;
import com.cache.utils.ConvertUtil;

/**
 * SortedSetOperation
 * @author wangshq
 * @version 1.0
 *
 */
public class SortedSetOperation extends AbstractOperation implements ISortedSetOperation{

    public SortedSetOperation(String key, Integer right) {
        super(key, right);
    }

    @Override
    public Long zadd(String key, double score, Object member) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.zadd(getKey(key), score,ConvertUtil.bean2String(member));
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long zcard(String key) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.zcard(getKey(key));
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long zcount(String key, double min, double max) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.zcount(getKey(key),min,max);
        } finally {
            jedis.close();
        }
    }

    @Override
    public double zincrby(String key, double score, Object member) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.zincrby(getKey(key),score,ConvertUtil.bean2String(member));
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T> Set<T> zrange(Class<T> clazz, String key, long start, long end) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return ConvertUtil.string2ObjSet(jedis.zrange(getKey(key),start,end), clazz);
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T> Set<T> zrangeByScore(Class<T> clazz, String key, double min, double max) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return ConvertUtil.string2ObjSet(jedis.zrangeByScore(getKey(key),min,max), clazz);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long zrank(String key, Object member) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.zrank(getKey(key),ConvertUtil.bean2String(member));
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T> Long zrem(String key, T... members) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.zrem(getKey(key),ConvertUtil.bean2StringArray(members));
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long zremrangeByRank(String key, long start, long end) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.zremrangeByRank(getKey(key), start, end);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long zremrangeByScore(String key, double start, double end) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.zremrangeByScore(getKey(key), start, end);
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T> Set<T> zrevrange(Class<T> clazz, String key, long start, long end) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return ConvertUtil.string2ObjSet(jedis.zrevrange(getKey(key), start, end),clazz);
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T> Set<T> zrevrangeByScore(Class<T> clazz, String key, double max, double min) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return ConvertUtil.string2ObjSet(jedis.zrevrangeByScore(getKey(key), max, min),clazz);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long zrevrank(String key, Object member) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.zrevrank(getKey(key), ConvertUtil.bean2String(member));
        } finally {
            jedis.close();
        }
    }

    @Override
    public double zscore(String key, Object member) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.zscore(getKey(key), ConvertUtil.bean2String(member));
        } finally {
            jedis.close();
        }
    }
}
