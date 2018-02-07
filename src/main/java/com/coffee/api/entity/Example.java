/**
 * Project Name:coffee-server-api
 * File Name:Example.java
 * Package Name:com.coffee.api.entity
 * Date:2018年2月7日下午6:33:10
 * Copyright (c) 2018, Coffee Ease 2016-2018 All Rights Reserved.
 *
 */

package com.coffee.api.entity;

import java.util.Date;

/**
 * ClassName:Example <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年2月7日 下午6:33:10 <br/>
 * @author   Jin.He (mailto:hejin@coffee-ease.com)
 * @version  
 * @see 	 
 */
public class Example {
	private int id;
	private String username;
	private int age;
	private Date ctm;

	public Example() {
	}

	public Example(String username, int age) {
		this.username = username;
		this.age = age;
		this.ctm = new Date();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getCtm() {
		return ctm;
	}

	public void setCtm(Date ctm) {
		this.ctm = ctm;
	}
	
}

