/**
 * Project Name:coffee-server-api
 * File Name:IndexController.java
 * Package Name:com.coffee.api.core.common
 * Date:2018年2月7日下午2:45:46
 * Copyright (c) 2018, Coffee Ease 2016-2018 All Rights Reserved.
 *
 */

package com.coffee.api.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName:IndexController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年2月7日 下午2:45:46 <br/>
 * @author   Jin.He (mailto:hejin@coffee-ease.com)
 * @version  
 * @see 	 
 */
@Controller
public class IndexController {
	
	@RequestMapping(value = { "/index", "" })
	@ResponseBody
	public String helloboot() {
		return "欢迎使用咖啡易融接口服务,访问api请参考接口文档说明.";
	}
}

