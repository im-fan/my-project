package com.project.arithmetic.common;

import com.alibaba.fastjson.JSONObject;
import com.project.arithmetic.MyCollectionUtil;

/**
 * 冒泡排序算法
 * (1)比较前后相邻的二个数据，如果前面数据大于后面的数据，就将这二个数据交换。
 * (2)这样对数组的第 0 个数据到 N-1 个数据进行一次遍历后，最大的一个数据就“沉”到数组第 N-1 个位置。
 * (3)N=N-1，如果 N 不为 0 就重复前面二步，否则排序完成。
 *@author: Weiyf
 *@Date: 2019-09-16 10:41
 */
public class Bubbing {

    private static int[] sort(int[] list){
        if(list.length == 0){
            return list;
        }

        int idx = list.length;
        for(int i=0; i<idx; i++){
            for(int j=1; j<idx-i; j++){
                if(list[j] < list[j-1]){
                    int temp = list[j];
                    list[j] = list[j-1];
                    list[j-1] = temp;
                }
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
        System.out.println(" 耗时："+(end - start)+"ms  结果："+JSONObject.toJSONString(list));
    }

}
