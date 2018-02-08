/**
 * Project Name:coffee-server-api
 * File Name:OtherExampleMapper.java
 * Package Name:com.coffee.api.mapper.other
 * Date:2018年2月8日下午3:35:34
 * Copyright (c) 2018, Coffee Ease 2016-2018 All Rights Reserved.
 *
 */

package com.coffee.api.othermapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.coffee.api.entity.Example;

/**
 * ClassName:OtherExampleMapper <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年2月8日 下午3:35:34 <br/>
 * @author   Jin.He (mailto:hejin@coffee-ease.com)
 * @version  
 * @see 	 
 */
public interface OtherExampleMapper {
	
	@Select("SELECT * FROM api_example")
	public List<Example> getExampleList();
}

