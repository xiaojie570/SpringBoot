package com.miaosha.redis;

public class MiaoshaUserKey extends BasePrefix{

	private static final int TOKEN_EXPIRE = 3600*24*2;
	public MiaoshaUserKey(int expireSeconds, String prefix) {
		super(expireSeconds,prefix);
		// TODO Auto-generated constructor stub
	}
	
	public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE,"tk");
	// 对象缓存希望是永久有效的
	public static MiaoshaUserKey getById = new MiaoshaUserKey(0,"id");
	
}
