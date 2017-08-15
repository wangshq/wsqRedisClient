/*
 * @(#)SetOperation.java 2016年4月19日 上午11:28:44 summer-cmpt-bmap-jredis Copyright
 * 2016 Thuisoft, Inc. All rights reserved. THUNISOFT PROPRIETARY/CONFIDENTIAL.
 * Use is subject to license terms.
 */
package com.cache.operation.impl;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

import com.cache.operation.AbstractOperation;
import com.cache.operation.ISetOperation;
import com.cache.utils.ConvertUtil;


/**
 * SetOperation
 * @author wujch
 * @version 1.0
 *
 */
public class SetOperation extends AbstractOperation implements ISetOperation {
    
    
    /**
     * @param key
     * @param right
     */
    public SetOperation(String key, int right) {
        super(key, right);
    }

    @Override
    public <T> Long sadd(String key, T... members) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.sadd(getKey(key), ConvertUtil.bean2StringArray(members));
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long scard(String key) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.scard(getKey(key));
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T> Set<T> sdiff(Class<T> clazz, String... keys) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return ConvertUtil.string2ObjSet(jedis.sdiff(getKeys(keys)), clazz);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long sdiffstore(String dstkey, String... keys) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.sdiffstore(getKey(dstkey),getKeys(keys));
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T> Set<T> sinter(Class<T> clazz, String... keys) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return ConvertUtil.string2ObjSet(jedis.sinter(getKeys(keys)), clazz);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long sinterstore(String dstkey, String... keys) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.sinterstore(getKey(dstkey),getKeys(keys));
        } finally {
            jedis.close();
        }
    }

    @Override
    public boolean sismember(String key, Object member) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.sismember(getKey(key),ConvertUtil.bean2String(member));
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T> Set<T> smembers(Class<T> clazz, String key) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return ConvertUtil.string2ObjSet(jedis.smembers(getKey(key)), clazz);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long smove(String srckey, String dstkey, Object member) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.smove(getKey(srckey),getKey(dstkey),ConvertUtil.bean2String(member));
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T> T spop(Class<T> clazz, String key) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return ConvertUtil.string2Bean(jedis.spop(getKey(key)), clazz);
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T> List<T> srandmember(Class<T> clazz, String key, int count) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return ConvertUtil.string2BeanList(clazz,jedis.srandmember(getKey(key),count));
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T> T srandmember(Class<T> clazz, String key) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return ConvertUtil.string2Bean(jedis.srandmember(getKey(key)),clazz);
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T> Long srem(String key, T... members) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.srem(getKey(key),ConvertUtil.bean2StringArray(members));
        } finally {
            jedis.close();
        }
    }

    @Override
    public <T> Set<T> sunion(Class<T> clazz, String... keys) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return ConvertUtil.string2ObjSet(jedis.sunion(getKeys(keys)),clazz);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long sunionstore(String dstkey, String... keys) {
        Jedis jedis = getJedisProxy(registerKey);
        try {
            return jedis.sunionstore(getKey(dstkey),getKeys(keys));
        } finally {
            jedis.close();
        }
    }

    
}
