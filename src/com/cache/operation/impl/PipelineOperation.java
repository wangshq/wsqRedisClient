package com.cache.operation.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.BitOP;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import com.cache.operation.AbstractOperation;
import com.cache.redis.RedisPool;
import com.cache.utils.ConvertUtil;

/**
 * 批量操作，实现了全部的写接口 <br>
 * 
 * @author 张卫东
 * @version 1.0
 * @date 2016-6-14
 */
public class PipelineOperation extends AbstractOperation {

    private final Jedis jedis;

    private final Pipeline pip;

    public PipelineOperation(String key, int mode) {
        super(key, mode);
        // TODO PipelineOperation的操作需要保证落到一个redis客户端上,并且可以写入,slave不支持写入
        this.jedis = RedisPool.INSTANCE.getMasterJedis();
        pip = this.jedis.pipelined();
    }

    public void sync() {
        pip.sync();
    }

    public void close() {
        jedis.close();
    }

    public Response<Long> zadd(String key, double score, Object member) {
        return pip.zadd(getKey(key), score, ConvertUtil.bean2String(member));
    }

    public Response<Long> zcard(String key) {

        return pip.zcard(getKey(key));
    }

    public Response<Long> zcount(String key, double min, double max) {
        return pip.zcount(getKey(key), min, max);
    }

    public Response<Double> zincrby(String key, double score, Object member) {

        return pip.zincrby(getKey(key), score, ConvertUtil.bean2String(member));
    }

    public Response<Long> zrank(String key, Object member) {

        return pip.zrank(getKey(key), ConvertUtil.bean2String(member));
    }

    public <T> Response<Long> zrem(String key, T... members) {
        return pip.zrem(getKey(key), ConvertUtil.bean2StringArray(members));
    }

    public Response<Long> zremrangeByRank(String key, long start, long end) {

        return pip.zremrangeByRank(getKey(key), start, end);
    }

    public Response<Long> zremrangeByScore(String key, double start, double end) {
        return pip.zremrangeByScore(getKey(key), start, end);
    }

    public Response<Long> zrevrank(String key, Object member) {
        return pip.zrevrank(getKey(key), ConvertUtil.bean2String(member));
    }

    public Response<Double> zscore(String key, Object member) {
        return pip.zscore(getKey(key), ConvertUtil.bean2String(member));
    }

    public <T> Response<Long> sadd(String key, T... members) {

        return pip.sadd(getKey(key), ConvertUtil.bean2StringArray(members));
    }

    public Response<Long> scard(String key) {

        return pip.scard(getKey(key));
    }

    public Response<Long> sdiffstore(String dstkey, String... keys) {

        return pip.sdiffstore(getKey(dstkey), getKeys(keys));
    }

    public Response<Long> sinterstore(String dstkey, String... keys) {

        return pip.sinterstore(getKey(dstkey), getKeys(keys));
    }

    public Response<Boolean> sismember(String key, Object member) {

        return pip.sismember(getKey(key), ConvertUtil.bean2String(member));
    }

    public Response<Long> smove(String srckey, String dstkey, Object member) {

        return pip.smove(getKey(srckey), getKey(dstkey), ConvertUtil
                .bean2String(member));
    }

    public <T> Response<Long> srem(String key, T... members) {

        return pip.srem(getKey(key), ConvertUtil.bean2StringArray(members));
    }

    public Response<Long> sunionstore(String dstkey, String... keys) {

        return pip.sunionstore(getKey(dstkey), getKeys(keys));
    }

    public Response<Long> append(String key, String value) {

        return pip.append(getKey(key), value);
    }

    public Response<Long> bitcount(String key, long start, long end) {

        return pip.bitcount(getKey(key), start, end);
    }

    public Response<Long> bitop(BitOP op, String destKey, String... srcKeys) {

        return pip.bitop(op, getKey(destKey), getKeys(srcKeys));
    }

    public Response<Long> decr(String key) {

        return pip.decr(getKey(key));
    }

    public Response<Long> decrBy(String key, long integer) {

        return pip.decrBy(getKey(key), integer);
    }

    public Response<Boolean> getbit(String key, long offset) {

        return pip.getbit(getKey(key), offset);
    }

    public Response<String> getSet(String key, Object value) {

        return pip.getSet(getKey(key), ConvertUtil.bean2String(value));
    }

    public Response<Long> incr(String key) {

        return pip.incr(getKey(key));
    }

    public Response<Long> incrBy(String key, long integer) {

        return pip.incrBy(getKey(key), integer);
    }

    public Response<Double> incrByFloat(String key, double value) {
        return pip.incrByFloat(getKey(key), value);
    }

    public Response<List<String>> mget(String... keys) {
        return pip.mget(getKeys(keys));
    }

    public Response<String> mset(String... keysvalues) {
        return pip.mset(getKeys(keysvalues));
    }

    public Response<Long> msetnx(String... keysvalues) {

        return pip.msetnx(getKeys(keysvalues));
    }

    public Response<String> psetex(String key, long milliseconds, String value) {

        return pip.psetex(getKey(key), milliseconds, value);
    }

    public void set(String key, String value) {

        pip.set(getKey(key), value);

    }

    public Response<Boolean> setbit(String key, long offset, boolean value) {

        return pip.setbit(getKey(key), offset, value);
    }

    public Response<String> setex(String key, int seconds, String value) {

        return pip.setex(getKey(key), seconds, value);
    }

    public Response<Long> setnx(String key, String value) {
        return pip.setnx(getKey(key), value);
    }

    public Response<Long> setrange(String key, long offset, String value) {
        return pip.setrange(getKey(key), offset, value);
    }

    public Response<Long> strlen(String key) {

        return pip.strlen(getKey(key));
    }

    public void setObject(String key, Object value) {
        pip.set(getKey(key), ConvertUtil.bean2String(value));
    }

    public <T> T getObject(String key, Class<T> clazz) {
//        return ConvertUtil.string2Bean(pip.get(getKey(key)), clazz);
        return null;
    }

    public Response<Long> llen(String key) {

        return pip.llen(getKey(key));
    }

    public <T> Response<Long> lpush(String key, T... obj) {

        return pip.lpush(getKey(key), ConvertUtil.bean2StringArray(obj));
    }

    public <T> Response<Long> lpushx(String key, T... obj) {

        return pip.lpushx(getKey(key), ConvertUtil.bean2StringArray(obj));
    }

    public <T> Response<Long> rpush(String key, T... obj) {

        return pip.rpush(getKey(key), ConvertUtil.bean2StringArray(obj));
    }

    public <T> Response<Long> rpushx(String key, T... obj) {

        return pip.rpushx(getKey(key), ConvertUtil.bean2StringArray(obj));
    }

    public Response<String> rpoplpush(String srckey, String destKey) {

        return pip.rpoplpush(getKey(srckey), getKey(destKey));
    }

    public Response<Long> lrem(String key, long count, Object value) {

        return pip.lrem(getKey(key), count, ConvertUtil.bean2String(value));
    }

    public Response<String> lset(String key, long index, Object value) {

        return pip.lset(getKey(key), index, ConvertUtil.bean2String(value));
    }

    public Response<String> ltrim(String key, long start, long end) {

        return pip.ltrim(getKey(key), start, end);
    }

    public Response<Long> del(String... keys) {

        return pip.del(getKeys(keys));
    }

    public Response<byte[]> dump(String key) {

        return pip.dump(getKey(key));
    }

    public Response<Boolean> exists(String key) {

        return pip.exists(getKey(key));
    }

    public Response<Long> expire(String key, int seconds) {

        return pip.expire(getKey(key), seconds);
    }

    public Response<Long> expireAt(String key, long unixTime) {

        return pip.expireAt(getKey(key), unixTime);
    }

    public Response<Set<String>> keys(String pattern) {

        return pip.keys(pattern);
    }

    public Response<Long> persist(String key) {

        return pip.persist(getKey(key));
    }

    public Response<Long> pexpire(String key, long milliseconds) {

        return pip.pexpire(getKey(key), milliseconds);
    }

    public Response<Long> pexpireAt(String key, long millisecondsTimestamp) {

        return pip.pexpireAt(getKey(key), millisecondsTimestamp);
    }

    public Response<Long> pttl(String key) {

        return pip.pttl(getKey(key));
    }

    public Response<String> randomKey() {
        return pip.randomKey();
    }

    public Response<String> rename(String oldkey, String newkey) {

        return pip.rename(getKey(oldkey), getKey(newkey));
    }

    public Response<Long> renamenx(String oldkey, String newkey) {

        return pip.renamenx(getKey(oldkey), getKey(newkey));
    }

    public Response<String> restore(String key, int ttl, byte[] serializedValue) {

        return pip.restore(getKey(key), ttl, serializedValue);
    }

    public Response<Long> ttl(String key) {

        return pip.ttl(getKey(key));
    }

    public Response<String> type(String key) {

        return pip.type(getKey(key));
    }

    public Response<Long> hdel(String key, String... fields) {

        return pip.hdel(getKey(key), fields);
    }

    public Response<Boolean> hexists(String key, String field) {

        return pip.hexists(getKey(key), field);
    }

    public Response<Long> hincrBy(String key, String field, long value) {

        return pip.hincrBy(getKey(key), field, value);
    }

    public Response<Double> hincrByFloat(String key, String field, double value) {

        return pip.hincrByFloat(getKey(key), field, value);
    }

    public Response<Set<String>> hkeys(String key) {

        return pip.hkeys(getKey(key));
    }

    public Response<Long> hlen(String key) {

        return pip.hlen(getKey(key));
    }

    public <T> Response<String> hmset(String key, Map<String, T> hash) {

        return pip.hmset(getKey(key), ConvertUtil.bean2StringMap(hash));
    }

    public Response<Long> hset(String key, String field, Object value) {

        return pip.hset(getKey(key), field, ConvertUtil.bean2String(value));
    }

    public Response<Long> hsetnx(String key, String field, Object value) {

        return pip.hsetnx(getKey(key), field, ConvertUtil.bean2String(value));
    }

    public Response<Map<String, String>> hgetAll(String key) {
        return pip.hgetAll(getKey(key));
    }

}