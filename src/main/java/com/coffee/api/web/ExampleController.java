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

import org.apache.log4j.Logger;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * ClassName:ExampleController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2018年2月7日 下午6:53:52 <br/>
 * 
 * @author Jin.He (mailto:hejin@coffee-ease.com)
 * @version
 * @see
 */
@Api(value = "/example", description = "API接口文档示例")
@RestController
@RequestMapping("/api")
public class ExampleController {
	
	private static final Logger log = Logger.getLogger(ExampleController.class);
	
	
	@Autowired
	private ExampleService exampleService;
	
	

	/**
	 * 根据ID查询信息
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "获取详细信息", notes = "根据url的id来获取详细信息")
	@ApiImplicitParam(name = "id", value = "信息ID", required = true, dataType = "Integer", paramType = "path")
	@RequestMapping(value = "example/{id}", method = RequestMethod.GET)
	public ResponseEntity<JsonResult> getExampleById(@PathVariable(value = "id") Integer id) {
		JsonResult r = new JsonResult();
		try {
			log.debug("根据url的id来获取详细信息ID"+id);
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
	 * 
	 * @return
	 */
	@ApiOperation(value = "获取信息列表", notes = "获取信息列表")
	@RequestMapping(value = "examples", method = RequestMethod.GET)
	public ResponseEntity<JsonResult> getUserList() {
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
	 * 查询信息列表 其他數據源
	 * 
	 * @return
	 */
	@ApiIgnore//使用该注解忽略这个API
	@ApiOperation(value = "其他數據源获取信息列表", notes = "其他數據源获取信息列表")
	@RequestMapping(value = "examples2", method = RequestMethod.GET)
	public ResponseEntity<JsonResult> getUserList2() {
		JsonResult r = new JsonResult();
		try {
			List<Example> examples = exampleService.getExampleListOther();
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
	 * 查询信息列表
	 * 
	 * @return int currentPage,int pageSize
	 */
	// @ApiResponses({
	// @ApiResponse(code=400,message="请求参数没填好"),
	// @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
	// })

	@ApiOperation(value = "分页获取信息列表", notes = "分页获取信息列表")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", name = "currentPage", dataType = "Integer", required = true, value = "当前页", defaultValue = "1"),
			@ApiImplicitParam(paramType = "path", name = "pageSize", dataType = "Integer", required = true, value = "页数", defaultValue = "10") })
	@RequestMapping(value = "examples4page/{currentPage}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<JsonResult> getUserListByPage(@PathVariable("currentPage") Integer currentPage,
			@PathVariable("pageSize") Integer pageSize) {
		JsonResult r = new JsonResult();
		try {
			List<Example> examples = exampleService.getExampleListByPage(currentPage, pageSize);
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
	 * 
	 * @param example
	 * @return
	 */
	@ApiOperation(value = "创建基本信息", notes = "根据基础对象创建基本信息")
	@ApiImplicitParam(name = "example", value = "详细实体example", required = true, dataType = "Example")
	@RequestMapping(value = "example", method = RequestMethod.POST)
	public ResponseEntity<JsonResult> add(@RequestBody Example example) {
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
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value="删除信息", notes="根据url的id来指定删除信息")
	@ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path")
	@RequestMapping(value = "example/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<JsonResult> delete(@PathVariable(value = "id") Integer id) {
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
	 * 
	 * @param example
	 * @return
	 */
	@ApiOperation(value="更新信息", notes="根据url的id来指定更新信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long",paramType = "path"),
			@ApiImplicitParam(name = "user", value = "实体example", required = true, dataType = "Example")
	})
	@RequestMapping(value = "example/{id}", method = RequestMethod.PUT)
	public ResponseEntity<JsonResult> update(@PathVariable("id") Integer id, @RequestBody Example example) {
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
