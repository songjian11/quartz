package com.cloud.batch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan(basePackages={"com.cloud.**.dao"})
@EnableScheduling
@SpringBootApplication
public class BatchApplication {
	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
		System.out.println("==============任务调度服务启动成功==============");
	}
}
