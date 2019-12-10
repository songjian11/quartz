package com.cloud.batch.controller;

import com.cloud.batch.util.SpringBeanContextUtil;
import com.cloud.batch.vo.JobRequest;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.bind.annotation.*;

@Api(value = "任务调度", tags = {"任务调度"})
@RestController
@RequestMapping("job")
@Slf4j
public class JobController {
    @Autowired
    private Scheduler scheduler;

    @ApiOperation(value = "添加任务", notes = "添加任务")
    @PostMapping("/addJob")
    public String add(@RequestBody @ApiParam(value = "任务参数", required = true, name = "request") JobRequest request) throws Exception {
        String cron = request.getCron();
        String jobName = request.getJobName();
        String jobGroup = request.getJobGroup();
        String jobClassName = request.getJobClassName();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        //根据name 和group获取当前trgger 的身份
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        CronTrigger triggerOld = null;
        //获取 触发器的信息
        triggerOld = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (triggerOld == null) {
            //将job加入到jobDetail中
            JobDetail jobDetail = JobBuilder.newJob(SpringBeanContextUtil.getBean(jobClassName, QuartzJobBean.class).getClass()).withIdentity(jobName, jobGroup).build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup).withSchedule(cronScheduleBuilder).build();
            //执行任务
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            return "job has exist";
        }
        return "is ok";
    }

    @ApiOperation(value = "删除任务", notes = "删除任务")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "jobName", value = "工作名称", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "jobGroup", value = "工作组名称", required = true, dataType = "String")
    })
    @GetMapping("/removeJob")
    public String remove(String jobName, String jobGroup) throws Exception {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        scheduler.pauseTrigger(triggerKey);// 停止触发器
        scheduler.unscheduleJob(triggerKey);// 移除触发器
        scheduler.deleteJob(new JobKey(jobName, jobGroup));// 删除任务
        return "is ok";
    }

    @ApiOperation(value = "修改任务", notes = "修改任务")
    @PostMapping("/updateJob")
    public String updateTime(@RequestBody @ApiParam(value = "任务参数", required = true, name = "request") JobRequest request) throws Exception {
        String cron = request.getCron();
        String jobName = request.getJobName();
        String jobGroup = request.getJobGroup();
        //根据name 和group获取当前trgger 的身份
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        CronTriggerImpl cronTrigger = (CronTriggerImpl)scheduler.getTrigger(triggerKey);
        if(null == cronTrigger){
            return "job not exist";
        }
        String oldCron = cronTrigger.getCronExpression();
        System.out.println("====================cron:" + oldCron);
        if(!oldCron.equals(cron)){
            // 修改时间
            cronTrigger.setCronExpression(cron);
            // 重启任务
            scheduler.rescheduleJob(triggerKey, cronTrigger);
        }
        return "is ok";
    }
}
