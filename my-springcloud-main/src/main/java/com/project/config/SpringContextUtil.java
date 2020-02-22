package com.project.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * SpringContextUtil
 * 可以配合缓存注解使用
 * 相关资料：https://blog.csdn.net/xfl4629712/article/details/79983959
 *@author: Weiyf
 *@Date: 2020/2/22 17:57
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext appCtx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appCtx = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return appCtx;
    }

    public static Object getBean(Class clazz) {
        return appCtx.getBean(clazz);
    }
}
