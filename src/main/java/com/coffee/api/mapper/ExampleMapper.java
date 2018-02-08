/**
 * Project Name:coffee-server-api
 * File Name:ExampleMapper.java
 * Package Name:com.coffee.api.mapper
 * Date:2018年2月7日下午6:35:17
 * Copyright (c) 2018, Coffee Ease 2016-2018 All Rights Reserved.
 *
 */

package com.coffee.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.coffee.api.entity.Example;

/**
 * ClassName:ExampleMapper <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年2月7日 下午6:35:17 <br/>
 * @author   Jin.He (mailto:hejin@coffee-ease.com)
 * @version  
 * @Mapper 这里可以使用@Mapper注解，但是每个mapper都加注解比较麻烦，所以统一配置@MapperScan在扫描路径在application类中
 * @see 	 
 */
public interface  ExampleMapper {
	
	@Select("SELECT * FROM api_example WHERE id = #{id}")
	Example getExampleById(Integer id);

	@Select("SELECT * FROM api_example")
	public List<Example> getExampleList();

	@Insert("INSERT INTO api_example(username, age, ctm) values(#{username}, #{age}, now())")
	public int add(Example example);

	@Update("UPDATE api_example SET username = #{user.username} , age = #{user.age} WHERE id = #{id}")
	public int update(@Param("id") Integer id, @Param("user") Example example);

	@Delete("DELETE from api_example where id = #{id} ")
	public int delete(Integer id);
	
	@Select("Select count(*) from api_example")
	public int countExample();
}

