package com.cache.utils;

import org.apache.commons.lang.StringUtils;

import com.cache.contans.CacheContans;

/**
 * 修改redis的key工具类，规则封装，参数修改等 <br>
 * 
 * @author 张卫东
 * @version 1.0
 * @date 2016-6-14
 */
public class KeyUtils {
    public static void main(String[] args) {
        System.out.println(0/0);
    }
    /**
     * 生成最终key
     * 
     * @param registerKey
     *            注册前缀
     * @param key
     *            原始key
     * @return
     * @author 张卫东
     */
    public static String generateKey(String registerKey, String key) {
        if (StringUtils.isBlank(key)) {
            return registerKey + CacheContans.CONTANCT;
        }
        return registerKey + CacheContans.CONTANCT + key;
    }
    
    /**
     * @param keys
     * @return
     */
    public static String[] generateKeys(String registerKey,String... keys) {
        String[] retkeys = new String[keys.length];
        for (int i = 0; i < retkeys.length; i++) {
            retkeys[i] = KeyUtils.generateKey(registerKey, keys[i]);
        }
        return retkeys;
    }

}
