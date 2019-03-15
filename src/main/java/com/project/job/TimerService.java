package com.project.job;

import java.util.TimerTask;

/**
 * 业务逻辑
 *
 *@author: Weiyf
 *@Date: 2019-03-14 17:11
 */
public class TimerService extends TimerTask {

    @Override
    public void run() {
        System.out.println("=====>" + System.currentTimeMillis());
    }

}
