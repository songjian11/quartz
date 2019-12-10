package com.cloud.batch.config;

import com.cloud.batch.quartz.MyQuartzFactory;

import org.quartz.utils.PoolingConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

//@Configuration
public class SchedulerFactoryConfig {
    @Autowired
    MyQuartzFactory myJobFactory;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("quartzProperties") Properties quartzProperties){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setQuartzProperties(quartzProperties);
        schedulerFactoryBean.setSchedulerName("My Job");
        schedulerFactoryBean.setJobFactory(myJobFactory);//将job实例化，能够操作进行Spring 注入
        return schedulerFactoryBean;
    }

    @Bean("quartzProperties")
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("quartz.properties"));
        //在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
}
