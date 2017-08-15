package com.cache.client;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;

import com.cache.contans.CacheContans;
import com.cache.operation.impl.HashOperation;
import com.cache.operation.impl.KeyOperation;
import com.cache.operation.impl.ListOperation;
import com.cache.operation.impl.PipelineOperation;
import com.cache.operation.impl.SetOperation;
import com.cache.operation.impl.SortedSetOperation;
import com.cache.operation.impl.StringOperation;
import com.cache.redis.RedisPool;
import com.cache.utils.ConvertUtil;

/**
 * 使用方法，详见
 * 
 * @see {@link com.test.CacheClientDemo}
 * 
 * @author wangshq
 * @version 1.0
 * 
 */
public class CacheManagerClient {

    
    private static final Log LOG = LogFactory.getLog(CacheManagerClient.class);

    // 已经注册的过的信息
    private static Map<String, Integer> REGISTER_INFO = new HashMap<String, Integer>();

    // 需要使用的operation
    private static Map<String, HashOperation> operationHash = new HashMap<String, HashOperation>();
    private static Map<String, ListOperation> operationList = new HashMap<String, ListOperation>();
    private static Map<String, SetOperation> operationSet = new HashMap<String, SetOperation>();
    private static HashMap<String, SortedSetOperation> operationSortSet = new HashMap<String, SortedSetOperation>();
    private static Map<String, StringOperation> operationString = new HashMap<String, StringOperation>();
    private static HashMap<String, KeyOperation> operationKey = new HashMap<String, KeyOperation>();

    /**
     * 获得注册过的信息
     * 
     * @return
     */
    public static Map<String, Integer> getRegtedInfo() {
        return REGISTER_INFO;
    }

    /**
     * 获得MapOperation
     * 
     * @param key
     * @return
     */
    public static HashOperation getHashOperation(String key) {
        return operationHash.get(key);
    }

    /**
     * 获得ListOperation
     * 
     * @param key
     * @return
     */
    public static ListOperation getListOperation(String key) {
        return operationList.get(key);
    }

    /**
     * 获得SetOperation
     * 
     * @param key
     * @return
     */
    public static SetOperation getSetOperation(String key) {
        return operationSet.get(key);
    }

    /**
     * 获得StringOperation
     * 
     * @param key
     * @return
     */
    public static SortedSetOperation getSortedSetOperation(String key) {
        return operationSortSet.get(key);
    }

    /**
     * 获得StringOperation
     * 
     * @param key
     * @return
     */
    public static KeyOperation getKeyOperation(String key) {
        return operationKey.get(key);
    }

    /**
     * 获得StringOperation
     * 
     * @param key
     * @return
     */
    public static StringOperation getStringOperation(String key) {
        return operationString.get(key);
    }

    /**
     * 获得管道（rdeis的批量）操作，在需要执行多次命令的时候可以使用此操作，但要注意以下几点：
     *      1 pipelineOperation没有做key的注册和模式管理，所以在获取operation的时候最好事先已经确认这个key是已经注册的
     *      2 我们封装了大部分的“写”方法，大部分的“读”方法没有包含
     * @param key
     * @return
     */
    public static PipelineOperation getPipelineOperation(String key) {
        return new PipelineOperation(key, REGISTER_INFO.get(key));
    }

    /**
     * 向redis里注册
     * 
     * @param registerKey   注册的key值，向redis里注册，在后面使用的时候一定是已经注册过的key值
     * @param mode 模式 1 只读 2 读写 
     * @param serverName 服务名 ：用于注册时向redis里写入信息用
     */
    public static void registerKey(String registerKey, int mode,
            String serverName) {
        try {
            // 向redis里注册
            registerKeyToRedis(registerKey, serverName);

            // 把注册的信息放到内存中
            if (REGISTER_INFO.containsKey(registerKey)) {
                LOG.info("【registerKey：" + registerKey + "】【model：" + mode
                        + "】已经注册！");
                return;
            }
            REGISTER_INFO.put(registerKey, mode);

            // 初始化operation
            initOperation(registerKey, mode);

            LOG.info("注册缓存成功【registerKey：" + registerKey + "】【model：" + mode
                    + "】");
        } catch (Exception e) {
            LOG.error("注册缓存失败【registerKey：" + registerKey + "】【model：" + mode
                    + "】", e);
        }
    }

    /**
     * 注册信息写道redis缓存中
     * 
     * @param registerKey
     *            注册的key
     * @throws Exception
     */
    private static void registerKeyToRedis(String registerKey, String serverName)
            throws Exception {

        String lastKey = CacheContans.REGISTER_KEY + CacheContans.CONTANCT
                + registerKey;

        Jedis jedis = RedisPool.INSTANCE.getMasterJedis(); // 此处不使用代理

        try {

            if (jedis.exists(lastKey)) {
                Set<String> allHk = jedis.hkeys(lastKey);
                StringBuffer info = new StringBuffer();
                for (String string : allHk) {
                    info.append(string).append(",");
                }
                LOG.warn("此key:" + lastKey + " 已在 " + info.toString() + " 里注册");
            }

            InetAddress address = InetAddress.getLocalHost();
            jedis.hset(lastKey, serverName + "_" + address.getHostAddress(),
                    registInfo(serverName, address));
        } finally {
            jedis.close();
        }
    }

    /**
     * 生成注册信息
     * @param serverName
     * @param address
     * @return
     */
    private static String registInfo(String serverName, InetAddress address) {
        return "【服务名：" + serverName + "】【ip：" + address.getHostAddress()
                + "】【主机名：" + address.getHostName() + "】【最后操作时间："
                + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")
                + "】";
    }

    /**
     * 初始化操作类
     * @param regInfo
     * @param key
     */
    private static void initOperation(String key, Integer right) {
        HashOperation mOperation = new HashOperation(key, right);
        ListOperation lOperation = new ListOperation(key, right);
        SetOperation setOperation = new SetOperation(key, right);
        StringOperation sOperation = new StringOperation(key, right);
        SortedSetOperation sortSetOperation = new SortedSetOperation(key, right);
        KeyOperation keyOperation = new KeyOperation(key, right);

        operationHash.put(key, mOperation);
        operationList.put(key, lOperation);
        operationSet.put(key, setOperation);
        operationString.put(key, sOperation);
        operationSortSet.put(key, sortSetOperation);
        operationKey.put(key, keyOperation);
    }

    public static void setSerializerType(String serializerType){
        ConvertUtil.SERIALIZER_TYPE = serializerType;
    }
    /**
     * 设置redis缓存池，使用默认配置
     * @param IP 地址
     * @param port 端口
     * @param AUTH 密码
     */
    public static void initRedisPool(String IP, String port, String AUTH) {
        RedisPool.INSTANCE.setJedisPool(IP, port, AUTH);
    }

    /**
     * 设置redis的连接池
     * @param maxAcive redis连接池的最大连接数
     * @param maxIdle redis连接池最大能够保持idel状态的对象数
     * @param maxWait 当池内没有返回对象时，最大等待时间
     * @param port redis服务的端口号
     * @param testOnBorrow 当调用borrow Object方法时，是否进行有效性检查（true时不检查）
     * @param timeout 客户端连接过期时间
     * @param IP redis服务的IP地址
     * @param AUTH 密码
     */
    public static void initRedisPool(String IP, String port, String AUTH,
            String timeout, String maxAcive, String maxIdle, String maxWait,
            boolean testOnBorrow) {
        RedisPool.INSTANCE.setJedisPool(IP, port, AUTH, timeout, maxAcive,
                maxIdle, maxWait, testOnBorrow);
    }

    /**
     * 通过配置文件来设置redis连接池
     */
    public static void initRedisPoolByConfig() {
        RedisPool.INSTANCE.setJedisPoolByConfig();
    }
}
