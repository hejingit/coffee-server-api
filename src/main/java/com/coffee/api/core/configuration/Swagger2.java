/**
 * Project Name:coffee-server-api
 * File Name:Swagger2.java
 * Package Name:com.coffee.api.core.configuration
 * Date:2018年2月8日下午4:37:35
 * Copyright (c) 2018, Coffee Ease 2016-2018 All Rights Reserved.
 *
 */

package com.coffee.api.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * ClassName:Swagger2 <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年2月8日 下午4:37:35 <br/>
 * @author   Jin.He (mailto:hejin@coffee-ease.com)
 * @version  
 * @see 	 
 */
@Configuration
public class Swagger2 {
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.coffee.api.web"))
				.paths(PathSelectors.any())
				.build();
	}
	
	private ApiInfo apiInfo() {
		//http://localhost:8080/swagger-ui.html
		return new ApiInfoBuilder()
				.title("Coffee-Ease api文档")
				.description("http://api.coffee-ease.com")
				.termsOfServiceUrl("http://api.coffee-ease.com")
				.version("1.0")
				.build();
	}
}

