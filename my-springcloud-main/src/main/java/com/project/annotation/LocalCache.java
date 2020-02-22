package com.project.annotation;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 本地缓存注解
 *
 *@author: Weiyf
 *@Date: 2020/2/21 18:08
 */
@Target({java.lang.annotation.ElementType.METHOD,
        java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LocalCache {

    /**
     * 例：
     * 单列入参：@LocalCache(key = "'key:'+#flag",expirationTime = 30)
     * 对象入参：@LocalCache(key = "'key:'+#param.flag",expirationTime = 30)
     * **/

    /** 缓存主键
     * Spel表达式
     * **/
    String key();

    /** 过期时长,默认30s **/
    int expirationTime() default 30;
}
