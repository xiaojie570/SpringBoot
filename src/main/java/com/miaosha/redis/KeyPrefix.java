package com.miaosha.redis;

/**
 * 将各个模块进行区分
 * @author ACER
 *
 */
public interface KeyPrefix {
	public int expireSeconds();
	
	public String getPrefix();
}
