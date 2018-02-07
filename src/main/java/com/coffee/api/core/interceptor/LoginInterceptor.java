/**
 * Project Name:coffee-server-api
 * File Name:LoginInterceptor.java
 * Package Name:com.coffee.api.core.interceptor
 * Date:2018年2月7日下午2:52:51
 * Copyright (c) 2018, Coffee Ease 2016-2018 All Rights Reserved.
 *
 */

package com.coffee.api.core.interceptor;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.coffee.api.core.common.GlobalConstants;
import com.coffee.api.core.dao.RedisResourceDao;

/**
 * ClassName:LoginInterceptor <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年2月7日 下午2:52:51 <br/>
 * @author   Jin.He (mailto:hejin@coffee-ease.com)
 * @version  
 * @see 	 
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Cookie[] cookies = request.getCookies();
		boolean falg = false;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (GlobalConstants.COOKIE_NAME.equals(cookie.getName())) {
					long now = new Date().getTime();
					String key = GlobalConstants.TOKEN_PREFIX_IN_REDIS + cookie.getValue();
					String last = RedisResourceDao.Hash.hget(key, GlobalConstants.KEY_TIME);
					if (last != null) {
						falg = now - Long.parseLong(last) <= GlobalConstants.TOKEN_ABLE_TIMESTAMP;
					}
					break;
				}
			}
		}
		if (!falg) {
			response.sendRedirect("../userController/webLoginEntry");
		}
		return falg;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView)
			throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
	}
}

