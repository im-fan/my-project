package com.project.arithmetic;

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
    }

    public static void main(String[] args){
        ListNode aa = new ListNode(2);
        aa.next = new ListNode(4);
        aa.next.next = new ListNode(3);

        ListNode bb = new ListNode(5);
        bb.next = new ListNode(6);
        bb.next.next = new ListNode(4);

        addTwoNumbers(aa,bb);
    }

}

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}