package com.project.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@ComponentScan("com.xxx.configuration")
@Configuration
public class XXManagerConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    private static ApplicationContext context;
//    @Autowired
//    private CheckInterceptor checkInterceptor;

    /**
     * 拦截器配置
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Session校验拦截器
//        registry.addInterceptor(checkInterceptor);
    }

    @Override
    public void setApplicationContext(ApplicationContext paramApplicationContext)
            throws BeansException {
        context = paramApplicationContext;
    }

    public static <T> T getBean(String paramString, Class<T> paramClass) {
        return context.getBean(paramString, paramClass);
    }

    public static <T> T getBean(Class<T> paramClass) {
        return context.getBean(paramClass);
    }

}
