package com.main.spring.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 使用动态代理打印日志
 *
 *@author: Weiyf
 *@Date: 2019-03-13 16:05
 */
public class LogProxy implements InvocationHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private Object proxyObj = null;

    /** 增加绑定方法 **/
    public Object bindObject(Object proxyObj){
        this.proxyObj = proxyObj;
        return Proxy.newProxyInstance(proxyObj.getClass().getClassLoader(),
                proxyObj.getClass().getInterfaces(),
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        Object result = null;
        try{
            log.info(" {} 开始执行 ====> ",method.getName());

            result = method.invoke(proxyObj,args);

            log.info(" {} 执行结束 《《《",method.getName());
        } catch (Exception e){
            log.error("方法执行异常，error={}",e.getMessage());
        }

        return result;
    }
}
