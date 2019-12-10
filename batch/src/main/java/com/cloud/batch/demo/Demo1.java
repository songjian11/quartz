package com.cloud.batch.demo;

import com.cloud.batch.job.JobDemo3;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.util.Properties;

public class Demo1 {
    public static void main(String[] args) throws Exception {
        Properties pro = new Properties();
        pro.load(Demo1.class.getClassLoader().getResourceAsStream("quartz.properties"));
        SchedulerFactory factory = new StdSchedulerFactory(pro);
        // 获取任务调度器
        Scheduler scheduler = factory.getScheduler();
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("msg", "hello world");
        // 创建任务
        JobDetail job = JobBuilder.newJob()
                .setJobData(jobDataMap) // 设置任务参数
                .ofType(JobDemo3.class) // 任务实现类
                .storeDurably(false) //
                .withDescription("测试") // job描述
                .withIdentity("hello job", "demo") // job名称和分组
                .build();
        // 创建时间
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");
        // 创建触发器
        CronTrigger trigger = TriggerBuilder.newTrigger().withSchedule(cronScheduleBuilder).forJob(job).build();
        scheduler.scheduleJob(job, trigger);
        // 开始执行计划
        scheduler.start();
    }
}
