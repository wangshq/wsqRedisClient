package com.test.kryo;
//package com.thunisoft.test.kryo;
//
//import io.protostuff.JsonIOUtil;
//import io.protostuff.LinkedBuffer;
//import io.protostuff.ProtostuffIOUtil;
//import io.protostuff.Schema;
//import io.protostuff.runtime.RuntimeSchema;
//
//import java.io.IOException;
//import java.util.List;
//
//import org.apache.commons.codec.binary.Base64;
//import org.apache.commons.io.output.ByteArrayOutputStream;
//import org.codehaus.jackson.JsonFactory;
//
//import com.alibaba.fastjson.JSON;
//import com.esotericsoftware.kryo.Kryo;
//import com.esotericsoftware.kryo.Registration;
//import com.esotericsoftware.kryo.io.Input;
//import com.thunisoft.test.utils.TestBean;
//
//public class ProtostuffTest {
//    static Schema schema;
//
//    @SuppressWarnings("unchecked")
//    public static byte[] bean2Byte(Object bean) throws Exception {
//
//        if (schema == null) {
//            schema = RuntimeSchema.getSchema(TestBean.class);
//        }
//
//        LinkedBuffer buffer = LinkedBuffer.allocate(4096);
//
//        byte[] protostuff = null;
//        try {
//            protostuff = ProtostuffIOUtil.toByteArray(bean, schema, buffer);
//        } finally {
//            buffer.clear();
//        }
//        Base64.encodeBase64String(protostuff);
//        return protostuff;
//    }
//
//    public static <T> T byte2Bean(byte bb, Class<T> c) {
//        Kryo kryo = new Kryo();
//        Registration registration = kryo.register(c);
//
//        Input input = new Input(bb);
//        T s = (T) kryo.readObject(input, registration.getType());
//        input.close();
//        return s;
//    }
//
//    public static String bean2String(Object obj) throws IOException {
//        if (schema == null) {
//            schema = RuntimeSchema.getSchema(TestBean.class);
//        }
//
//        LinkedBuffer buffer = LinkedBuffer.allocate(4096);
//        JsonFactory jfactory = new JsonFactory();
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//
//        byte[] protostuff = null;
//        try {
//            JsonIOUtil.writeTo(os, obj, schema, true);
//            new String(os.toByteArray());
//        } finally {
//            buffer.clear();
//        }
//        // Base64.encodeBase64String(protostuff);
//        return "";
//    }
//
//    public static <T> T string2Bean(String str, Class<T> clazz) {
//        return JSON.parseObject(str, clazz);
//    }
//
//    public static void main(String[] args) throws Exception {
//
//        List<TestBean> list = TestBean.randomBean(100000);
//
//        // kryo.setReferences(true);
//        // kryo.setRegistrationRequired(true);
//        // kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
//        // 注册类
//
//        long time = System.currentTimeMillis();
//
//        for (TestBean bean : list) {
//
//            // 序列化
//            bean2Byte(bean);
//            // 反序列化
//            // string2Bean(str, TestBean.class);
//
//        }
//        time = System.currentTimeMillis() - time;
//        System.out.println("time:" + time);
//    }
//}
