package com.project.config.support;

import com.project.annotation.LocalCache;
import com.project.web.entity.po.cache.LocalCacheValue;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地缓存拦截器
 *
 *@author: Weiyf
 *@Date: 2020/2/21 18:09
 */
@Aspect
@Component
public class LocalCacheProcessor {

    /** SPEL表达式 **/
    private ExpressionParser parser = new SpelExpressionParser();

    /** 本地缓存池 **/
    public static volatile Map<String, LocalCacheValue> LocalCachePool = new ConcurrentHashMap<>();

    /** 最大key数量-临时方案 **/
    public static final int KeyMasSize = 10;

    /** key最大存活时长 单位:s **/
    public static final long KeyMaxLiveSecond = 60;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(com.project.annotation.LocalCache)")
    public void cacheMethod(){}

    /**
     * 缓存中有值则直接返回
     * **/
    @Around("cacheMethod() && @annotation(localCache)")
    public Object localCache(ProceedingJoinPoint jp, LocalCache localCache) {

        /** 清理过期缓存 **/
        long current = System.currentTimeMillis();
        cleanCache(current);

        /** 读缓存 **/
        String key = getCacheKey(jp,localCache.key());
        Object value = getCacheValue(key);
        if(value != null){
            return value;
        }

        /** 无缓存 **/
        try {
            synchronized (this){
                /** 再次判断缓存中是否有值 **/
                value = getCacheValue(key);
                if(value != null){
                    return value;
                }

                Object obj = jp.proceed();
                if(obj == null){
                    //不考虑空值导致的缓存穿透问题
                    return null;
                }

                /** 控制缓存总量,超过最大key限制则不写入缓存 **/
                if(LocalCachePool.keySet().size() >= KeyMasSize){
                    return obj;
                }

                /** 写入缓存 **/
                long liveSecond = localCache.expirationTime() > KeyMaxLiveSecond
                        ? KeyMaxLiveSecond : localCache.expirationTime();
                LocalCachePool.put(key,LocalCacheValue.builder()
                        .liveSecond(liveSecond)
                        .cachedTime(System.currentTimeMillis())
                        .values(obj)
                        .build());

                /** 返回接口值 **/
                return obj;
            }
        } catch (Throwable throwable) {
            log.warn("[本地缓存异常] key={},error={}",key,throwable);
        }

        return null;
    }

    /** 获取缓存key **/
    private Object getCacheValue(String key){
        LocalCacheValue value = LocalCachePool.get(key);
        if(value != null && value.getValues() != null){
            return value.getValues();
        }
        return null;
    }

    /**获取缓存key*/
    private String getCacheKey(ProceedingJoinPoint jp, String cacheKey) {
        //兼容固定key("key")写法
        if(!cacheKey.contains("#")){
            return cacheKey;
        }
        StandardEvaluationContext context = getStandardEvaluationContext(jp);
        Object key = parser.parseExpression(cacheKey).getValue(context);
        return String.valueOf(key);
    }

    /**@EnableTransactionManagement(proxyTargetClass = true) 必须启用CGLIB 代理*/
    private StandardEvaluationContext getStandardEvaluationContext(ProceedingJoinPoint jp) {
        Object[] args = jp.getArgs();
        MethodSignature signature = (MethodSignature) jp.getSignature();
        String[] parameterNames = signature.getParameterNames();

        StandardEvaluationContext context = new StandardEvaluationContext();
        if (parameterNames == null) {
            return context;
        }
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }
        return context;
    }

    /** 清理过期缓存 **/
    private void cleanCache(long current){
        for(String key : LocalCachePool.keySet()){
            LocalCacheValue value = LocalCachePool.get(key);
            long old = value.getCachedTime();
            long expiration = value.getLiveSecond();
            if((old + expiration * 1000) <= current){
                LocalCachePool.remove(key);
            }
        }
    }
}
