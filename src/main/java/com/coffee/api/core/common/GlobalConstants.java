/**
 * Project Name:coffee-server-api
 * File Name:GlobalConstants.java
 * Package Name:com.coffee.api.core.common
 * Date:2018年2月7日下午2:42:59
 * Copyright (c) 2018, Coffee Ease 2016-2018 All Rights Reserved.
 *
 */

package com.coffee.api.core.common;

import java.util.ResourceBundle;

/**
 * ClassName:GlobalConstants <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年2月7日 下午2:42:59 <br/>
 * @author   Jin.He (mailto:hejin@coffee-ease.com)
 * @version  
 * @see 	 
 */
public class GlobalConstants {
	private static long ONE_HOUR_TIMESTAMP = 60 * 60 * 1000;
	public static long TOKEN_ABLE_TIMESTAMP;
	public static String KEY_TOKEN = "token";
	public static String KEY_DATA = "data";
	public static String KEY_SIGN = "sign";
	public static String KEY_TIME = "time";
	public static String KEY_ABLE = "able";
	public static String VALUE_TRUE = "true";
	public static String KEY_MD5_SIGN = "md5sign";
	public static String REDIS_KEYPREFIX = getProp("redis.keyprefix") + "_";
	public static String LOGIN_PREFIX_IN_REDIS = REDIS_KEYPREFIX + "cf_cr_lgn_";
	public static String TOKEN_PREFIX_IN_REDIS = REDIS_KEYPREFIX + "cf_cr_tkn_";
	public static String COOKIE_NAME = "coffee_login_cookie";
	public static String KEY_RESULT = "result";

	public static final Integer WEX_REDEEMCODE_LENGTH = 5;
	public static final Integer COFFEEBEAN_REDEEMCODE_LENGTH = 6;

	public static String ERROR_MSG = "未查询到申请码信息";
	
	public static final String INVALID_DATE_KEY="coffeebean.applycode.invalid.day";

	static {
		ResourceBundle bundle = ResourceBundle.getBundle("application");
		TOKEN_ABLE_TIMESTAMP = ONE_HOUR_TIMESTAMP * Integer.parseInt(bundle.getString("token_able_hour"));
	}
	
	private static String getProp(String key){
	    ResourceBundle bundle = ResourceBundle.getBundle("application");
	    return bundle.getString(key);
	}
}

