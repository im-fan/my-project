package com.project.arithmetic.common;

import java.util.Arrays;
import java.util.List;

/**
 * 二分算法
 *
 * @desc
 * 又叫折半查找，要求待查找的序列有序。每次取中间位置的值与待查关键字比较，
 * 如果中间位置 的值比待查关键字大，则在前半部分循环这个查找的过程，
 * 如果中间位置的值比待查关键字小， 则在后半部分循环这个查找的过程。
 * 直到查找到了为止，否则序列中没有待查的关键字。
 *
 *@author: Weiyf
 *@Date: 2019-09-16 10:12
 */
public class ErFen {

    private static int search(int number, List<Integer> list){
        int min = 0;
        int max = list.size() - 1;
        int mid;
        while(min <= max){
            mid = (min + max) / 2;
            int result = list.get(mid);
            if(result == number){
                return mid;
            }

            if (result < number){
                min = mid+1;
            } else {
                max = mid-1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int result = search(2, Arrays.asList(1,2,3,4,5,6,7));
        System.out.println("下标 = "+result);
    }

}
