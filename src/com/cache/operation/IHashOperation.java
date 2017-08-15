package com.cache.operation;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * IHashOperation
 * @author wangshq
 * @version 1.0
 *
 * @param <String>
 * @param <String>
 * @param <Object>
 */
public interface IHashOperation {
    
    /**
     * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略
     * @param key
     * @param fields    map的key
     * @return 返回删除域的个数,删除不存在的域返回0
     */
    Long hdel(final String key, final String... fields);
    
    
    /**
     * 查看哈希表 key 中，给定域 field 是否存在
     * @param key
     * @param field      map的key
     * @return true 存在  false不存在
     */
    Boolean hexists(final String key, final String field);
    
    
    /**
     * 返回哈希表 key 中给定域 field 的值。
     * 
     * @param key
     * @param field
     * @return 当给定域不存在或是给定 key 不存在时，返回 nil
     */
    <T> T hget(Class<T> clazz,final String key, final String field);
    
    
    /**
     * 返回哈希表 key 中，所有的域和值
     * @param key
     * @return 以列表形式返回哈希表的域和域的值。若 key 不存在，返回空列表。
     */
    <T> Map<String, T> hgetAll(Class<T> clazz,final String key);
    
    
    /**
     * 为哈希表 key 中的域 field 的值加上增量 increment 。
     * 增量也可以为负数，相当于对给定域进行减法操作。
     * 如果 key 不存在，一个新的哈希表被创建并执行 HINCRBY 命令
     * 如果域 field 不存在，那么在执行命令前，域的值被初始化为 0
     * 对一个储存字符串值的域 field 执行 HINCRBY 命令将造成一个错误。
     * 本操作的值被限制在 64 位(bit)有符号数字表示之内
     * @param key
     * @param field
     * @param value 
     * @return 执行 HINCRBY 命令之后，哈希表 key 中域 field 的值。
     */
    Long hincrBy(final String key, final String field, final long value);
    
    
    /**
     * 为哈希表 key 中的域 field 加上浮点数增量 increment 。
     * 如果哈希表中没有域 field ，那么 HINCRBYFLOAT 会先将域 field 的值设为 0 ，然后再执行加法操作
     * 如果键 key 不存在，那么 HINCRBYFLOAT 会先创建一个哈希表，再创建域 field ，最后再执行加法操作。
     * 当以下任意一个条件发生时，返回一个错误：
     * <p>
     * 域 field 的值不是字符串类型(因为 redis 中的数字和浮点数都以字符串的形式保存，所以它们都属于字符串类型）
     * 域 field 当前的值或给定的增量 increment 不能解释(parse)为双精度浮点数(double precision floating point number)
     * <p>
     * @param key
     * @param field
     * @param value
     * @return 执行加法操作之后 field 域的值。
     */
    Double hincrByFloat(final String key, final String field, final double value);
    
    
    /**
     * 返回哈希表 key 中的所有域。
     * @param key
     * @return 一个包含哈希表中所有域的表。
     * 当 key 不存在时，返回一个空表。
     */
    Set<String> hkeys(final String key);
    
    /**
     * 返回哈希表 key 中域的数量。
     * @param key
     * @return 哈希表中域的数量。
     *         当 key 不存在时，返回 0 。
     */
    Long hlen(final String key);
    
    /**
     * 返回哈希表 key 中，一个或多个给定域的值。
     * 如果给定的域不存在于哈希表，那么返回一个 nil 值。
     * @param key
     * @param fields
     * @return M一个包含多个给定域的关联值的表，表值的排列顺序和给定域参数的请求顺序一样。
     */
    <T> List<T> hmget(Class<T> clazz,final String key, final String... fields);
    
    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中。
     * 此命令会覆盖哈希表中已存在的域。
     * 如果 key 不存在，一个空哈希表被创建并执行 HMSET 操作。
     * @param key
     * @param hash
     * @return如果命令执行成功，返回 OK 。
     * 当 key 不是哈希表(hash)类型时，返回一个错误。
     */
    <T> String hmset(final String key, final Map<String, T> hash);
    
     /**
     * 将哈希表 key 中的域 field 的值设为 value 。
     * <p>
     * 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。
     * <p>
     * 如果域 field 已经存在于哈希表中，旧值将被覆盖
     * @param key
     * @param field
     * @param value
     * @return 如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。
     *           如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 
     */
    Long hset(final String key, final String field, final Object value);
    
    
    /**
     * 将哈希表 key 中的域 field 的值设置为 value ，当且仅当域 field 不存在。
     * 若域 field 已经存在，该操作无效。
     * 如果 key 不存在，一个新哈希表被创建并执行 HSETNX 命令。
     * @param key
     * @param field
     * @param value
     * @return 设置成功，返回 1 。
     *          如果给定域已经存在且没有操作被执行，返回 0 。
     */
    Long hsetnx(final String key, final String field, final Object value);
    
    
    /**
     * 返回哈希表 key 中所有域的值。
     * <p>
     * <b>Time complexity:</b> O(N), where N is the total number of entries
     * @param key
     * @return 一个包含哈希表中所有值的表。
     *          当 key 不存在时，返回一个空表。
     */
    <T> List<T> hvals(Class<T> clazz,final String key);
    
}
