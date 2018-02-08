///**
// * Project Name:coffee-server-api
// * File Name:SpringBootJdbcApplication.java
// * Package Name:com.coffee.api.core.dao
// * Date:2018年2月7日下午2:51:30
// * Copyright (c) 2018, Coffee Ease 2016-2018 All Rights Reserved.
// *
// */
//
//package com.coffee.api.core.dao;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.env.Environment;
//import org.springframework.jdbc.core.JdbcTemplate;
//
///**
// * ClassName:SpringBootJdbcApplication <br/>
// * Function: TODO ADD FUNCTION. <br/>
// * Date:     2018年2月7日 下午2:51:30 <br/>
// * @author   Jin.He (mailto:hejin@coffee-ease.com)
// * @version  
// * @see 	 
// */
//@SpringBootApplication
//public class SpringBootJdbcApplication {
//	@Autowired
//	private Environment env;
//
//	/*@Bean
//	public DataSource dataSource() {
//		DataSource dataSource = new DataSource();
//		dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
//		dataSource.setUrl(env.getProperty("spring.datasource.url"));
//		dataSource.setUsername(env.getProperty("spring.datasource.username"));
//		dataSource.setPassword(env.getProperty("spring.datasource.password"));
//		return dataSource;
//	}*/
//	@Bean(name = "primaryDataSource")
//    @Qualifier("primaryDataSource")
//    @ConfigurationProperties(prefix="spring.datasource.primary")
//    public DataSource primaryDataSource() {
//        return DataSourceBuilder.create().build();
//    }
////    @Bean(name = "secondaryDataSource")
////    @Qualifier("secondaryDataSource")
////    @Primary
////    @ConfigurationProperties(prefix="spring.datasource.secondary")
////    public DataSource secondaryDataSource() {
////        return DataSourceBuilder.create().build();
////    }
//	
//	
//	@Bean(name = "primaryJdbcTemplate")
//	public JdbcTemplate primaryJdbcTemplate(
//	        @Qualifier("primaryDataSource") DataSource dataSource) {
//	    return new JdbcTemplate(dataSource);
//	}
////	@Bean(name = "secondaryJdbcTemplate")
////	public JdbcTemplate secondaryJdbcTemplate(
////	        @Qualifier("secondaryDataSource") DataSource dataSource) {
////	    return new JdbcTemplate(dataSource);
////	}
//}
//
