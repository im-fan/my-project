package com.project.arithmetic.leetcode;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
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

    public static int[] twoSum(int[] numbers, int target) {

        int[] res = new int[2];
        int low = 0;
        int high = numbers.length - 1;
        while (low < high) {
            if (numbers[low] + numbers[high] > target) {
                high--;
            }
            else if (numbers[low] + numbers[high] < target) {
                low++;
            }
            else {
                res[0] = low + 1;
                res[1] = high + 1;
                break;
            }
        }
        return res;

        //143ms
        /*
        int[] result = new int[2];
        for(int i=0; i<numbers.length; i++){
            for(int j=i+1; j<numbers.length; j++){
                if(numbers[i]+numbers[j] != target){
                    continue;
                }
                result[0]=i+1;
                result[1]=j+1;
            }
        }
        return result;
        */
    }

    public static void main(String[] args) {
        int[] numbers = {2,3,4,5};
        long time = System.currentTimeMillis();
        System.out.println(JSONObject.toJSONString(twoSum(numbers,9)));
        System.out.println(System.currentTimeMillis()-time);
    }
}
