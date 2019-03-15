package com.project.job;

import java.util.Timer;

/**
 * 启动定时任务
 *
 *@author: Weiyf
 *@Date: 2019-03-14 17:20
 */
public class TimerJob {

    public void jobRun(){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerService(),0,1000);
    }

    public static void main(String[] args){
        TimerJob job = new TimerJob();
        job.jobRun();
    }

}
