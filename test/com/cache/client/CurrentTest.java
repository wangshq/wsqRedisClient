package com.cache.client;

import com.cache.operation.IStringOperation;

/**
 * CurrentTest
 * @author wangshq
 * @version 1.0
 *
 */
public class CurrentTest {

//    public static void main(String[] args) {
//        
//        RedisPool.INSTANCE.setJedisPool("172.16.33.173", "6379", "aap2017");
//        String registerKey = "wangshq.test";
//        CacheManagerClient.registerKey(registerKey, 2, "wangshq.test");
//        
//        
////        IKeyOperation dIStringOperation = CacheManagerClient.getKeyOperation(registerKey);
////        dIStringOperation.deleteHeadKeyDatas("");
//        
//        IHashOperation aIStringOperation = CacheManagerClient.getHashOperation(registerKey);
//        
//        long start=System.currentTimeMillis(); //获取开始时间  
//        for (int i = 0; i < 10*10000; i++) {
//            aIStringOperation.hset("111key", i+"", new Corp());
//        }
//        long end=System.currentTimeMillis(); //获取结束时间
//        System.out.println("corp程序运行时间："+(end-start));
//        
//        //单线程测试
//      //  signalThread(aIStringOperation);
//        
//        
////        10个线程测试
////        duogeThread(10,aIStringOperation);
//        
//        
////        //100个线程测试
////        duogeThread(100,aIStringOperation);
//        
//        
////      200个线程测试
////        duogeThread(200,aIStringOperation);
//        
//        
////      400个线程测试
////        duogeThread(400,aIStringOperation);
//        
////      500个线程测试
////        duogeThread(c,aIStringOperation);
//        //IKeyOperation dIStringOperation = CacheManagerClient.getKeyOperation(registerKey);
//    }

    /**
     * @param aIStringOperation
     */
    public static void signalThread(IStringOperation aIStringOperation) {
        for (int i = 0; i < 1000000; i++) {
            aIStringOperation.set("key"+i, "value"+i);
        }
    }
    
    
    /**
     * @param aIStringOperation
     */
    public static void duogeThread(int count,final IStringOperation aIStringOperation) {
        for (int i = 0; i < count; i++) {
            Thread aThread = new Thread(new Runnable() {
                @Override
                public void run() {
//                    for (int i = 0; i < 10000; i++) {
                        aIStringOperation.get("111key");
//                    }
                }
            });
            aThread.start();
        }
    }
}
