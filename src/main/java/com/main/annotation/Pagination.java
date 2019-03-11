package com.main.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: weiyf
 * @Date: 2017/9/4 下午6:09
 * @Description: 启用分页功能注解
 */
@Target(java.lang.annotation.ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Pagination {

}
