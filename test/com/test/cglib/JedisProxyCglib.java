package com.test.cglib;

import java.lang.reflect.Method;

import redis.clients.jedis.Jedis;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 */
public class JedisProxyCglib implements MethodInterceptor {

    private Jedis target;

    /**
     * 创建代理对象
     * 
     * @param target
     * @return
     */
    public Jedis getInstance(Jedis target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        // 回调方法
        enhancer.setCallback(this);
        // 创建代理对象
        return (Jedis) enhancer.create();
    }

    @Override
    // 回调方法
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

        // proxy.invokeSuper(obj, args);
        args[0] = args[0] + "_xx_";
        proxy.invoke(target, args);
        return null;

    }

}