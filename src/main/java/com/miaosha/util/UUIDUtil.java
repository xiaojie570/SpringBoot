package com.miaosha.util;

import java.util.UUID;

public class UUIDUtil {
	public static String uuid() {
		// 原生的UUID中间是带有-的，所以这里需要将-去掉
		return UUID.randomUUID().toString().replace("-", "");
	}
}
