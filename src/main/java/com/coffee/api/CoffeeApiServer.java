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
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;


/**
 * ClassName:CoffeeApiServer <br/>
 * Function: 接口服务启动总入口. <br/>
 * Date:     2018年2月7日 下午2:33:22 <br/>
 * @author   Jin.He (mailto:hejin@coffee-ease.com)
 * @version  
 * @see 	 
 */
@SpringBootApplication
@MapperScan("com.coffee.api.mapper")
public class CoffeeApiServer extends SpringBootServletInitializer{
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CoffeeApiServer.class);
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(CoffeeApiServer.class, args);
	}
}

