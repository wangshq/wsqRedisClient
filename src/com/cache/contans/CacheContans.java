
package com.cache.contans;





/**
 * NoramlContans
 * @author wangshq
 * @version 1.0
 *
 */
public class CacheContans {
    
    /**key值的连接符*/
    
    public static String CONTANCT = ".";
    /**读写权限：只读 */
    public static final int MODE_READ_ONLY = 1;
    
    /**读写权限：读写 */
    public static final int MODE_READ_AND_WRITE = 2;
    
    
    public static final String REGISTER_KEY = "register";
    
    public static final String CONFIG_NAME = "props.config";
    
    public static final String CONFIG_REGKEY = "redis.registerKeys";
    
    public static final String CONFIG_SERVER_NAME = "redis.serverName";
    
}
