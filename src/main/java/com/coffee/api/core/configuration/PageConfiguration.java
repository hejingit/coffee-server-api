///**
// * Project Name:coffee-server-api
// * File Name:PageConfiguration.java
// * Package Name:com.coffee.api.core.configuration
// * Date:2018年2月7日下午9:02:09
// * Copyright (c) 2018, Coffee Ease 2016-2018 All Rights Reserved.
// *
// */
//
//package com.coffee.api.core.configuration;
//
//import java.util.Properties;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.github.pagehelper.PageHelper;
//
///**
// * ClassName:PageConfiguration <br/>
// * Function: TODO ADD FUNCTION. <br/>
// * Date:     2018年2月7日 下午9:02:09 <br/>
// * @author   Jin.He (mailto:hejin@coffee-ease.com)
// * @version  
// * @see 	 
// */
//@Configuration
//public class PageConfiguration {
//	
///**
// * 配置mybatis的分页插件pageHelper
// * pageHelper:
// * @author Jin.He (mailto:hejin@coffee-ease.com)
// * @return
// */
//@Bean
//public PageHelper pageHelper(){
//		PageHelper pageHelper = new PageHelper();
//		Properties properties = new Properties();
//		properties.setProperty("offsetAsPageNum", "true");
//		properties.setProperty("rowBoundsWithCount", "true");
//		properties.setProperty("reasonable", "true");
//		properties.setProperty("dialect", "mysql"); // 配置mysql数据库的方言
//		pageHelper.setProperties(properties);
//		return pageHelper;
//	}
//}
//
