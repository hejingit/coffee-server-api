/**
 * Project Name:coffee-server-api
 * File Name:RedisService.java
 * Package Name:com.coffee.api.core.service
 * Date:2018年2月7日下午2:59:00
 * Copyright (c) 2018, Coffee Ease 2016-2018 All Rights Reserved.
 *
 */

package com.coffee.api.core.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coffee.api.core.bean.User;
import com.coffee.api.core.common.GlobalConstants;
import com.coffee.api.core.dao.RedisResourceDao;

/**
 * ClassName:RedisService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年2月7日 下午2:59:00 <br/>
 * @author   Jin.He (mailto:hejin@coffee-ease.com)
 * @version  
 * @see 	 
 */
@Service("redisService")
public class RedisService {
	Logger logger = LoggerFactory.getLogger(RedisService.class);

	@Autowired
	UserService userService;
	
	public void setToken(Map<String, String> tokenMap, Integer id, String token) {
		String idKey = GlobalConstants.LOGIN_PREFIX_IN_REDIS + id;
		String tokenKey = GlobalConstants.TOKEN_PREFIX_IN_REDIS + token;
		String oldToken = RedisResourceDao.Hash.hget(idKey, GlobalConstants.KEY_TOKEN);
		if (oldToken != null) {
			RedisResourceDao.Hash.hdel(GlobalConstants.TOKEN_PREFIX_IN_REDIS + oldToken);
			
		}
		RedisResourceDao.Hash.hset(idKey, GlobalConstants.KEY_TOKEN, token);
		RedisResourceDao.Hash.hmset(tokenKey, tokenMap);
	}
	
	public void updateToken(int id, String fieid, String value) {
		String idKey = GlobalConstants.LOGIN_PREFIX_IN_REDIS + id;
		String token = RedisResourceDao.Hash.hget(idKey, GlobalConstants.KEY_TOKEN);
		if (token != null) {
			String tokenKey = GlobalConstants.TOKEN_PREFIX_IN_REDIS + token;
			RedisResourceDao.Hash.hset(tokenKey, fieid, value);
		}
	}

	public boolean flush() {
		List<User> list = userService.findAll();
		if (list != null) {
			Map<String, String> tokenMap = new HashMap<String, String>();
			for (User user : list) {
				tokenMap.put(GlobalConstants.KEY_SIGN, user.getSign());
				tokenMap.put(GlobalConstants.KEY_ABLE, GlobalConstants.VALUE_TRUE);
				setToken(tokenMap, user.getId(), user.getToken());
			}
		}
		return true;
	}
	
}
