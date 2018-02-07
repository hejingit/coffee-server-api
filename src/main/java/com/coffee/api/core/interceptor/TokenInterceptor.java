/**
 * Project Name:coffee-server-api
 * File Name:TokenInterceptor.java
 * Package Name:com.coffee.api.core.interceptor
 * Date:2018年2月7日下午2:54:36
 * Copyright (c) 2018, Coffee Ease 2016-2018 All Rights Reserved.
 *
 */

package com.coffee.api.core.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.coffee.api.core.bean.CoffeeResponse;
import com.coffee.api.core.common.GlobalConstants;
import com.coffee.api.core.dao.RedisResourceDao;
import com.coffee.api.util.DesUtils;
import com.coffee.api.util.Md5Encrypt;

/**
 * ClassName:TokenInterceptor <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年2月7日 下午2:54:36 <br/>
 * @author   Jin.He (mailto:hejin@coffee-ease.com)
 * @version  
 * @see 	 
 */
public class TokenInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		try {
			String token = request.getParameter(GlobalConstants.KEY_TOKEN);
			String md5sign = request.getParameter(GlobalConstants.KEY_MD5_SIGN);
			String data = request.getParameter(GlobalConstants.KEY_DATA);
			logger.info("checkToken: token={}, md5sign={}, data={}", token, md5sign, data);
			// 校验token
			if (StringUtils.isBlank(token)) {
				writeErrorMsg(response.getWriter(), "token不能为空！");
				return false;
			}
			String key = GlobalConstants.TOKEN_PREFIX_IN_REDIS + token;
			Map<String, String> tokenMap = RedisResourceDao.Hash.hgetAll(key);
			if (tokenMap == null || tokenMap.size() == 0) {
				writeErrorMsg(response.getWriter(), "token无效！");
				return false;
			}
//			long now = new Date().getTime();
//			if (now - Long.parseLong(tokenMap.get(GlobalConstants.KEY_TIME)) > GlobalConstants.TOKEN_ABLE_TIMESTAMP) {
//				writeErrorMsg(response.getWriter(), "token已过期！");
//				return false;
//			}
			if (!GlobalConstants.VALUE_TRUE.equals(tokenMap.get(GlobalConstants.KEY_ABLE))) {
				writeErrorMsg(response.getWriter(), "账号不可用！");
				return false;
			}
			// 校验md5sign
			if (StringUtils.isBlank(md5sign)) {
				writeErrorMsg(response.getWriter(), "md5sign不能为空！");
				return false;
			}
			if (StringUtils.isBlank(data)) {
				writeErrorMsg(response.getWriter(), "data不能为空！");
				return false;
			}
			String sign = tokenMap.get(GlobalConstants.KEY_SIGN);
			data = analyzeData(data, sign, request,token);
			if (data == null) {
				writeErrorMsg(response.getWriter(), "data无法解密，请检查加密方式或密钥是否正确！");
				return false;
			}
			if (!md5sign.equals(Md5Encrypt.md5(data))) {
				writeErrorMsg(response.getWriter(), "md5sign校验失败！");
				return false;
			}
			request.setAttribute(GlobalConstants.KEY_SIGN, sign);
			
//			String msg = checkMd5Sign(request.getParameterMap(), sign);
//			if (msg != null) {
//				writeErrorMsg(response.getWriter(), msg);
//				return false;
//			}
//			RedisClusterResourceDao.Hash.hset(key, "time", String.valueOf(now));
		} catch (Exception e) {
			logger.error("checkToken-exception.", e);
			writeErrorMsg(response.getWriter(), "网络繁忙，请稍后重试！");
			return false;
		}
		return true;
	}

	private String analyzeData(String data, String sign, HttpServletRequest request,String token) throws Exception {
		try {
			data = DesUtils.decrypt(data, sign);
		} catch (Exception e) {
			logger.info("analyze failed.", e);
			return null;
		}
		String[] kvs = data.split("&");
		for (String kv : kvs) {
			String[] split = kv.split("=", 2);
			if (split.length == 2 && !"".equals(split[0]) && !"".equals(split[1])) {
				request.setAttribute(split[0], split[1]);
			}
		}
		request.setAttribute("token", token);
		return com.coffee.api.util.StringUtils.append(data, "&sign=", sign);
	}

	private void writeErrorMsg(Writer writer, String msg) throws IOException {
		logger.info("checkToken-writeErrorMsg={}", msg);
		CoffeeResponse<String> result = new CoffeeResponse<String>();
		result.setErrorMsg(msg);
		result.setResult(CoffeeResponse.ERROR);
		writer.write(JSON.toJSONString(result));
		writer.close();
	}

//	private String checkMd5Sign(Map<String, String[]> params, String sign) {
//		TreeMap<String, String> treeMap = new TreeMap<String, String>();
//		String signValue = null;
//		for (Entry<String, String[]> entry : params.entrySet()) {
//			String key = entry.getKey();
//			String[] value = entry.getValue();
//			if (GlobalConstants.KEY_MD5_SIGN.equals(key)) {
//				signValue = value[0];
//				continue;
//			}
//			if (value != null && value.length > 0 && StringUtils.isNoneBlank(value[0])) {
//				treeMap.put(key, value[0]);
//			}
//		}
//		StringBuilder sb = new StringBuilder();
//		for (Entry<String, String> entry : treeMap.entrySet()) {
//			sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
//		}
//		sb.append(GlobalConstants.KEY_SIGN).append("=").append(sign);
//		if (!signValue.equals(Md5Encrypt.md5(sb.toString()))) {
//			return "md5sign校验失败！";
//		}
//		return null;
//	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView)
			throws Exception {
		CoffeeResponse result = (CoffeeResponse) request.getAttribute(GlobalConstants.KEY_RESULT);
		if (request != null) {
			Object content = result.getContent();
			if (content != null) {
				String json = JSON.toJSONString(content);
				String key = (String) request.getAttribute(GlobalConstants.KEY_SIGN);
				result.setContent(DesUtils.encrypt(json, key));
			}
		}
		PrintWriter writer = response.getWriter();
		logger.info("Response:"+JSON.toJSONString(result));
		writer.write(JSON.toJSONString(result));
		writer.close();
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
	}
}

