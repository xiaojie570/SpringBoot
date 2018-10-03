package com.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
	// 
	public static String md5(String src) {
		return DigestUtils.md5Hex(src);
	}
	
	// 固定的salt
	private static final String salt = "1a2b3c4d";
	
	// 用户输入的密码 + 固定的salt --> 将用户在页面输入的密码进行第一次md5加密
	public static String inputPassToFormPass(String inputPass) {
		String str = ""+salt.charAt(0)+salt.charAt(2) + inputPass +salt.charAt(5) + salt.charAt(4);// 12123456c3
		System.out.println(str);
		return md5(str);
	}
	
	// 在第一次md5加密的基础上，又进行第二次md5加密， 这个salt是一个随机的salt
	public static String formPassToDBPass(String formPass, String salt) {
		String str = ""+salt.charAt(0)+salt.charAt(2) + formPass +salt.charAt(5) + salt.charAt(4); // 12123456c3
		return md5(str);
	}
	
	// 将用户输入的明文密码转化为输入数据库的两次md5密码
	public static String inputPassToDbPass(String inputPass, String saltDB) {
		String formPass = inputPassToFormPass(inputPass);
		String dbPass = formPassToDBPass(formPass, saltDB);
		return dbPass;
	}
	

	
	/*public static void main(String[] args) {
		System.out.println(inputPassToFormPass("123456"));//d3b1294a61a07da9b49b6e22b2cbd7f9
		System.out.println(formPassToDBPass(inputPassToFormPass("123456"), "1a2b3c4d")); // b7797cce01b4b131b433b6acf4add449
		System.out.println(inputPassToDbPass("123456", "1a2b3c4d"));//b7797cce01b4b131b433b6acf4add449

	}*/
}
