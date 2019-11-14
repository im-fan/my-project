package com.project.web.job;



import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时任务
 *
 *@author: Weiyf
 *@Date: 2019-07-10 17:14
 */
public class ScheduledJob implements Job {
    private SimpleDateFormat dateFormat() {
        return new SimpleDateFormat("HH:mm:ss");
    }

    @Override
    public void execute(JobExecutionContext context) {
        System.out.println("AAAA: The time is now " + dateFormat().format(new Date()));
    }
}
