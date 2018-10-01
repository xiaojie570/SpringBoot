package com.miaosha.redis;

public abstract class BasePrefix implements KeyPrefix{

	private int expireSeconds;
	private String prefix;
	
	
	public BasePrefix(String prefix) {
		
		this(0,prefix);
	}

	public BasePrefix(int expireSeconds, String prefix) {
		super();
		this.expireSeconds = expireSeconds;
		this.prefix = prefix;
	}

	// 默认0代表永不过期
	public int expireSeconds() {
		return expireSeconds;
	}

	// 
	public String getPrefix() {
		String className = getClass().getSimpleName();
		return className + prefix;
	}

}
