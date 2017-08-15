package com.cache.utils;

import com.alibaba.fastjson.JSON;

/**
 * FastJsonSerializer
 * @author wangshq
 * @version 1.0
 *
 */
public class FastJsonSerializer implements ISerializerUtil{

    @Override
    public String bean2String(Object obj) {
        return JSON.toJSONString(obj);
    }

    @Override
    public <T> T string2Bean(String str, Class<T> clazz) {
        return JSON.parseObject(str, clazz);
    }

}
