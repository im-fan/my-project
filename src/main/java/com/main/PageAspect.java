package com.main;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PageAspect {

    @Pointcut("@annotation(com.main.annotation.Pagination)")
    public void pagePointCut(){
    }

    @Before("pagePointCut()")
    public void before(JoinPoint joinPoint){
        PageUtil.startPage(joinPoint);
    }

}
