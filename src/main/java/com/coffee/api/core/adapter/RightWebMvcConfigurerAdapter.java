/**
 * Project Name:coffee-server-api
 * File Name:RightWebMvcConfigurerAdapter.java
 * Package Name:com.coffee.api.core.adapter
 * Date:2018年2月7日下午2:38:05
 * Copyright (c) 2018, Coffee Ease 2016-2018 All Rights Reserved.
 *
 */

package com.coffee.api.core.adapter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.coffee.api.core.interceptor.LoginInterceptor;
import com.coffee.api.core.interceptor.TokenInterceptor;

/**
 * ClassName:RightWebMvcConfigurerAdapter <br/>
 * Function: 权限验证拦截器 <br/>
 * Date:     2018年2月7日 下午2:38:05 <br/>
 * @author   Jin.He (mailto:hejin@coffee-ease.com)
 * @version  
 * @see 	 
 */
@Configuration
public class RightWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/userController/**", "/redisController/**") //
				.excludePathPatterns("/userController/webLogin**", "/userController/login/**");
//		registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/getCreditApply/**", "/creditRedeemcodeVerify/**");
	}
}
