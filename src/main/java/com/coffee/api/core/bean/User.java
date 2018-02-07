/**
 * Project Name:coffee-server-api
 * File Name:User.java
 * Package Name:com.coffee.api.core.bean
 * Date:2018年2月7日下午2:40:58
 * Copyright (c) 2018, Coffee Ease 2016-2018 All Rights Reserved.
 *
 */

package com.coffee.api.core.bean;
/**
 * ClassName:User <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年2月7日 下午2:40:58 <br/>
 * @author   Jin.He (mailto:hejin@coffee-ease.com)
 * @version  
 * @see 	 
 */
public class User {
	
	private Integer id;
	private String username;
	private String password;
	private String token;
	private String sign;
	private boolean admin;
	private boolean able;
	private String createTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public boolean isAble() {
		return able;
	}
	public void setAble(boolean able) {
		this.able = able;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}

