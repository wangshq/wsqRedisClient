package com.cache.operation;

import java.util.Collection;
import java.util.List;

import redis.clients.jedis.BitOP;

/**
 * IStringValueOperation
 * 
 * @author wujch
 * @version 1.0
 * 
 */
public interface IStringOperation {
    /**
     * 将 value 追加到 key 原来的值的末尾 如果 key 不存在，就简单地将给定 key 设为 value
     * 
     * @param key
     * @param value
     * @return
     */
    Long append(String key, String value);

    /**
     * 计算给定字符串中，被设置为 1 的比特位的数量
     * 
     * @param key
     * @param start
     * @param end
     * @return 被设置为 1 的位的数量
     */
    Long bitcount(String key, long start, long end);

    /**
     * 对一个或多个保存二进制位的字符串 key 进行位元操作，并将结果保存到 destkey 上
     * 
     * @param op
     * @param destKey
     * @param srcKeys
     * @return 保存到 destkey 的字符串的长度，和输入 key 中最长的字符串长度相等
     */
    Long bitop(BitOP op, String destKey, String... srcKeys);

    /**
     * 将 key 中储存的数字值减一
     * 
     * @param key
     * @return 执行 DECR 命令之后 key 的值
     */
    Long decr(String key);

    /**
     * 将 key 所储存的值减去减量 decrement
     * 
     * @param key
     * @param integer
     * @return 减去 decrement 之后， key 的值
     */
    Long decrBy(String key, long integer);

    /**
     * 返回 key 所关联的字符串值
     * 
     * @param key
     * @return 当 key 不存在时，返回 nil ，否则，返回 key 的值。
     * 
     *         如果 key 不是字符串类型，那么返回一个错误
     */
    String get(String key);

    /**
     * 对 key 所储存的字符串值，获取指定偏移量上的位(bit)。
     * 
     * 当 offset 比字符串值的长度大，或者 key 不存在时，返回 0 。
     * 
     * @param key
     * @param offset
     * @return 字符串值指定偏移量上的位(bit)。
     */
    boolean getbit(String key, long offset);

    /**
     * 截取字符串，负数偏移量表示从字符串最后开始计数
     * 
     * @param key
     * @param startOffset
     * @param endOffset
     * @return 截取得出的子字符串
     */
    String getrange(String key, long startOffset, long endOffset);

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值 当 key 存在但不是字符串类型时，返回一个错误
     * 
     * @param key
     * @param value
     * @return 返回给定 key 的旧值
     */
    String getSet(String key, Object value);

    /**
     * 将 key 中储存的数字值增一
     * 
     * @param key
     * @return 执行 INCR 命令之后 key 的值
     */
    Long incr(String key);

    /**
     * 将 key 所储存的值加上增量 increment
     * 
     * @param key
     * @param integer
     * @return 加上 increment 之后， key 的值
     */
    Long incrBy(String key, long integer);

    /**
     * 为 key 中所储存的值加上浮点数增量 increment
     * 
     * @param key
     * @param value
     * @return 执行命令之后 key 的值
     */
    double incrByFloat(String key, double value);

    /**
     * 返回所有(一个或多个)给定 key 的值。
     * 
     * 如果给定的 key 里面，有某个 key 不存在，那么这个 key 返回特殊值 nil 。因此，该命令永不失败。
     * 
     * @param <T>
     * @param clazz
     * @param keys
     * @return 一个包含所有给定 key 的值的列表
     */
    List<String> mget(String... keys);

    /**
     * 同时设置一个或多个 key-value 对
     * 
     * @param keysvalues
     * @return 总是返回 OK
     */
    String mset(String... keysvalues);

    /**
     * MSETNX key value [key value ...]
     * 
     * 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在。
     * 
     * 即使只有一个给定 key 已存在， MSETNX 也会拒绝执行所有给定 key 的设置操作。
     * 
     * MSETNX 是原子性的，因此它可以用作设置多个不同 key 表示不同字段(field)的唯一性逻辑对象(unique logic
     * object)，所有字段要么全被设置，要么全不被设置。
     * 
     * @param keysvalues
     * @return 当所有 key 都成功设置，返回 1 。
     * 
     *         如果所有给定 key 都设置失败(至少有一个 key 已经存在)，那么返回 0
     */
    Long msetnx(String... keysvalues);

    /**
     * PSETEX key milliseconds value
     * 
     * 这个命令和 SETEX 命令相似，但它以毫秒为单位设置 key 的生存时间，而不是像 SETEX 命令那样，以秒为单位。
     * 
     * @param key
     * @param milliseconds
     * @param value
     * @return 设置成功时返回 OK
     */
    String psetex(String key, long milliseconds, String value);

    /**
     * 将字符串值 value 关联到 key
     * 
     * @param key
     * @param value
     *            return 操作成功完成时，才返回 OK
     */
    void set(String key, String value);

    /**
     * SETBIT key offset value
     * 
     * 对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)。
     * 
     * 位的设置或清除取决于 value 参数，可以是 0 也可以是 1 。
     * 
     * 当 key 不存在时，自动生成一个新的字符串值。
     * 
     * 字符串会进行伸展(grown)以确保它可以将 value 保存在指定的偏移量上。当字符串值进行伸展时，空白位置以 0 填充。
     * 
     * offset 参数必须大于或等于 0 ，小于 2^32 (bit 映射被限制在 512 MB 之内)。
     * 
     * 对使用大的 offset 的 SETBIT 操作来说，内存分配可能造成 Redis 服务器被阻塞。具体参考 SETRANGE
     * 命令，warning(警告)部分。
     * 
     * @param key
     * @param offset
     * @param value
     * @return 指定偏移量原来储存的位
     */
    boolean setbit(String key, long offset, boolean value);

    /**
     * SETBIT key offset value
     * 
     * 对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)。
     * 
     * 位的设置或清除取决于 value 参数，可以是 0 也可以是 1 。
     * 
     * 当 key 不存在时，自动生成一个新的字符串值。
     * 
     * 字符串会进行伸展(grown)以确保它可以将 value 保存在指定的偏移量上。当字符串值进行伸展时，空白位置以 0 填充。
     * 
     * offset 参数必须大于或等于 0 ，小于 2^32 (bit 映射被限制在 512 MB 之内)。
     * 
     * 对使用大的 offset 的 SETBIT 操作来说，内存分配可能造成 Redis 服务器被阻塞。具体参考 SETRANGE
     * 命令，warning(警告)部分。
     * 
     * @param key
     * @param offset
     * @param value
     * @return 指定偏移量原来储存的位
     */
    boolean setbit(String key, long offset, String value);

    /**
     * 将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)。
     * 
     * @param key
     * @param seconds
     * @param value
     * @return 设置成功时返回 OK 。当 seconds 参数不合法时，返回一个错误
     */
    String setex(String key, int seconds, String value);

    /**
     * SETNX key value
     * 
     * 将 key 的值设为 value ，当且仅当 key 不存在。
     * 
     * 若给定的 key 已经存在，则 SETNX 不做任何动作。
     * 
     * SETNX 是『SET if Not eXists』(如果不存在，则 SET)的简写。
     * 
     * @param key
     * @param value
     * @return 设置成功，返回 1 。设置失败，返回 0 。
     * 
     */
    Long setnx(String key, String value);

    /**
     * 用 value 参数覆写(overwrite)给定 key 所储存的字符串值，从偏移量 offset 开始 不存在的 key 当作空白字符串处理
     * 
     * @param key
     * @param offset
     * @param value
     * @return 被 SETRANGE 修改之后，字符串的长度
     */
    Long setrange(String key, long offset, String value);

    /**
     * 返回 key 所储存的字符串值的长度 当 key 储存的不是字符串值时，返回一个错误
     * 
     * @param key
     * @return 字符串值的长度 当 key 。不存在时，返回 0
     */
    Long strlen(String key);

    /**
     * 添加对象方法（扩展添加）
     * 
     * @param key
     * @param value
     *            对象
     */
    void setObject(String key, Object value);

    /**
     * 返回key所关联的对象（扩展添加）。
     * 
     * @param <T>
     * @param key
     * @return
     */
    <T> T getObject(String key, Class<T> clazz);

    /**
     * @param rawKeys
     * @return
     */
    <T> Collection<T> mgetObject(String[] rawKeys ,Class<T> clazz);

}
