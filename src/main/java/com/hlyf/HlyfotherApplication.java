package com.hlyf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;


import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableTransactionManagement  //开启事务处理
@SpringBootApplication
@EnableScheduling
@ServletComponentScan(basePackages={"com.hlyf.fliterController"})
//@EnableEurekaClient //本服务启动后会自动注册进eureka服务中
public class HlyfotherApplication extends SpringBootServletInitializer {

	//jar 启动
	public static void main(String[] args) {
		SpringApplication.run(HlyfotherApplication.class, args);
	}

	// tomcat war启动
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(HlyfotherApplication.class);
	}


}
