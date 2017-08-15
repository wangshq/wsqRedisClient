package com.cache.operation;

import java.util.List;
import java.util.Set;

/**
 * ISetOperation
 * @author wujch
 * @version 1.0
 *
 */
public interface ISetOperation {

    /**
     * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略
     * 假如 key 不存在，则创建一个只包含 member 元素作成员的集合
     * 当 key 不是集合类型时，返回一个错误
     * @param key
     * @param members
     * @return  被添加到集合中的新元素的数量，不包括被忽略的元素
     */
    <T> Long sadd(String key, T... members);

    /**
     * 返回集合 key 的基数(集合中元素的数量)。
     * @param key
     * @return 集合的基数 当 key 不存在时，返回 0 
     */
    Long scard(String key);

    /**
     * 返回一个集合的全部成员，该集合是所有给定集合之间的差集
     * 不存在的 key 被视为空集
     * @param <T>
     * @param clazz
     * @param keys
     * @return 交集成员的列表
     */
    <T> Set<T> sdiff(Class<T> clazz, String... keys);

    /**
     * 这个命令的作用和 SDIFF 类似，但它将结果保存到 destination 集合，而不是简单地返回结果集
     * 如果 destination 集合已经存在，则将其覆盖。
     * destination 可以是 key 本身
     * @param dstkey
     * @param keys
     * @return 结果集中的元素数量
     */
    Long sdiffstore(String dstkey, String... keys);

    /**
     * 返回一个集合的全部成员，该集合是所有给定集合的交集
     * 不存在的 key 被视为空集
     * 当给定集合当中有一个空集时，结果也为空集(根据集合运算定律)。
     * @param <T>
     * @param clazz
     * @param keys
     * @return 交集成员的列表
     */
    <T> Set<T> sinter(Class<T> clazz, String... keys);

    /**
     * 这个命令类似于 SINTER 命令，但它将结果保存到 destination 集合，而不是简单地返回结果集
     * 如果 destination 集合已经存在，则将其覆盖
     * destination 可以是 key 本身
     * @param dstkey
     * @param keys
     * @return 结果集中的成员数量
     */
    Long sinterstore(String dstkey, String... keys);

    /**
     * 判断 member 元素是否集合 key 的成员
     * 
     * @param key
     * @param member
     * @return 如果 member 元素是集合的成员，返回 1 
     *         如果 member 元素不是集合的成员，或 key 不存在，返回 0 
     */
    boolean sismember(String key, Object member);

    /**
     * 返回集合 key 中的所有成员
     * 不存在的 key 被视为空集合
     * @param <T>
     * @param clazz
     * @param key
     * @return 集合中的所有成员
     */
    <T> Set<T> smembers(Class<T> clazz, String key);

    /**
     *  将 member 元素从 source 集合移动到 destination 集合
     * @param srckey
     * @param dstkey
     * @param member
     * @return 如果 member 元素被成功移除，返回 1 
     *         如果 member 元素不是 source 集合的成员，并且没有任何操作对 destination 集合执行，那么返回 0
     */
    Long smove(String srckey, String dstkey, Object member);

    /**
     * 移除并返回集合中的一个随机元素
     * 如果只想获取一个随机元素，但不想该元素从集合中被移除的话，可以使用 SRANDMEMBER 命令
     * @param key
     * @return 被移除的随机元素
     *         当 key 不存在或 key 是空集时，返回 nil 
     */
    <T> T spop(Class<T> clazz, String key);

    /**
     * 如果命令执行时，只提供了 key 参数，那么返回集合中的一个随机元素
     * 
     * @param <T>
     * @param clazz
     * @param key
     * @param count
     * @return 只提供 key 参数时，返回一个元素；如果集合为空，返回 nil 。
     *         如果提供了 count 参数，那么返回一个数组；如果集合为空，返回空数组
     */
    <T> List<T> srandmember(Class<T> clazz, String key, int count);

    /**
     * 从一个集返回一个随机元素，无需拆卸元件。如果设置为空或
      *键不存在，则返回零对象
     * @param key
     * @return
     */
    <T> T srandmember(Class<T> clazz, String key);

    /**
     * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。
     * 当 key 不是集合类型，返回一个错误
     * @param key
     * @param members
     * @return 被成功移除的元素的数量，不包括被忽略的元素
     */
    <T> Long srem(String key, T... members);

    /**
     * 返回一个集合的全部成员，该集合是所有给定集合的并集。
     * 不存在的 key 被视为空集
     * @param <T>
     * @param clazz
     * @param keys
     * @return 并集成员的列表
     */
    <T> Set<T> sunion(Class<T> clazz, String... keys);

    /**
     * 这个命令类似于 SUNION 命令，但它将结果保存到 destination 集合，而不是简单地返回结果集
     * 如果 destination 已经存在，则将其覆盖。
     * destination 可以是 key 本身。
     * @param dstkey
     * @param keys
     * @return 结果集中的元素数量
     */
    Long sunionstore(String dstkey, String... keys);

}
