/**
 * Project Name:coffee-server-api
 * File Name:ExampleService.java
 * Package Name:com.coffee.api.service
 * Date:2018年2月7日下午6:41:35
 * Copyright (c) 2018, Coffee Ease 2016-2018 All Rights Reserved.
 *
 */

package com.coffee.api.service;

import java.util.List;

import com.coffee.api.entity.Example;

/**
 * ClassName:ExampleService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年2月7日 下午6:41:35 <br/>
 * @author   Jin.He (mailto:hejin@coffee-ease.com)
 * @version  
 * @see 	 
 */
public interface ExampleService {
	
	Example getExampleById(Integer id);

	public List<Example> getExampleList();
	
	public List<Example> getExampleListOther();
	
	public List<Example> getExampleListByPage(int currentPage,int pageSize);

	public int add(Example example);

	public int update(Integer id, Example example);

	public int delete(Integer id);
}

