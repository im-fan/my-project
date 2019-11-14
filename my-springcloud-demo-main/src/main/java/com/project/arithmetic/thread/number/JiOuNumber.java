package com.project.arithmetic.thread.number;

import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 交替打印奇偶数
 *
 *@author: Weiyf
 *@Date: 2019-10-15 10:55
 */
public class JiOuNumber {

    /** true:奇数 false:偶数 **/
    private volatile boolean flag = false;

    public static final Lock LOCK = new ReentrantLock();
    private int number = 0;
    private final int tot = 1000;

    private static class Jinumber implements Runnable{
        private JiOuNumber obj;

        public Jinumber(JiOuNumber obj){
            this.obj = obj;
        }
        @Override
        public void run() {
            while (obj.number <= obj.tot){
                if(!obj.flag){
                    try{
                        LOCK.lock();
                        obj.number++;
                        System.out.println(Thread.currentThread().getName() + " number = "+obj.number);

                    } catch (RuntimeException e){
                        System.out.println("error="+ JSONObject.toJSONString(e.getMessage()));
                    } finally {
                        obj.flag = true;
                        LOCK.unlock();
                    }
                }
            }
        }
    }

    private static class OuNumber implements Runnable{

        private JiOuNumber obj;

        public OuNumber(JiOuNumber obj){
            this.obj = obj;
        }

        @Override
        public void run() {
            while (obj.number <= obj.tot){
                if(obj.flag){

                    try{

                        LOCK.lock();
                        obj.number++;
                        System.out.println(Thread.currentThread().getName() + " number = "+obj.number);
                    } catch (RuntimeException e){
                        System.out.println("error="+ JSONObject.toJSONString(e.getMessage()));
                    } finally {
                        obj.flag = false;
                        LOCK.unlock();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        JiOuNumber obj = new JiOuNumber();
        Thread t1 = new Thread(new Jinumber(obj));
        t1.setName("t1");

        Thread t2 = new Thread(new OuNumber(obj));
        t2.setName("t2");

        t1.start();
        t2.start();
    }

}
