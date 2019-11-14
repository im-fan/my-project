package com.project.arithmetic.thread.number;

import com.alibaba.fastjson.JSONObject;
import netscape.javascript.JSObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两个线程单独计算给定信息，最后汇总
 *
 *@author: Weiyf
 *@Date: 2019-10-15 11:38
 */
public class AddNumber {

    private final List<Integer> listA = Arrays.asList(21,43,57,15,99,46,2,30,77,26);
    private final List<Integer> listB = Arrays.asList(56,21,79,46,67,12,92,58,69,11);
    private volatile List<Integer> listTot = new ArrayList<>(10);
    private volatile int idx = 0;

    public static CountDownLatch countDownLatch = new CountDownLatch(10);

    public static Lock lock = new ReentrantLock();

    public static class ThreadOne implements Runnable{

        private AddNumber addNumber;

        public ThreadOne(AddNumber addNumber){
            this.addNumber = addNumber;
        }

        @Override
        public void run() {
            while(addNumber.idx < addNumber.listA.size()-1){
                try{
                    lock.lock();
                    System.out.println(Thread.currentThread().getName()+" 开始操作===》"+System.currentTimeMillis());
                    Thread.sleep(50);
                    addNumber.listTot.add(addNumber.idx,
                            addNumber.listA.get(addNumber.idx) +
                                    addNumber.listB.get(addNumber.idx));

                } catch (RuntimeException| InterruptedException e){
                    System.out.println(e);
                } finally {
                    countDownLatch.countDown();
                    addNumber.idx++;
                    lock.unlock();
                }
            }
        }
    }

    public static class ThreadTwo implements Runnable{

        private AddNumber addNumber;

        public ThreadTwo(AddNumber addNumber){
            this.addNumber = addNumber;
        }

        @Override
        public void run() {
            while(addNumber.idx < addNumber.listA.size() -1){
                try{
                    lock.lock();
                    System.out.println(Thread.currentThread().getName()+" 开始操作===》"+System.currentTimeMillis());
                    Thread.sleep(50);
                    addNumber.listTot.add(addNumber.idx,
                            addNumber.listA.get(addNumber.idx) +
                                    addNumber.listB.get(addNumber.idx));

                } catch (RuntimeException | InterruptedException e){
                    System.out.println(e);
                } finally {
                    countDownLatch.countDown();
                    addNumber.idx++;
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AddNumber addNumber = new AddNumber();

        Thread t1 = new Thread(new ThreadOne(addNumber));
        Thread t2 = new Thread(new ThreadTwo(addNumber));
        t1.setName("T1");
        t2.setName("T2");

        t1.start();
        t2.start();
        countDownLatch.await();
        System.out.println("最终结果==》 "+ JSONObject.toJSONString(addNumber.listTot));
    }

}
