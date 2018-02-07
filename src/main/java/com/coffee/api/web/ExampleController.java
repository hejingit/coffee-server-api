/**
 * Project Name:coffee-server-api
 * File Name:ExampleController.java
 * Package Name:com.coffee.api.web
 * Date:2018年2月7日下午6:53:52
 * Copyright (c) 2018, Coffee Ease 2016-2018 All Rights Reserved.
 *
 */

package com.coffee.api.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.coffee.api.entity.Example;
import com.coffee.api.entity.JsonResult;
import com.coffee.api.service.ExampleService;

/**
 * ClassName:ExampleController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年2月7日 下午6:53:52 <br/>
 * @author   Jin.He (mailto:hejin@coffee-ease.com)
 * @version  
 * @see 	 
 */
@RestController
@RequestMapping("/api")
public class ExampleController {
	@Autowired
	private ExampleService exampleService;
	
	/**
	 * 根据ID查询信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "example/{id}", method = RequestMethod.GET)
	public ResponseEntity<JsonResult> getExampleById (@PathVariable(value = "id") Integer id){
		JsonResult r = new JsonResult();
		try {
			Example example = exampleService.getExampleById(id);
			r.setResult(example);
			r.setStatus("ok");
		} catch (Exception e) {
			r.setResult(e.getClass().getName() + ":" + e.getMessage());
			r.setStatus("error");
			e.printStackTrace();
		}
		return ResponseEntity.ok(r);
	}

	/**
	 * 查询信息列表
	 * @return
	 */
	@RequestMapping(value = "examples", method = RequestMethod.GET)
	public ResponseEntity<JsonResult> getUserList (){
		JsonResult r = new JsonResult();
		try {
			List<Example> examples = exampleService.getExampleList();
			r.setResult(examples);
			r.setStatus("ok");
		} catch (Exception e) {
			r.setResult(e.getClass().getName() + ":" + e.getMessage());
			r.setStatus("error");
			e.printStackTrace();
		}
		return ResponseEntity.ok(r);
	}

	/**
	 * 添加信息
	 * @param example
	 * @return
	 */
	@RequestMapping(value = "example", method = RequestMethod.POST)
	public ResponseEntity<JsonResult> add (@RequestBody Example example){
		JsonResult r = new JsonResult();
		try {
			int orderId = exampleService.add(example);
			if (orderId < 0) {
				r.setResult(orderId);
				r.setStatus("fail");
			} else {
				r.setResult(orderId);
				r.setStatus("ok");
			}
		} catch (Exception e) {
			r.setResult(e.getClass().getName() + ":" + e.getMessage());
			r.setStatus("error");

			e.printStackTrace();
		}
		return ResponseEntity.ok(r);
	}

	/**
	 * 根据id删除信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "example/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<JsonResult> delete (@PathVariable(value = "id") Integer id){
		JsonResult r = new JsonResult();
		try {
			int ret = exampleService.delete(id);
			if (ret < 0) {
				r.setResult(ret);
				r.setStatus("fail");
			} else {
				r.setResult(ret);
				r.setStatus("ok");
			}
		} catch (Exception e) {
			r.setResult(e.getClass().getName() + ":" + e.getMessage());
			r.setStatus("error");

			e.printStackTrace();
		}
		return ResponseEntity.ok(r);
	}

	/**
	 * 根据id修改信息
	 * @param example
	 * @return
	 */
	@RequestMapping(value = "example/{id}", method = RequestMethod.PUT)
	public ResponseEntity<JsonResult> update (@PathVariable("id") Integer id, @RequestBody Example example){
		JsonResult r = new JsonResult();
		try {
			int ret = exampleService.update(id, example);
			if (ret < 0) {
				r.setResult(ret);
				r.setStatus("fail");
			} else {
				r.setResult(ret);
				r.setStatus("ok");
			}
		} catch (Exception e) {
			r.setResult(e.getClass().getName() + ":" + e.getMessage());
			r.setStatus("error");

			e.printStackTrace();
		}
		return ResponseEntity.ok(r);
	}

}

