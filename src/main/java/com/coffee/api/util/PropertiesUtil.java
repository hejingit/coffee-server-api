/**
 * Project Name:coffee-server-api
 * File Name:PropertiesUtil.java
 * Package Name:com.coffee.api.util
 * Date:2018年2月7日下午2:35:39
 * Copyright (c) 2018, Coffee Ease 2016-2018 All Rights Reserved.
 *
 */

package com.coffee.api.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * ClassName:PropertiesUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年2月7日 下午2:35:39 <br/>
 * @author   Jin.He (mailto:hejin@coffee-ease.com)
 * @version  
 * @see 	 
 */
public class PropertiesUtil {
	 private Properties props;
	    private URI uri;
	    private static final String fileName = "/application.properties";

	    public PropertiesUtil(String fileName) {
	        readProperties(fileName);
	    }

	    public PropertiesUtil() {
	        readProperties(fileName);
	    }

	    private void readProperties(String fileName) {
	        try {
	            props = new Properties();
	            InputStream fis = getClass().getResourceAsStream(fileName);
	            props.load(fis);
	            uri = this.getClass().getResource(fileName).toURI();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    /**
	     * 获取某个属性
	     */
	    public String getProperty(String key) {
	        return props.getProperty(key);
	    }
	    /**
	     * 获取所有属性，返回一个map,不常用 可以试试props.putAll(t)
	     */
	    @SuppressWarnings("rawtypes")
	    public Map<String, String> getAllProperty() {
	        Map<String, String> map = new HashMap<String, String>();
	        Enumeration enu = props.propertyNames();
	        while (enu.hasMoreElements()) {
	            String key = (String) enu.nextElement();
	            String value = props.getProperty(key);
	            map.put(key, value);
	        }
	        return map;
	    }
	    /**
	     * 在控制台上打印出所有属性，调试时用。
	     */
	    public void printProperties() {
	        props.list(System.out);
	    }
	    /**
	     * 写入properties信息
	     */
	    public void writeProperties(String key, String value) {
	        try {
	            OutputStream fos = new FileOutputStream(new File(uri));
	            props.setProperty(key, value);
	            // 将此 Properties 表中的属性列表（键和元素对）写入输出流
	            props.store(fos, "『comments』Update key：" + key);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}


