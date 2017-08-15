package com.cache.operation;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.SortingParams;


/**
 * IKeyOperation
 * 
 * @author wangshq
 * @version 1.0
 * 
 */
public interface IKeyOperation {

    /**
     * 删除给定的一个或多个 key 。 不存在的 key 会被忽略。
     * 
     * @param keys
     * @return 被删除 key 的数量。
     */
    Long del(final String... keys);

    /**
     * 序列化给定 key ，并返回被序列化的值，使用 RESTORE 命令可以将这个值反序列化为 Redis 键。
     * 
     * @param key
     * @return
     */
    byte[] dump(final String key);

    /**
     * 检查给定 key 是否存在
     * 
     * @param key
     * @return Boolean reply, true if the key exists, otherwise false
     */
    Boolean exists(final String key);

    /**
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。 在 Redis 中，带有生存时间的 key
     * 被称为『易失的』(volatile)。
     * <p>
     * 生存时间可以通过使用 DEL 命令来删除整个 key 来移除，或者被 SET 和 GETSET 命令覆写(overwrite)，这意味着，
     * 如果一个命令只是修改(alter)一个带生存时间的 key 的值而不是用一个新的 key
     * 值来代替(replace)它的话，那么生存时间不会被改变。
     * <p>
     * 比如说，对一个 key 执行 INCR 命令，对一个列表进行 LPUSH 命令，或者对一个哈希表执行 HSET 命令，这类操作都不会修改 key
     * 本身的生存时间。 可以对一个已经带有生存时间的 key 执行 EXPIRE 命令，新指定的生存时间会取代旧的生存时间。
     * <p>
     * Time complexity: O(1)
     * 
     * @see <ahref="http://code.google.com/p/redis/wiki/ExpireCommand">ExpireCommand</a>
     * @param key
     * @param seconds
     * @return 设置成功返回 1 。 当 key 不存在或者不能为 key 设置生存时间时(比如在低于 2.1.3 版本的 Redis
     *         中你尝试更新 key 的生存时间)，返回 0 。
     */
    Long expire(final String key, final int seconds);

    /**
     * EXPIREAT 的作用和 EXPIRE 类似，都用于为 key 设置生存时间。 不同在于 EXPIREAT 命令接受的时间参数是 UNIX
     * 时间戳(unix timestamp)。
     * 
     * @param key
     * @param unixTime
     * @return如果生存时间设置成功，返回 1 。 当 key 不存在或没办法设置生存时间，返回 0 。
     */
    Long expireAt(final String key, final long unixTime);

    /**
     * 移除给定 key 的生存时间，将这个 key 从『易失的』(带生存时间 key )转换成『持久的』(一个不带生存时间、永不过期的 key )。
     * 
     * @param key
     * @return 当生存时间移除成功时，返回 1 . 如果 key 不存在或 key 没有设置生存时间，返回 0 。
     */
    Long persist(final String key);

    /**
     * 这个命令和 EXPIRE 命令的作用类似，但是它以毫秒为单位设置 key 的生存时间，而不像 EXPIRE 命令那样，以秒为单位。
     * 
     * @param key
     * @param milliseconds
     * @return 设置成功，返回 1 key 不存在或设置失败，返回 0
     */
    Long pexpire(final String key, final long milliseconds);

    /**
     * 这个命令和 EXPIREAT 命令类似，但它以毫秒为单位设置 key 的过期 unix 时间戳，而不是像 EXPIREAT 那样，以秒为单位。
     * 
     * @param key
     * @param millisecondsTimestamp
     * @return 如果生存时间设置成功，返回 1 。 当 key 不存在或没办法设置生存时间时，返回 0 。(查看 EXPIRE 命令获取更多信息)
     */
    Long pexpireAt(final String key, final long millisecondsTimestamp);

    /**
     * 这个命令类似于 TTL 命令，但它以毫秒为单位返回 key 的剩余生存时间，而不是像 TTL 命令那样，以秒为单位。
     * 
     * @param key
     * @return 当 key 不存在时，返回 -2 当 key 存在但没有设置剩余生存时间时，返回 -1 。 否则，以毫秒为单位，返回 key
     *         的剩余生存时间。
     */
    Long pttl(final String key);

    /**
     * 从当前数据库中随机返回(不删除)一个 key 。
     * 
     * @return 当数据库不为空时，返回一个 key 。 当数据库为空时，返回 nil 。
     */
    String randomKey();

    /**
     * 将 key 改名为 newkey 当 key 和 newkey 相同，或者 key 不存在时，返回一个错误。 当 newkey 已经存在时，
     * RENAME 命令将覆盖旧值。
     * <p>
     * Time complexity: O(1)
     * 
     * @param oldkey
     * @param newkey
     * @return 改名成功时提示 OK ，失败时候返回一个错误。
     */
    String rename(final String oldkey, final String newkey);

    /**
     * 当且仅当 newkey 不存在时，将 key 改名为 newkey 当 key 不存在时，返回一个错误。
     * 
     * @param oldkey
     * @param newkey
     * @return 修改成功时，返回 1 。 如果 newkey 已经存在，返回 0 。
     */
    Long renamenx(final String oldkey, final String newkey);

    /**
     * 反序列化给定的序列化值，并将它和给定的 key 关联。
     * 
     * @param key
     * @param ttl
     * @param serializedValue
     * @return 如果反序列化成功那么返回 OK ，否则返回一个错误。
     */
    String restore(final String key, final int ttl, final byte[] serializedValue);

    /**
     * 以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)
     * 
     * @param key
     * @return 当 key 不存在时，返回 -2 。 当 key 存在但没有设置剩余生存时间时，返回 -1 。 否则，以秒为单位，返回 key
     *         的剩余生存时间。
     */
    Long ttl(final String key);

    /**
     * 返回 key 所储存的值的类型。
     * 
     * @param key
     * @return none (key不存在) string (字符串) list (列表) set (集合) zset (有序集) hash
     *         (哈希表)
     */
    String type(final String key);
    
    /**
     * 返回或保存给定列表、集合、有序集合 key 中经过排序的元素。
     * <p>
     * 排序默认以数字作为对象，值被解释为双精度浮点数，然后进行比较。
     * @param key
     * @return Assuming the Set/List at key contains a list of numbers, the return value will be the
     *         list of numbers ordered from the smallest to the biggest number.
     */
    public <T> List<T> sort(Class<T> clazz,final String key);
    
    
    /**
     * 返回或保存给定列表、集合、有序集合 key 中经过排序的元素
     * <p>
     * <b>examples:</b>
     * <p>
     * Given are the following sets and key/values:
     * 
     * <pre>
     * x = [1, 2, 3]
     * y = [a, b, c]
     * 
     * k1 = z
     * k2 = y
     * k3 = x
     * 
     * w1 = 9
     * w2 = 8
     * w3 = 7
     * </pre>
     * 
     * Sort Order:
     * 
     * <pre>
     * sort(x) or sort(x, sp.asc())
     * -> [1, 2, 3]
     * 
     * sort(x, sp.desc())
     * -> [3, 2, 1]
     * 
     * sort(y)
     * -> [c, a, b]
     * 
     * sort(y, sp.alpha())
     * -> [a, b, c]
     * 
     * sort(y, sp.alpha().desc())
     * -> [c, a, b]
     * </pre>
     * 
     * Limit (e.g. for Pagination):
     * 
     * <pre>
     * sort(x, sp.limit(0, 2))
     * -> [1, 2]
     * 
     * sort(y, sp.alpha().desc().limit(1, 2))
     * -> [b, a]
     * </pre>
     * 
     * Sorting by external keys:
     * 
     * <pre>
     * sort(x, sb.by(w*))
     * -> [3, 2, 1]
     * 
     * sort(x, sb.by(w*).desc())
     * -> [1, 2, 3]
     * </pre>
     * 
     * Getting external keys:
     * 
     * <pre>
     * sort(x, sp.by(w*).get(k*))
     * -> [x, y, z]
     * 
     * sort(x, sp.by(w*).get(#).get(k*))
     * -> [3, x, 2, y, 1, z]
     * </pre>
     * @see #sort(String)
     * @see #sort(String, SortingParams, String)
     * @param key
     * @param sortingParameters
     * @return a list of sorted elements.
     */
    public <T> List<T> sort(Class<T> clazz, final String key, final SortingParams sortingParameters);

    /**
     * 删除以keyHead开头的key
     * tip:如果想删除以注册key的所有值，那么传入一个null即可
     * @param keyHead
     */
    
    public long deleteHeadKeyDatas(final String keyHead);
    /**
     * 
     */
    public Set<String> keys(String keyhead);
}
