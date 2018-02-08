/**
 * Project Name:coffee-server-api
 * File Name:OtherDataSourceConfig.java
 * Package Name:com.coffee.api.core.configuration
 * Date:2018年2月8日下午2:47:57
 * Copyright (c) 2018, Coffee Ease 2016-2018 All Rights Reserved.
 *
 */

package com.coffee.api.core.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * ClassName:OtherDataSourceConfig <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年2月8日 下午2:47:57 <br/>
 * @author   Jin.He (mailto:hejin@coffee-ease.com)
 * @version  
 * @see 	 
 */
@Configuration
//扫描 Mapper 接口并容器管理
@MapperScan(basePackages = OtherDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "otherSqlSessionFactory")
public class OtherDataSourceConfig {
	// 精确到 master 目录，以便跟其他数据源隔离
  static final String PACKAGE = "com.coffee.api.othermapper";

  @Value("${spring.datasource.other.url}")
  private String url;

  @Value("${spring.datasource.other.username}")
  private String user;

  @Value("${spring.datasource.other.password}")
  private String password;

  @Value("${spring.datasource.other.driver-class-name}")
  private String driverClass;

  @Bean(name = "otherDataSource")
  @Primary
  public DataSource masterDataSource() {
      DruidDataSource dataSource = new DruidDataSource();
      dataSource.setDriverClassName(driverClass);
      dataSource.setUrl(url);
      dataSource.setUsername(user);
      dataSource.setPassword(password);
      return dataSource;
  }

  @Bean(name = "otherTransactionManager")
  public DataSourceTransactionManager masterTransactionManager() {
      return new DataSourceTransactionManager(masterDataSource());
  }

  @Bean(name = "otherSqlSessionFactory")
  public SqlSessionFactory masterSqlSessionFactory(@Qualifier("otherDataSource") DataSource otherDataSource)
          throws Exception {
      final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
      sessionFactory.setDataSource(otherDataSource);
    //自定义数据库配置的时候，需要将pageHelper的bean注入到Plugins中，如果采用系统默认的数据库配置，则只需要定义pageHelper的bean，会自动注入。       
//      sessionFactory.setPlugins(new Interceptor[] { pageHelper() });
      return sessionFactory.getObject();
  }
}


