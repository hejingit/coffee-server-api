/**
 * Project Name:coffee-server-api
 * File Name:DefaultDataSourceConfig.java
 * Package Name:com.coffee.api.core.configuration
 * Date:2018年2月8日下午2:15:55
 * Copyright (c) 2018, Coffee Ease 2016-2018 All Rights Reserved.
 *
 */

package com.coffee.api.core.configuration;

import java.util.Properties;

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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;

/**
 * ClassName:DefaultDataSourceConfig <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年2月8日 下午2:15:55 <br/>
 * @author   Jin.He (mailto:hejin@coffee-ease.com)
 * @version  
 * @see 	 
 */
@Configuration
//扫描 Mapper 接口并容器管理
@MapperScan(basePackages = DefaultDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "defaultSqlSessionFactory")
public class DefaultDataSourceConfig {
	// 精确到 master 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.coffee.api.mapper";
 
    @Value("${spring.datasource.primary.url}")
    private String url;
 
    @Value("${spring.datasource.primary.username}")
    private String user;
 
    @Value("${spring.datasource.primary.password}")
    private String password;
 
    @Value("${spring.datasource.primary.driver-class-name}")
    private String driverClass;
 
    @Bean(name = "defaultDataSource")
    @Primary
    public DataSource masterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }	
	
	@Bean(name = "primaryJdbcTemplate")
	public JdbcTemplate primaryJdbcTemplate(
	        @Qualifier("defaultDataSource") DataSource dataSource) {
	    return new JdbcTemplate(dataSource);
	}
    
 
    @Bean(name = "defaultTransactionManager")
    @Primary
    public DataSourceTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(masterDataSource());
    }
 
    @Bean(name = "defaultSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("defaultDataSource") DataSource defaultDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(defaultDataSource);
        //自定义数据库配置的时候，需要将pageHelper的bean注入到Plugins中，如果采用系统默认的数据库配置，则只需要定义pageHelper的bean，会自动注入。       
        sessionFactory.setPlugins(new Interceptor[] { pageHelper() });
        return sessionFactory.getObject();
    }
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        p.setProperty("dialect", "mysql");
        pageHelper.setProperties(p);
        return pageHelper;
    }
}

