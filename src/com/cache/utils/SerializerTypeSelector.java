package com.cache.utils;

import org.apache.commons.lang.StringUtils;

/**
 * SerializerTypeSelector
 * @author wangshq
 * @version 1.0
 *
 */
public class SerializerTypeSelector {

    /**
     * @param sERIALIZER_TYPE
     * @return
     * @throws Exception 
     */
    public static ISerializerUtil getSerializerUtil(String sERIALIZER_TYPE){
        if(StringUtils.startsWithIgnoreCase(sERIALIZER_TYPE,"fastJson")){
            return new FastJsonSerializer();
        }else if(StringUtils.startsWithIgnoreCase(sERIALIZER_TYPE, "jackson")){
            return new JacksonJsonSerializer();
        }else {
            return null;
        }
    }

    
}
