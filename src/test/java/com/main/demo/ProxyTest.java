package com.main.demo;

import com.main.spring.pointcut.PointCutDemo;
import com.main.spring.proxy.LogProxy;
import com.project.web.service.user.UserService;
import com.project.web.service.user.impl.UserServiceImpl;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactoryBean;

/**
 * 代理类测试
 *
 *@author: Weiyf
 *@Date: 2019-03-13 16:17
 */
public class ProxyTest {

    private static void proxyTest(){

        LogProxy logProxy = new LogProxy();
        UserService userService = (UserService) logProxy.bindObject(new UserServiceImpl());
        userService.printUser();

    }

    private static void proxyStaticTest(){
        LogProxy logProxy = new LogProxy();
        UserServiceImpl service = (UserServiceImpl) logProxy.bindObject(new UserServiceImpl());
        service.printUserInfo();
    }

    private static void methodInterceptionTest()  {
        try{

            ProxyFactoryBean factoryBean = new ProxyFactoryBean();
            factoryBean.setProxyInterfaces(new Class[]{UserService.class});
            factoryBean.setInterceptorNames("userServiceImpl");
            UserServiceImpl service = (UserServiceImpl) factoryBean.getObject();

            service.printUserInfo();

        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
//         proxyTest();
//        proxyStaticTest();
        methodInterceptionTest();

    }

}
