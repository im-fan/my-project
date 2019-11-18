package com.project.arithmetic.common;


import com.alibaba.fastjson.JSONObject;
import com.project.arithmetic.MyCollectionUtil;

/**
 * 选择排序
 *
 *@author: Weiyf
 *@Date: 2019-09-16 11:51
 */
public class SelectionSort {

    private static int[] sort(int[] list){
        if(list.length == 0){
            return list;
        }

        for(int i=0; i<list.length; i++){
            int idx = i;
            for(int j=i; j<list.length; j++){
                if(list[j] < list[idx]){
                    idx = j;
                }
            }
            if(idx != 0){
                MyCollectionUtil.swap(list,i,idx);
            }

        }

        return list;
    }

    public static void main(String[] args) {
        int[] list = MyCollectionUtil.list(10000,99999);
        System.out.println("初始值 "+JSONObject.toJSONString(list));
        long start = System.currentTimeMillis();
        list = sort(list);
        long end = System.currentTimeMillis();
        System.out.println(" 耗时："+(end - start)+"ms  结果："+ JSONObject.toJSONString(list));
    }

}
