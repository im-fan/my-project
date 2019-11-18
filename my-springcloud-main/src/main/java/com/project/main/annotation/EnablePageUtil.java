package com.project.main.annotation;

import com.project.main.PageConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //在运行期保留注解信息
@Target({ElementType.TYPE})  //方法注解
@Import(PageConfiguration.class)
public @interface EnablePageUtil {
}
