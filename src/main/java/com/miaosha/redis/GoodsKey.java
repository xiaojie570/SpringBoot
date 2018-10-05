package com.miaosha.redis;

public class GoodsKey extends BasePrefix{

	public GoodsKey(int expireSecondes,String prefix) {
		super(expireSecondes,prefix);
		// TODO Auto-generated constructor stub
	}

	public static GoodsKey getGoodsList = new GoodsKey(60,"gl");
	public static GoodsKey getGoodsDetail = new GoodsKey(60,"gd");
}
