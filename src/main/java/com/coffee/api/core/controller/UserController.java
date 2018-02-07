/**
 * Project Name:coffee-server-api
 * File Name:UserController.java
 * Package Name:com.coffee.api.core.controller
 * Date:2018年2月7日下午2:48:22
 * Copyright (c) 2018, Coffee Ease 2016-2018 All Rights Reserved.
 *
 */

package com.coffee.api.core.controller;

import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.coffee.api.core.bean.DataGrid;
import com.coffee.api.core.bean.Page;
import com.coffee.api.core.bean.User;
import com.coffee.api.core.common.GlobalConstants;
import com.coffee.api.core.service.RedisService;
import com.coffee.api.core.service.UserService;
import com.coffee.api.util.DesUtils;

/**
 * ClassName:UserController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年2月7日 下午2:48:22 <br/>
 * @author   Jin.He (mailto:hejin@coffee-ease.com)
 * @version  
 * @see 	 
 */

@Controller
@RequestMapping("/userController")
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	UserService userService;
	@Autowired
	RedisService redisService;

	@RequestMapping(value = "/layout")
	public String layout() {
		return "basic/layout";
	}

	@RequestMapping(value = "/userListEntry")
	public String userListEntry() {
		return "user/userList";
	}

	@RequestMapping(value = "/userList")
	@ResponseBody
	public String userList(ModelMap modelMap, User user, Page page) {
		DataGrid dataGrid = userService.find(user, page);
		return JSON.toJSONString(dataGrid);
	}

	@RequestMapping(value = "/addUserEntry")
	public String addUserEntry() {
		return "user/addUser";
	}

	@RequestMapping(value = "/addUser")
	@ResponseBody
	public String addUser(String username,String orgid) {
		if (StringUtils.isBlank(username)) {
			return "用户名不能为空！";
		}
		if (StringUtils.isBlank(orgid)) {
            return "教育机构ID不能为空！";
        }
		username = username.trim();
		if (username.length() > 32) {
			return "用户名长度限制在32位以内！";
		}
		return userService.addUser(username,orgid);
	}

	@RequestMapping(value = "/updateToken")
	@ResponseBody
	public String updateToken(Integer id) {
		if (id == null) {
			return null;
		}
		return userService.updateToken(id);
	}

	@RequestMapping(value = "/ableUser")
	@ResponseBody
	public boolean ableUser(Integer id, Boolean able) {
		if (id == null || able == null) {
			return false;
		}
		return userService.ableUser(id, able);
	}

	@RequestMapping(value = "/webLoginEntry")
	public String webLoginEntry() {
		return "basic/login";
	}

	@RequestMapping(value = "/webLogin")
	@ResponseBody
	public String webLogin(String username, String password) {
		Map<String, String> result = new HashMap<String, String>(2);
		try {
			// 字段检查
			if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
				result.put("msg", "用户名或密码不能为空！");
				return JSON.toJSONString(result);
			}
			username = username.trim();
			password = password.trim();
			User user = userService.find(username, password);
			if (user == null) {
				result.put("msg", "用户名或密码错误！");
				return JSON.toJSONString(result);
			}
			if (!user.isAdmin()) {
				result.put("msg", "只有管理员可访问！");
				return JSON.toJSONString(result);
			}
			Integer id = user.getId();
			String token = user.getToken();
			Map<String, String> tokenMap = new HashMap<String, String>();
			tokenMap.put(GlobalConstants.KEY_TIME, String.valueOf(new Date().getTime()));
//			tokenMap.put(GlobalConstants.KEY_TOKEN, token);
			redisService.setToken(tokenMap, id, token);
			result.put("cookie", token);
		} catch (Exception e) {
			result.put("msg", "服务异常：" + e.getMessage());
			logger.error("webLogin failed: username={}, password={}", username, password, e);
		}
		return JSON.toJSONString(result);
	}

	@RequestMapping(value = "/flushRedis ")
	@ResponseBody
	public boolean flushRedis() {
		logger.info("flush redis.");
		return redisService.flush();
	}

	@RequestMapping(value = "/desUtilsEntry")
	private String desUtilsEntry() {
		return "des/desUtil";
	}

	@RequestMapping(value = "/encrypt")
	@ResponseBody
	private String encrypt(String text, String key) {
		try {
			if (StringUtils.isBlank(text)) {
				return "data不能为空！";
			}
			text = URLDecoder.decode(text, "UTF-8");
			if (StringUtils.isNotBlank(key)) {
				String data = DesUtils.encrypt(text, key);
				logger.info("text={}, key={}: encrypt={}", text, key, data);
				return data;
			}
			String data = DesUtils.encrypt(text);
			logger.info("text={}: encrypt={}", text, data);
			return data;
		} catch (Exception e) {
			logger.error("des encrypt.", e);
			return "加密失败，请检查相关参数！" + e.getMessage();
		}
	}

	@RequestMapping(value = "/decrypt")
	@ResponseBody
	private String decrypt(String text, String key) {
		try {
			if (StringUtils.isBlank(text)) {
				return "data不能为空！";
			}
			if (StringUtils.isNotBlank(key)) {
				String data = DesUtils.decrypt(text, key);
				logger.info("text={}, key={}: decrypt={}", text, key, data);
				return data;
			}
			String data = DesUtils.decrypt(text);
			logger.info("text={}: decrypt={}", text, data);
			return data;
		} catch (Exception e) {
			logger.error("des encrypt.", e);
			return "解密失败，请检查相关参数！" + e.getMessage();
		}
	}
	
	@RequestMapping(value = "/appInfo")
	@ResponseBody
	private String appInfo() {
		ResourceBundle bundle = ResourceBundle.getBundle("application");
		StringBuilder sb = new StringBuilder();
		Set<String> keys = bundle.keySet();
		for (String key : keys) {
			sb.append(key).append("=").append(bundle.getObject(key)).append(";\r\n");
		}
		return sb.toString();
	}

}

