package com.cache.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * ConvertUtil
 * @author wangshq
 * @version 1.0
 *
 */
public class ConvertUtil {
    
    public static String SERIALIZER_TYPE = "fastJson";
    
    
    public static String bean2String(Object obj) {
        return SerializerTypeSelector.getSerializerUtil(SERIALIZER_TYPE).bean2String(obj);
    }
    
    public static <T> T string2Bean (String str,Class<T> clazz){
        if(str == null){
            return null;
        }
        return SerializerTypeSelector.getSerializerUtil(SERIALIZER_TYPE).string2Bean(str, clazz);
    }
    
    /**
     * @param <T>
     * @param clazz
     * @param values
     * @return
     */
    public static <T> List<T> string2BeanList(Class<T> clazz, List<String> values) {
        List<T> returnValues = new ArrayList<T>();
        for(String value : values){
            T t = ConvertUtil.string2Bean(value, clazz);
            if(t == null){
                continue;
            }
            returnValues.add(ConvertUtil.string2Bean(value, clazz));
        }
        return returnValues;
    }
    
    
    /**
     * @param <T>
     * @param smembers
     * @return
     */
    public static <T> Set<T> string2ObjSet(Set<String> smembers,Class<T> clazz) {
        Set<T> returnSet = new HashSet<T>();
        for (Iterator<String> iterator = smembers.iterator(); iterator.hasNext();) {
            String vaule = iterator.next();
            returnSet.add(string2Bean(vaule,clazz));
        }
        return returnSet;
    }
    
    
    /**
     * @param values
     * @return
     */
    public static String[] bean2StringArray(Object... values) {
        String[] returnValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            returnValues[i] = bean2String(values[i]);
        }
        return returnValues;
    }

    /**
     * @param <T>
     * @param hash
     * @return
     */
    public static <T> Map<String, String> bean2StringMap(Map<String, T> hash) {
        
        Map<String, String> returnMap = new HashMap<String, String>();
        for (Iterator<String> iterator = hash.keySet().iterator(); iterator.hasNext();) {
            String key = iterator.next();
            if(StringUtils.isBlank(key)){
                continue;
            }
            Object value = hash.get(key);
            if(value instanceof String){
                returnMap.put(key, (String) value);
            }else {
                returnMap.put(key, bean2String(value));
            }
        }
        return returnMap;
    }

    /**
     * @param <T>
     * @param values
     * @return
     */
    public static <T> Map<String, T> string2BeanMap(Map<String, String> m ,Class<T> clazz) {
        Map<String, T> returnMap = new HashMap<String, T>();
        for (Iterator<String> iterator = m.keySet().iterator(); iterator.hasNext();) {
            String kValue = iterator.next();
            returnMap.put(kValue, string2Bean(m.get(kValue),clazz));
        }
        return returnMap;
    }

}
