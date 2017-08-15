package com.test.kryo;
//package com.thunisoft.test.kryo;
//
//import java.sql.Timestamp;
//import java.util.List;
//
//import org.apache.commons.codec.binary.Base64;
//import org.objenesis.strategy.StdInstantiatorStrategy;
//
//import com.esotericsoftware.kryo.Kryo;
//import com.esotericsoftware.kryo.Registration;
//import com.esotericsoftware.kryo.io.Input;
//import com.esotericsoftware.kryo.io.Output;
//import com.thunisoft.test.utils.TestBean;
//
//public class KryoTest {
//    static Kryo kryo = new Kryo();
//    static {
//        // Registration registration = kryo.register(TestBean.class);
//    }
//
//    public static byte[] bean2Byte(Object bean) {
//
//        Output output = new Output(1, 4096);
//        kryo.writeObject(output, bean);
//        byte[] bb = output.toBytes();
//        output.flush();
//        output.close();
//
//        // Base64.encodeBase64String(bb);
//        return bb;
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
//    public static String bean2String(Object bean) {
//        Kryo kryo = new Kryo();
//        Registration registration = kryo.register(bean.getClass());
//        Output output = new Output(1, 4096);
//        kryo.writeObject(output, bean);
//        byte[] bb = output.toBytes();
//        output.flush();
//        output.close();
//        return Base64.encodeBase64String(bb);
//    }
//
//    public static <T> T string2Bean(String bean, Class<T> c) {
//        byte[] bb = Base64.decodeBase64(bean);
//
//        Kryo kryo = new Kryo();
//        kryo.setReferences(true);
//        kryo.setRegistrationRequired(true);
//        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
//
//        Registration registration = kryo.register(c);
//        kryo.register(Timestamp.class);
//
//        Input input = new Input(bb);
//        T s = (T) kryo.readObject(input, registration.getType());
//        input.close();
//        return s;
//    }
//
//    public static void main(String[] args) {
//
//        List<TestBean> list = TestBean.randomBean(100000);
//
//        long time = System.currentTimeMillis();
//
//        for (TestBean bean : list) {
//
//            // 反序列化
//
//            bean2Byte(bean);
//
//        }
//        time = System.currentTimeMillis() - time;
//        System.out.println("time:" + time);
//    }
//}
