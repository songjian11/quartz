package com.cloud.batch.quartz;

import com.cloud.batch.config.SchedulerFactoryConfig;
import com.zaxxer.hikari.HikariConfig;
import org.quartz.spi.TriggerFiredBundle;
import org.quartz.utils.HikariCpPoolingConnectionProvider;
import org.quartz.utils.PoolingConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

//@Component
public class MyQuartzFactory extends AdaptableJobFactory {
    @Autowired
    AutowireCapableBeanFactory capableBeanFactory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        // 调用父类的方法创建好Quartz所需的Job实例
        Object jobInstance = super.createJobInstance(bundle);
        // 使用BeanFactory为创建好的Job实例进行属性自动装配并将其纳入到Spring容器的管理之中
        capableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}
