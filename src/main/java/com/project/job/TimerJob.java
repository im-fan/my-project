package com.project.job;

import com.main.utils.LocalDateUtil;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 启动定时任务
 *
 *@author: Weiyf
 *@Date: 2019-03-14 17:20
 */
@Service
public class TimerJob implements CommandLineRunner{

    @Override
    public void run(String... strings) {

        /*多线程并行处理定时任务时，Timer运行多个TimeTask时，
        只要其中之一没有捕获抛出的异常，其它任务便会自动终止运行，
        使用ScheduledExecutorService则没有这个问题。*/

        /*ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder()
                        .namingPattern("example-schedule-pool-%d")
                        .daemon(true).build());
        executorService.scheduleAtFixedRate(() -> {
            System.out.printf("JobRun ===== %s \n",LocalDateUtil.getDateTimeNow());
        },0,1000, TimeUnit.MILLISECONDS);*/

    }

}
