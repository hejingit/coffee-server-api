package com.coffee.api.util;

public class TokenUtils {

	public static String getToken(Object pre) {
		return Md5Encrypt.md5(String.valueOf(pre)).substring(8, 24) + UuidUtils.get32Uuid();
	}
	public static String getSign() {
		return UuidUtils.get32Uuid();
	}
}
