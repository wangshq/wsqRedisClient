package com.cache.operation;

import java.util.List;

/**
 * ListOperations
 * 
 * @author wangshq
 * @version 1.0
 * 
 */
public interface IListOperation {

    /**
     * 它是 LPOP 命令的阻塞版本，当给定列表内没有任何元素可供弹出的时候，连接将被 BLPOP 命令阻塞，直到等待超时或发现可弹出元素为止。 <br>
     * BLPOP key [key ...] timeout
     * 
     * @param <T>
     *            转换成的对象类型
     * @param clazz
     *            转换成的对象类型
     * @param time
     *            阻塞时间，秒单位
     * @param key
     *            key
     * @return
     * @author 张卫东
     */
    <T> List<T> blpop(Class<T> clazz, int time, String... key);

    /**
     * 它是 RPOP 命令的阻塞版本，当给定列表内没有任何元素可供弹出的时候，连接将被 BRPOP 命令阻塞，直到等待超时或发现可弹出元素为止。 <br>
     * BRPOP key [key ...] timeout
     * 
     * @param <T>
     *            转换成的对象类型
     * @param clazz
     *            转换成的对象类型
     * @param time
     *            阻塞时间，秒单位
     * @param key
     *            key
     * @return
     * @author 张卫东
     */
    <T> List<T> brpop(Class<T> clazz, int time, String... key);

    /**
     * BRPOPLPUSH source destination timeout
     * 
     * BRPOPLPUSH 是 RPOPLPUSH 的阻塞版本，当给定列表 source 不为空时， BRPOPLPUSH 的表现和 RPOPLPUSH
     * 一样。
     * 
     * 当列表 source 为空时， BRPOPLPUSH 命令将阻塞连接，直到等待超时，或有另一个客户端对 source 执行 LPUSH 或
     * RPUSH 命令为止。
     * 
     * 超时参数 timeout 接受一个以秒为单位的数字作为值。超时参数设为 0 表示阻塞时间可以无限期延长(block indefinitely) 。
     * 
     * @param <T>
     * @param source
     * @param destination
     * @param timeout
     * @param cls
     * @return
     * @author 张卫东
     */
    <T> T brpoplpush(String source, String destination, int timeout, Class<T> cls);

    /**
     * LINDEX key index
     * 
     * 返回列表 key 中，下标为 index 的元素。
     * 
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
     * 
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     * 
     * 如果 key 不是列表类型，返回一个错误。
     * 
     * @param <T>
     * @param key
     * @param index
     * @param clazz
     * @return
     * @author 张卫东
     */
    <T> T lindex(String key, long index, Class<T> clazz);

    /**
     * 
     * 序列化问题，无法精准比较字符串
     */
    // void linsert(final String key, final LIST_POSITION where, final Object
    // pivot, final Object value);

    /**
     * LLEN key
     * 
     * 返回列表 key 的长度。
     * 
     * 如果 key 不存在，则 key 被解释为一个空列表，返回 0 .
     * 
     * 如果 key 不是列表类型，返回一个错误。
     * 
     * @param key
     * @return
     * @author 张卫东
     */
    Long llen(String key);

    /**
     * 移除并返回列表 key 的头元素。
     * 
     * @param <T>
     * @param clazz
     * @param key
     * @return
     * @author 张卫东
     */
    <T> T lpop(Class<T> clazz, String key);

    /**
     * 移除并返回列表 key 的尾元素。
     * 
     * @param <T>
     * @param clazz
     * @param key
     * @return
     * @author 张卫东
     */
    <T> T rpop(Class<T> clazz, String key);

    /**
     * LPUSH key value [value ...]
     * 
     * 将一个或多个值 value 插入到列表 key 的表头
     * 
     * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头： 比如说，对空列表 mylist 执行命令 LPUSH
     * mylist a b c ，列表的值将是 c b a ，这等同于原子性地执行 LPUSH mylist a 、 LPUSH mylist b 和
     * LPUSH mylist c 三个命令。
     * 
     * 如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作。
     * 
     * 当 key 存在但不是列表类型时，返回一个错误。
     * 
     * @param <T>
     * @param key
     * @param obj
     * @return
     * @author 张卫东
     */
    <T> Long lpush(final String key, final T... obj);

    /**
     * LPUSHX key value
     * 
     * 将值 value 插入到列表 key 的表头，当且仅当 key 存在并且是一个列表。
     * 
     * 和 LPUSH 命令相反，当 key 不存在时， LPUSHX 命令什么也不做。
     * 
     * @param <T>
     * @param key
     * @param obj
     * @return
     * @author 张卫东
     */
    <T> Long lpushx(final String key, final T... obj);

    /**
     * RPUSH key value [value ...]
     * 
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。
     * 
     * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表尾：比如对一个空列表 mylist 执行 RPUSH mylist
     * a b c ，得出的结果列表为 a b c ，等同于执行命令 RPUSH mylist a 、 RPUSH mylist b 、 RPUSH
     * mylist c 。
     * 
     * 如果 key 不存在，一个空列表会被创建并执行 RPUSH 操作。
     * 
     * 当 key 存在但不是列表类型时，返回一个错误。
     * 
     * @param <T>
     * @param key
     * @param obj
     * @return
     * @author 张卫东
     */
    <T> Long rpush(final String key, final T... obj);

    /**
     * RPUSHX key value
     * 
     * 将值 value 插入到列表 key 的表尾，当且仅当 key 存在并且是一个列表。
     * 
     * 和 RPUSH 命令相反，当 key 不存在时， RPUSHX 命令什么也不做。
     * 
     * @param <T>
     * @param key
     * @param obj
     * @return
     * @author 张卫东
     */
    <T> Long rpushx(final String key, final T... obj);

    /**
     * RPOPLPUSH source destination
     * 
     * 命令 RPOPLPUSH 在一个原子时间内，执行以下两个动作：
     * 
     * 将列表 source 中的最后一个元素(尾元素)弹出，并返回给客户端。 将 source 弹出的元素插入到列表 destination ，作为
     * destination 列表的的头元素。 举个例子，你有两个列表 source 和 destination ， source 列表有元素 a,
     * b, c ， destination 列表有元素 x, y, z ，执行 RPOPLPUSH source destination 之后，
     * source 列表包含元素 a, b ， destination 列表包含元素 c, x, y, z ，并且元素 c 会被返回给客户端。
     * 
     * 如果 source 不存在，值 nil 被返回，并且不执行其他动作。
     * 
     * 如果 source 和 destination
     * 相同，则列表中的表尾元素被移动到表头，并返回该元素，可以把这种特殊情况视作列表的旋转(rotation)操作。
     * 
     * @param <T>
     * @param cls
     * @param srckey
     * @param destKey
     * @return
     * @author 张卫东
     */
    <T> T rpoplpush(Class<T> cls, String srckey, String destKey);

    /**
     * LRANGE key start stop
     * 
     * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。
     * 
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
     * 
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     * 
     * 注意LRANGE命令和编程语言区间函数的区别
     * 
     * 假如你有一个包含一百个元素的列表，对该列表执行 LRANGE list 0 10 ，结果是一个包含11个元素的列表，这表明 stop 下标也在
     * LRANGE 命令的取值范围之内(闭区间)，这和某些语言的区间函数可能不一致，比如Ruby的 Range.new 、 Array#slice
     * 和Python的 range() 函数。
     * 
     * 超出范围的下标
     * 
     * 超出范围的下标值不会引起错误。
     * 
     * 如果 start 下标比列表的最大下标 end ( LLEN list 减去 1 )还要大，那么 LRANGE 返回一个空列表。
     * 
     * 如果 stop 下标比 end 下标还要大，Redis将 stop 的值设置为 end 。
     * 
     * @param <T>
     * @param key
     * @param start
     * @param end
     * @param calzz
     * @return
     * @author 张卫东
     */
    <T> List<T> lrange(String key, long start, long end, Class<T> calzz);

    /**
     * LREM key count value
     * 
     * 根据参数 count 的值，移除列表中与参数 value 相等的元素。
     * 
     * count 的值可以是以下几种：
     * 
     * count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。 count < 0 :
     * 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。 count = 0 : 移除表中所有与 value
     * 相等的值。
     * 
     * @param key
     * @param count
     * @param value
     * @return
     * @author 张卫东
     */
    Long lrem(String key, long count, Object value);

    /**
     * LSET key index value
     * 
     * 将列表 key 下标为 index 的元素的值设置为 value 。
     * 
     * 当 index 参数超出范围，或对一个空列表( key 不存在)进行 LSET 时，返回一个错误。
     * 
     * 关于列表下标的更多信息，请参考 LINDEX 命令。
     * 
     * @param key
     * @param index
     * @param value
     * @return
     * @author 张卫东
     */
    String lset(String key, long index, Object value);

    /**
     * LTRIM key start stop
     * 
     * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
     * 
     * 举个例子，执行命令 LTRIM list 0 2 ，表示只保留列表 list 的前三个元素，其余元素全部删除。
     * 
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
     * 
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     * 
     * 当 key 不是列表类型时，返回一个错误。
     * 
     * LTRIM 命令通常和 LPUSH 命令或 RPUSH 命令配合使用，举个例子：
     * 
     * LPUSH log newest_log LTRIM log 0 99 这个例子模拟了一个日志程序，每次将最新日志 newest_log 放到
     * log 列表中，并且只保留最新的 100 项。注意当这样使用 LTRIM 命令时，时间复杂度是O(1)，因为平均情况下，每次只有一个元素被移除。
     * 
     * 注意LTRIM命令和编程语言区间函数的区别
     * 
     * 假如你有一个包含一百个元素的列表 list ，对该列表执行 LTRIM list 0 10 ，结果是一个包含11个元素的列表，这表明 stop
     * 下标也在 LTRIM 命令的取值范围之内(闭区间)，这和某些语言的区间函数可能不一致，比如Ruby的 Range.new 、
     * Array#slice 和Python的 range() 函数。
     * 
     * 超出范围的下标
     * 
     * 超出范围的下标值不会引起错误。
     * 
     * 如果 start 下标比列表的最大下标 end ( LLEN list 减去 1 )还要大，或者 start > stop ， LTRIM
     * 返回一个空列表(因为 LTRIM 已经将整个列表清空)。
     * 
     * 如果 stop 下标比 end 下标还要大，Redis将 stop 的值设置为 end 。
     * 
     * @param key
     * @param start
     * @param end
     * @return
     * @author 张卫东
     */
    String ltrim(final String key, final long start, final long end);

}
