/**
 * Project Name:coffee-server-api
 * File Name:DruidStatFilter.java
 * Package Name:com.coffee.api.core.jpa
 * Date:2018年2月8日下午3:28:17
 * Copyright (c) 2018, Coffee Ease 2016-2018 All Rights Reserved.
 *
 */

package com.coffee.api.core.filter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import com.alibaba.druid.support.http.WebStatFilter;

/**
 * ClassName:DruidStatFilter <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年2月8日 下午3:28:17 <br/>
 * @author   Jin.He (mailto:hejin@coffee-ease.com)
 * @version  
 * @see 	 
 */
@WebFilter(filterName="druidStatFilter",urlPatterns="/*",  
initParams={  
        @WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")// 忽略资源  
})  
public class DruidStatFilter extends WebStatFilter {  
  
}  

