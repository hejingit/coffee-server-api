/**
 * Project Name:coffee-server-api
 * File Name:DruidStatViewServlet.java
 * Package Name:com.coffee.api.core.filter
 * Date:2018年2月8日下午3:29:07
 * Copyright (c) 2018, Coffee Ease 2016-2018 All Rights Reserved.
 *
 */

package com.coffee.api.core.filter;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.alibaba.druid.support.http.StatViewServlet;

/**
 * ClassName:DruidStatViewServlet <br/>
 * Function: http://localhost:8080/druid/ <br/>
 * Date:     2018年2月8日 下午3:29:07 <br/>
 * @author   Jin.He (mailto:hejin@coffee-ease.com)
 * @version  
 * @see 	 
 */
@WebServlet(urlPatterns = "/druid/*",  
initParams={  
//        @WebInitParam(name="allow",value="127.0.0.1"),// IP白名单 (没有配置或者为空，则允许所有访问)  
//        @WebInitParam(name="deny",value="192.168.0.0"),// IP黑名单 (存在共同时，deny优先于allow)  
        @WebInitParam(name="loginUsername",value="admin"),// druid监控页面登陆用户名  
        @WebInitParam(name="loginPassword",value="admin"),// druid监控页面登陆密码  
        @WebInitParam(name="resetEnable",value="false")// 禁用HTML页面上的“Reset All”功能  
})  
public class DruidStatViewServlet extends StatViewServlet {  
  
    /**  
     *   
     */  
    private static final long serialVersionUID = 1L;  
  
} 

