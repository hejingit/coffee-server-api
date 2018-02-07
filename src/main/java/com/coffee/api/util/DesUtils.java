package com.coffee.api.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;



public class DesUtils {
	private static final String PASSWORD_CRYPT_KEY = "kEHrDooxWHCWtfeSxvDvgqZq";
	private final static String ALGORITHM = "DES";
	private final static String charsetName = "UTF-8";


	public final static String decrypt(String data) throws Exception {
		return new String(decrypt(hex2byte(data.getBytes(charsetName)), PASSWORD_CRYPT_KEY.getBytes(charsetName)), charsetName);
	}
	
	public final static String decrypt(String data, String key) throws Exception {
		return new String(decrypt(hex2byte(data.getBytes(charsetName)), key.getBytes(charsetName)), charsetName);
	}

	public final static String encrypt(String data, String key) throws Exception {
		return byte2hex(encrypt(data.getBytes(charsetName), key.getBytes(charsetName)));
	}
	
	public final static String encrypt(String data) throws Exception {
		return byte2hex(encrypt(data.getBytes(charsetName), PASSWORD_CRYPT_KEY.getBytes(charsetName)));
	}

	private static byte[] encrypt(byte[] data, byte[] key) {
		try {
			SecureRandom sr = new SecureRandom();
			DESKeySpec dks = new DESKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
			SecretKey securekey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
			return cipher.doFinal(data);
		} catch (Exception e) {
			System.err.print("encrypt fail:\n"+e);
			return null;
		}
	}

	private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		return cipher.doFinal(data);
	}

	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("the length is not even");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}
}