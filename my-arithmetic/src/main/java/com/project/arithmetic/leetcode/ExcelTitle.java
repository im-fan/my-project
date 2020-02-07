package com.project.arithmetic.leetcode;


/**
 * Excel title
 *
 *@author: Weiyf
 *@Date: 2019-12-13 15:03
 */
public class ExcelTitle {

    public static String convertToTitle(int n) {
        if (n <= 0) {
            return "";
        }
        String str = new String();
        while (n > 0) {
            n--;
            str = (char) (n % 26 + 'A')+str;
            n =n / 26;
        }
        return str;

    }

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        System.out.println(convertToTitle(701));
        System.out.println(System.currentTimeMillis()-time);
    }

}
