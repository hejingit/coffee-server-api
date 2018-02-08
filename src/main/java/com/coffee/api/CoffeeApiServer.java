/**
 * Project Name:coffee-server-api
 * File Name:CoffeeApiServer.java
 * Package Name:com.coffee.api
 * Date:2018年2月7日下午2:33:22
 * Copyright (c) 2018, Coffee Ease 2016-2018 All Rights Reserved.
 *
 */

package com.coffee.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * ClassName:CoffeeApiServer <br/>
 * Function: 接口服务启动总入口. <br/>
 * Date:     2018年2月7日 下午2:33:22 <br/>
 * @author   Jin.He (mailto:hejin@coffee-ease.com)
 * @version  
 * @see 	 
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})  //禁用springboot 自動注入數據源
@MapperScan("com.coffee.api.mapper")
@ServletComponentScan // 注意要加上@ServletComponentScan注解，否则Servlet无法生效
@EnableSwagger2
public class CoffeeApiServer extends SpringBootServletInitializer{
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CoffeeApiServer.class);
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(CoffeeApiServer.class, args);
	}
}

