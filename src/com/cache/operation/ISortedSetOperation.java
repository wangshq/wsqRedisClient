package com.cache.operation;

import java.util.Set;

/**
 * 
 * @author LQ
 *
 */
public interface ISortedSetOperation {
    /**
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中
     * @param key
     * @param score
     * @param member
     * @return 被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员
     */
    Long zadd(String key, double score, Object member);

    /**
     * 返回有序集 key 的基数
     * @param key
     * @return 当 key 存在且是有序集类型时，返回有序集的基数
     *         当 key 不存在时，返回 0 
     */
    Long zcard(String key);

    /**
     * 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量
     * 关于参数 min 和 max 的详细使用方法，请参考 ZRANGEBYSCORE 命令
     * @param key
     * @param min
     * @param max
     * @return score 值在 min 和 max 之间的成员的数量
     */
    Long zcount(String key, double min, double max);

    /**
     * 为有序集 key 的成员 member 的 score 值加上增量 increment 。
     * 可以通过传递一个负数值 increment ，让 score 减去相应的值，比如 ZINCRBY key -5 member ，就是让 member 的 score 
     * 值减去 5 
     * 当 key 不存在，或 member 不是 key 的成员时， ZINCRBY key increment member 等同于 ZADD key increment member
     * 当 key 不是有序集类型时，返回一个错误
     * score 值可以是整数值或双精度浮点数。
     * @param key
     * @param score
     * @param member
     * @return member 成员的新 score 值，以字符串形式表示
     */
    double zincrby(String key, double score, Object member);

    /**
     * 返回有序集 key 中，指定区间内的成员
     * 其中成员的位置按 score 值递增(从小到大)来排序
     * 具有相同 score 值的成员按字典序(lexicographical order )来排列。
     * 如果你需要成员按 score 值递减(从大到小)来排列，请使用 ZREVRANGE 命令
     * @param <T>
     * @param clazz
     * @param key
     * @param start
     * @param end
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表
     */
    <T> Set<T> zrange(Class<T> clazz, String key, long start, long end);

    /**
     * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列
     * 具有相同 score 值的成员按字典序(lexicographical order)来排列(该属性是有序集提供的，不需要额外的计算)。
     * 可选的 LIMIT 参数指定返回结果的数量及区间(就像SQL中的 SELECT LIMIT offset, count )，注意当 offset 很大时，定位 
     * offset 的操作可能需要遍历整个有序集，此过程最坏复杂度为 O(N) 时间。
     * 
     * @param <T>
     * @param clazz
     * @param key
     * @param min
     * @param max
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表
     */
    <T> Set<T> zrangeByScore(Class<T> clazz, String key, double min, double max);

    /**
     * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从小到大)顺序排列。
     * 排名以 0 为底，也就是说， score 值最小的成员排名为 0
     * 使用 ZREVRANK 命令可以获得成员按 score 值递减(从大到小)排列的排名
     * @param key
     * @param member
     * @return 如果 member 是有序集 key 的成员，返回 member 的排名
     *         如果 member 不是有序集 key 的成员，返回 nil 
     */
    Long zrank(String key, Object member);

    /**
     * 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略
     * 当 key 存在但不是有序集类型时，返回一个错误
     * @param key
     * @param members
     * @return 被成功移除的成员的数量，不包括被忽略的成员
     */
    <T> Long zrem(String key, T... members);

    /**
     * 移除有序集 key 中，指定排名(rank)区间内的所有成员。
     * 区间分别以下标参数 start 和 stop 指出，包含 start 和 stop 在内
     * 下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推。
     * 你也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推。
     * @param key
     * @param start
     * @param end
     * @return 被移除成员的数量。
     */
    Long zremrangeByRank(String key, long start, long end);

    /**
     * 移除有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员
     * 自版本2.1.6开始， score 值等于 min 或 max 的成员也可以不包括在内，详情请参见 ZRANGEBYSCORE 命令
     * @param key
     * @param start
     * @param end
     * @return 被移除成员的数量。
     */
    Long zremrangeByScore(String key, double start, double end);

    /**
     * 返回有序集 key 中，指定区间内的成员
     * 其中成员的位置按 score 值递减(从大到小)来排列
     * 具有相同 score 值的成员按字典序的逆序(reverse lexicographical order)排列
     * 除了成员按 score 值递减的次序排列这一点外， ZREVRANGE 命令的其他方面和 ZRANGE 命令一样
     * @param <T>
     * @param clazz
     * @param key
     * @param start
     * @param end
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表
     */
    <T> Set<T> zrevrange(Class<T> clazz, String key, long start, long end);

    /**
     * 返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。有序集成员按 score 值递减(从大到小)的次序排列
     * 具有相同 score 值的成员按字典序的逆序(reverse lexicographical order )排列
     * 除了成员按 score 值递减的次序排列这一点外， ZREVRANGEBYSCORE 命令的其他方面和 ZRANGEBYSCORE 命令一样
     * @param <T>
     * @param clazz
     * @param key
     * @param max
     * @param min
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表。
     */
    <T> Set<T> zrevrangeByScore(Class<T> clazz, String key, double max, double min);

    /**
     * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递减(从大到小)排序
     * 排名以 0 为底，也就是说， score 值最大的成员排名为 0
     * 使用 ZRANK 命令可以获得成员按 score 值递增(从小到大)排列的排名。
     * @param key
     * @param member
     * @return  如果 member 是有序集 key 的成员，返回 member 的排名。
     *          如果 member 不是有序集 key 的成员，返回 nil 
     */
    Long zrevrank(String key, Object member);

    /**
     * 返回有序集 key 中，成员 member 的 score 值
     * 如果 member 元素不是有序集 key 的成员，或 key 不存在，返回 nil 。
     * 
     * @param key
     * @param member
     * @return member 成员的 score 值，以字符串形式表示
     */
    double zscore(String key, Object member);

}
