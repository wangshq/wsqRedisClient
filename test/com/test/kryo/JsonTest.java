package com.test.kryo;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.test.utils.TestBean;

public class JsonTest {

    public static String bean2String(Object obj) {
        return JSON.toJSONString(obj);
    }

    public static <T> T string2Bean(String str, Class<T> clazz) {
        return JSON.parseObject(str, clazz);
    }

    public static void main(String[] args) {

        List<TestBean> list = TestBean.randomBean(100000);

        // kryo.setReferences(true);
        // kryo.setRegistrationRequired(true);
        // kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
        // 注册类

        long time = System.currentTimeMillis();

        for (TestBean bean : list) {

            // 序列化
            String str = bean2String(bean);
            // 反序列化
            //string2Bean(str, TestBean.class);

        }
        time = System.currentTimeMillis() - time;
    }
}
