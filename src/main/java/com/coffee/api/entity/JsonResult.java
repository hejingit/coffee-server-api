/**
 * Project Name:coffee-server-api
 * File Name:JsonResult.java
 * Package Name:com.coffee.api.entity
 * Date:2018年2月7日下午6:51:02
 * Copyright (c) 2018, Coffee Ease 2016-2018 All Rights Reserved.
 *
 */

package com.coffee.api.entity;
/**
 * ClassName:JsonResult <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年2月7日 下午6:51:02 <br/>
 * @author   Jin.He (mailto:hejin@coffee-ease.com)
 * @version  
 * @see 	 
 */
public class JsonResult {
	private String status = null;

	private Object result = null;

	public JsonResult status(String status) {
		this.status = status;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
}

