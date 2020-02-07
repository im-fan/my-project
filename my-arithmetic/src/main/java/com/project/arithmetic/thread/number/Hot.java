package com.project.arithmetic.thread.number;

public class Hot {

    public static void main(String[] args) {
        for(;;){
            System.out.println(Thread.currentThread().getName());
            new Runnable(){
                @Override
                public void run() {
                    try {
                        Thread.sleep(100000);
                    } catch (InterruptedException e) {

                    }
                }
            };
        }
    }

}
