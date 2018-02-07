package com.coffee.api.util;

import java.util.UUID;

public class UuidUtils {

	/**
	 * 获取32位的UUID
	 */
	public static String get32Uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	/**
	 * 获取64位的UUID
	 */
	public static String get64Uuid() {
		return get32Uuid() + get32Uuid();
	}
	
}
