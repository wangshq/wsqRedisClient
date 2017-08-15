package com.cache.utils;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JacksonJsonSerializer
 * @author wangshq
 * @version 1.0
 *
 */
public class JacksonJsonSerializer implements ISerializerUtil{

    private static final Log LOG = LogFactory.getLog(JacksonJsonSerializer.class);
    
    private static ObjectMapper mapper = new ObjectMapper();
    static{
        mapper.configure(MapperFeature.REQUIRE_SETTERS_FOR_GETTERS, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    }
    
    
    @Override
    public String bean2String(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOG.error("使用JacksonJson序列化失败：", e);
        }
        return null;
    }

    @Override
    public <T> T string2Bean(String str, Class<T> clazz) {
        try {
            return mapper.readValue(str, clazz);
        } catch (IOException e) {
            LOG.error("使用JacksonJson反序列化失败：", e);
        }
        return null;
    }

}
