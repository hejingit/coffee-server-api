package com.coffee.api.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * ClassName: Md5Encrypt <br/>
 * Function: 数字摘要 <br/>
 * date: 2018年2月7日 下午3:02:53 <br/>
 *
 * @author Jin.He (mailto:hejin@coffee-ease.com)
 * @version
 */
public class Md5Encrypt {

	public synchronized static String md5(String text) {
		MessageDigest msgDigest = null;
		try {
			// 返回实现指定摘要算法的 MessageDigest对象,用于MD5
			msgDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(
					"System doesn't support MD5 algorithm.");
		}
		msgDigest.update(text.getBytes());
		byte[] bytes = msgDigest.digest();

		byte tb;
		char low;
		char high;
		char tmpChar;

		String md5Str = new String();

		for (int i = 0; i < bytes.length; i++) {
			tb = bytes[i];

			tmpChar = (char) ((tb >>> 4) & 0x000f);

			if (tmpChar >= 10) {
				high = (char) (('a' + tmpChar) - 10);
			} else {
				high = (char) ('0' + tmpChar);
			}
			md5Str += high;
			tmpChar = (char) (tb & 0x000f);
			if (tmpChar >= 10) {
				low = (char) (('a' + tmpChar) - 10);
			} else {
				low = (char) ('0' + tmpChar);
			}

			md5Str += low;
		}
		return md5Str;
	}
	public synchronized static String md5Upper(String text) {
		return md5(text).toUpperCase();
	}
	 
}
