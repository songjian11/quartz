package com.cloud.batch.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类，我们继承QuartzJobBean
 * 重写executeInternal方法来实现具体的定时业务逻辑
 */
public class JobDemo2 extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String msg = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("msg");
        System.out.println("current time :"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                + "---" + msg);
    }
}
