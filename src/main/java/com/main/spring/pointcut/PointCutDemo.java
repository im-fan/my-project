package com.main.spring.pointcut;




import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PointCutDemo implements MethodInterceptor {


    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object invoke(MethodInvocation methodInvocation)  {
        try{

            log.info("{} 方法开始执行",methodInvocation.getMethod().getName());
            Object obj = methodInvocation.proceed();
            log.info("{} 方法执行结束",methodInvocation.getMethod().getName());
            return obj;
        } catch (Throwable e){
            log.error("");
            return null;
        }
    }
}
