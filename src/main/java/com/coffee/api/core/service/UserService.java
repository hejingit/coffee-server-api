/**
 * Project Name:coffee-server-api
 * File Name:UserService.java
 * Package Name:com.coffee.api.core.service
 * Date:2018年2月7日下午2:59:18
 * Copyright (c) 2018, Coffee Ease 2016-2018 All Rights Reserved.
 *
 */

package com.coffee.api.core.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.coffee.api.core.bean.DataGrid;
import com.coffee.api.core.bean.Page;
import com.coffee.api.core.bean.User;
import com.coffee.api.core.common.GlobalConstants;
import com.coffee.api.util.Md5Encrypt;
import com.coffee.api.util.TokenUtils;
import com.mysql.jdbc.Statement;

/**
 * ClassName:UserService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年2月7日 下午2:59:18 <br/>
 * @author   Jin.He (mailto:hejin@coffee-ease.com)
 * @version  
 * @see 	 
 */
@Service("userService")
public class UserService {
		Logger logger = LoggerFactory.getLogger(UserService.class);
		@Autowired
		@Qualifier("primaryJdbcTemplate")
		private JdbcTemplate jdbcTemplate;
		@Autowired
		private RedisService redisService;
		
		
		
		public String getOrgid(String token){
		    String sql = "select orgid from tbl_user where token=?";
		    return jdbcTemplate.queryForObject(sql, String.class,token);
		}

		public Integer findId(String username, String password) {
			String sql = "select id from tbl_user where username=? and password=?";
			return jdbcTemplate.queryForObject(sql, Integer.class, username, Md5Encrypt.md5(username + password));
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public User findById(int id) {
			try {
				String sql = "select * from tbl_user where id=?";
				return (User) jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper(User.class), id);
			} catch (EmptyResultDataAccessException e) {
			} catch (DataAccessException e) {
				logger.warn("find user failed: id={}.", id, e);
			}
			return null;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public User find(String username, String password) {
			try {
				String sql = "select * from tbl_user where username=? and password=?";
				return (User) jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper(User.class), username, Md5Encrypt.md5(username + password));
			} catch (EmptyResultDataAccessException e) {
			} catch (DataAccessException e) {
				logger.warn("find user failed: username={}, password={}.", username, password, e);
			}
			return null;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public DataGrid find(User user, Page page) {
			DataGrid dataGrid = new DataGrid();
			StringBuilder tempSql = new StringBuilder(" from tbl_user where 1=1");
			if (user != null) {
				String username = user.getUsername();
				if (StringUtils.isNotBlank(username)) {
					tempSql.append(" and username like '%").append(username.trim()).append("%'");
				}
				String token = user.getToken();
				if (StringUtils.isNotBlank(token)) {
					tempSql.append(" and token='").append(token.trim()).append("'");
				}
			}
			String sql = "select count(*)" + tempSql;
			Integer total = jdbcTemplate.queryForObject(sql, Integer.class);
			dataGrid.setTotal(total);
			if (page != null) {
				tempSql.append(" limit ").append((page.getPage() - 1) * page.getRows()).append(",").append(page.getRows());
			}
			sql = "select *" + tempSql;
			List list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class));
			dataGrid.setRows(list);
			return dataGrid;
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public List<User> findAll() {
			try {
				String sql = "select * from tbl_user";
				return jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class));
			} catch (DataAccessException e) {
				logger.warn("flush failed.", e);
			}
			return null;
		}

		public String addUser(final String username,String orgid) {
			try {
				String sql = "select id from tbl_user where username=?";
				Integer id = jdbcTemplate.queryForObject(sql, Integer.class, username);
				if (id != null) {
					return "[" + username + "]已经存在，添加失败！";
				}
			} catch (EmptyResultDataAccessException e) {

			} catch (DataAccessException e) {
				logger.warn("add user failed: username={}.", username, e);
				return e.getMessage();
			}

			final String token = TokenUtils.getToken(username);
			final String sign = TokenUtils.getSign();
			KeyHolder key = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					String sql = "insert into tbl_user(username, token, sign, orgid) values(?, ?, ?, ?)";
					PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, username);
					ps.setString(2, token);
					ps.setString(3, sign);
					ps.setString(4, orgid);
					return ps;
				}
			}, key);
			Map<String, String> tokenMap = new HashMap<String, String>();
			tokenMap.put(GlobalConstants.KEY_SIGN, sign);
//			tokenMap.put("time", String.valueOf(new Date().getTime()));
			tokenMap.put(GlobalConstants.KEY_ABLE, GlobalConstants.VALUE_TRUE);
			int id = key.getKey().intValue();
			redisService.setToken(tokenMap, id, token);
			tokenMap.remove(GlobalConstants.KEY_ABLE);
			tokenMap.put(GlobalConstants.KEY_TOKEN, token);
			tokenMap.put("username", username);
			return JSON.toJSONString(tokenMap);
		}

		public boolean ableUser(int id, boolean able) {
			try {
				String sql = "update tbl_user set able=? where id=?";
				jdbcTemplate.update(sql, able, id);
				redisService.updateToken(id, GlobalConstants.KEY_ABLE, String.valueOf(able));
			} catch (Exception e) {
				logger.warn("able user failed: id={}, able={}.", id, able, e);
				return false;
			}
			return true;
		}

		public String updateToken(int id) {
			try {
				String sql = "update tbl_user set token=?, sign=? where id=?";
				String token = TokenUtils.getToken(id);
				String sign = TokenUtils.getSign();
				jdbcTemplate.update(sql, token, sign, id);
				User user = findById(id);
				if (user != null) {
					Map<String, String> tokenMap = new HashMap<String, String>();
					tokenMap.put(GlobalConstants.KEY_SIGN, sign);
//					tokenMap.put("time", String.valueOf(new Date().getTime()));
					tokenMap.put(GlobalConstants.KEY_ABLE, String.valueOf(user.isAble()));
					redisService.setToken(tokenMap, id, token);
					tokenMap.put(GlobalConstants.KEY_TOKEN, token);
					return JSON.toJSONString(tokenMap);
				}
			} catch (DataAccessException e) {
				logger.warn("update token failed: id={}.", id, e);
			}
			return null;
		}
	}
