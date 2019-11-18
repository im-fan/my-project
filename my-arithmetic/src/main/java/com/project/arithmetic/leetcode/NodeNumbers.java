package com.project.arithmetic.leetcode;



import com.utils.LocalDateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 题目来自力扣中国 https://leetcode-cn.com
 *
 *@author: Weiyf
 *@Date: 2019-04-08 17:24
 */
public class NodeNumbers {

    /**
     * 不考虑首位为0
     * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出：7 -> 0 -> 8
     * 原因：342 + 465 = 807
     * **/
    public static void addTwoNumbers(ListNode l1, ListNode l2) {

        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        ListNode nextA = l1;
        while(nextA != null){
            list1.add(nextA.val);
            nextA = nextA.next;
        }

        ListNode nextB = l2;
        while(nextB != null){
            list2.add(nextB.val);
            nextB = nextB.next;
        }

        String num1 = "";
        String num2 = "";
        for(int i=list1.size()-1; i>=0; i--){
            num1 += list1.get(i);
        }
        for(int j=list2.size()-1; j>=0; j--){
            num2 += list2.get(j);
        }
        int numOne = Integer.valueOf(num1);
        int numTwo = Integer.valueOf(num2);

        String tot = String.valueOf(numOne + numTwo);
        char[] totCh = tot.toCharArray();

        for(char a : totCh){
            System.out.print(a + "====");
        }
        System.out.println();
    }

    public static void addTwoNumbersTwo(ListNode l1, ListNode l2) {

        Integer first = 0;
        Integer second = 0;
        int i=0;

        while(l1 != null){
            first = first + l1.val * Double.valueOf(Math.pow(10,i)).intValue();
            l1 = l1.next;

            second = second + l2.val * Double.valueOf(Math.pow(10,i)).intValue();
            l2 = l2.next;
            i++;
        }

        String tot = String.valueOf(first + second);
        char[] totCh = tot.toCharArray();

        for(char a : totCh){
            System.out.print(a + "====");
        }
        System.out.println();
    }




    public static void main(String[] args){

        ListNode aa = new ListNode(2);
        aa.next = new ListNode(4);
        aa.next.next = new ListNode(3);

        ListNode bb = new ListNode(5);
        bb.next = new ListNode(6);
        bb.next.next = new ListNode(4);

        long startTime = LocalDateUtil.currentTimeMillis();
//        addTwoNumbers(aa,bb);
//        long firstTime = LocalDateUtil.currentTimeMillis();
//        System.out.println("耗时===>"+(firstTime - startTime));

        long secondTime = LocalDateUtil.currentTimeMillis();
        addTwoNumbersTwo(aa,bb);
        System.out.println("耗时===>"+(secondTime - startTime));

    }

}

class  ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}