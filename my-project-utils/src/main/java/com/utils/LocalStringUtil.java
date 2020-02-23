package com.utils;



import com.alibaba.excel.util.StringUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 字符串处理工具
 *
 *@author: Weiyf
 *@Date: 2020/2/21 16:45
 */
public class LocalStringUtil {

    /** 首字母大写 **/
    public static String firstWordUpCase(String str){

        if(StringUtils.isEmpty(str) || str.length() <= 0){
            return str;
        }

        char[] chars = str.toCharArray();
        if (chars[0] >= 'a' && chars[0] <= 'z') {
            chars[0] = (char)(chars[0] - 32);
        }

        return String.valueOf(chars);
    }

    /** 是否为纯数字 **/
    public static boolean isNumber(String str){
        char[] chars = str.toCharArray();
        for (char ch : chars){
            if(ch >= '0' && ch <='9'){
                continue;
            }
            return false;
        }
        return true;
    }

    /**
     * 反射获取列值,返回long类型(非数字类型，用hashcode值)
     * 用于排序
     * **/
    public static Long invokeValue(Object obj,String column)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        column = "get" + firstWordUpCase(column);
        Object obj1 = obj.getClass().getDeclaredMethod(column).invoke(obj);
        if(obj1 == null){
            return 0L;
        }

        String str = String.valueOf(obj1);
        if(!LocalStringUtil.isNumber(str)){
            return Long.valueOf(str.hashCode());
        }
        return obj1 == null ? 0L : Long.valueOf(str);
    }

}
