package com.miaosha.redis;

public class UserKey extends BasePrefix{

	
	public UserKey(String prefix) {
		super(prefix);
		// TODO Auto-generated constructor stub
	}

	public static UserKey getById = new UserKey("id");
	public static UserKey getByIdName = new UserKey("name");
	
}
