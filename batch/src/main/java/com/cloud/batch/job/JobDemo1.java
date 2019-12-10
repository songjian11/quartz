package com.cloud.batch.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
public class JobDemo1 {
//    @Scheduled(cron = "0/5 * * * * ?")
    public void job0(){
        System.out.println("===============================job1: 执行");
    }
}
