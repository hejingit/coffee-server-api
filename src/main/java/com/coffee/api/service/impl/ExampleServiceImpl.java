/**
 * Project Name:coffee-server-api
 * File Name:ExampleServiceImpl.java
 * Package Name:com.coffee.api.service.impl
 * Date:2018年2月7日下午6:47:14
 * Copyright (c) 2018, Coffee Ease 2016-2018 All Rights Reserved.
 *
 */

package com.coffee.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coffee.api.entity.Example;
import com.coffee.api.mapper.ExampleMapper;
import com.coffee.api.service.ExampleService;

/**
 * ClassName:ExampleServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年2月7日 下午6:47:14 <br/>
 * @author   Jin.He (mailto:hejin@coffee-ease.com)
 * @version  
 * @see 	 
 */
@Service
public class ExampleServiceImpl implements ExampleService {
	
	@Autowired
	private ExampleMapper exampleMapper;

	@Override
	public Example getExampleById(Integer id) {
		return exampleMapper.getExampleById(id);
	}

	@Override
	public List<Example> getExampleList() {
		return exampleMapper.getExampleList();
	}

	@Override
	public int add(Example user) {
		return exampleMapper.add(user);
	}

	@Override
	public int update(Integer id, Example user) {
		return exampleMapper.update(id, user);
	}

	@Override
	public int delete(Integer id) {
		return exampleMapper.delete(id);
	}
}

