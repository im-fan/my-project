package com.project.arithmetic;

import org.apache.commons.lang.math.RandomUtils;

public class MyCollectionUtil {

    public static int[] list(int times,int max){
        int[] list = new int[times];
        for(int i=0; i<times; i++){
            int result = RandomUtils.nextInt(max);
            list[i]=result;
        }
        return list;
    }

    public static int[] swap(int[] list,int idxa,int idxb){
        int temp = list[idxa];
        list[idxa] = list[idxb];
        list[idxb] = temp;
        return list;
    }

}
