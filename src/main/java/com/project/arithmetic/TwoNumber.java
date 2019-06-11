package com.project.arithmetic;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 两数之和
 *
 * 给定 nums = [2, 7, 11, 15], target = 9
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 *
 *@author: Weiyf
 *@Date: 2019-06-06 15:09
 */
public class TwoNumber {

    private static int[] findTwoNumberIndex(int[] numbers,int target){

        int[] result = new int[0];
        List<Integer> list = new ArrayList<>();
        for(int a : numbers){
            list.add(a);
        }

        for(int a : list){
            int b = target - a;
            if(list.contains(b)){
                result = new int[]{list.indexOf(a), list.indexOf(b)};
                break;
            }
        }
        return result;
    }


    public static void main(String[] args) {
        int[] a = {1,2,5,9,3};
        System.out.println(JSONObject.toJSONString(findTwoNumberIndex(a,10)));
    }
}
