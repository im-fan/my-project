package com.project.arithmetic;

import java.util.HashMap;
import java.util.Map;

/**
 * 3. 无重复字符的最长子串
 *
 * 输入: "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 *
 *@author: Weiyf
 *@Date: 2019-06-06 15:54
 */
public class DistinctCode {

    private static int findMaxLengthCode(String code){

        char[] ch = code.toCharArray();

        String str = "";
        String maxStr = "";

        for(char a : ch){

            if(str.contains(String.valueOf(a))){
                if(str.length() > maxStr.length()){
                    maxStr = str;
                }
                str = "";
            }
            str = str + a;
        }
        System.out.println(maxStr);
        return maxStr.length();
    }

    public static void main(String[] args) {
        String code = "abcccba";
        System.out.println(findMaxLengthCode(code));
    }

}
