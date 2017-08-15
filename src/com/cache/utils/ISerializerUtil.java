package com.cache.utils;

/**
 * ISerializerUtil
 * @author wangshq
 * @version 1.0
 *
 */
public interface ISerializerUtil {

    String bean2String(Object obj);
    
    <T> T string2Bean (String str,Class<T> clazz);
}
