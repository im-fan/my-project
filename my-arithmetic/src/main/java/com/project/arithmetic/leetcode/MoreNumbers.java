package com.project.arithmetic.leetcode;

/**
 * 169.多数元素
 *
 *@author: Weiyf
 *@Date: 2019-12-13 16:10
 */
public class MoreNumbers {

    private static int convert(int n){

        String result = Integer.toBinaryString(n);

        if(result.length()<32){
            for(int i=0;i<=32-result.length(); i++){
                result = "00"+result;
            }
        }
        System.out.println(result);
        char[] ch = result.toCharArray();
        String str = "";
        for(int i=ch.length-1 ;i>=0; i--){
            str +=ch[i];
        }
        System.out.println(str);
        return Integer.valueOf(str,2);
    }

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        System.out.println(convert(43261596));
        System.out.println(System.currentTimeMillis()-time);
    }

}
