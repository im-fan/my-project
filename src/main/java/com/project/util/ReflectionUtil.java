package com.project.util;

import java.lang.reflect.Field;

/**
 * 类属性值操作工具
 *
 *@author: Weiyf
 *@Date: 2018/7/24 11:28
 */
public class ReflectionUtil {


    /**
     * 合并参数值[未完成]
     *
     * @param source 来源类
     * @param target 目标类
     *
     *@author: Weiyf
     *@Date: 2018/7/24 11:28
     */
    public static void mergeParam(Object source,Object target){
        if(source == null || target == null){
            return;
        }
        Field[] sourceFields = source.getClass().getDeclaredFields();
        Field[] targetFields = target.getClass().getDeclaredFields();
        for(Field targetField : targetFields){
            for(Field sourceField : sourceFields){
                sourceField.setAccessible(true);
                targetField.setAccessible(true);
                if(!sourceField.getName().equals(targetField.getName())){
                    continue;
                }

                try {
                    //来源类字段值为空，不覆盖
                    if(sourceField.get(source) == null
                            || "".equals(sourceField.get(source))){
                        continue;
                    }

                    //类型不操作当前字段
                    targetField.set(target,sourceField.get(source));
                }catch (RuntimeException e){
                    continue;
                } catch (IllegalAccessException e) {
                    continue;
                }

            }
        }
    }

}
