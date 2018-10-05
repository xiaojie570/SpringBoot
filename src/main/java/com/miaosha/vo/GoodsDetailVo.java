package com.miaosha.vo;

import com.miaosha.domain.MiaoshaUser;

public class GoodsDetailVo {
	private int miaoshaStatus = 0;
	private int remainSeconds = 0;
	private MiaoshaUser miaoshaUser;
	private GoodsVo goodsVo;

	public int getMiaoshaStatus() {
		return miaoshaStatus;
	}

	public void setMiaoshaStatus(int miaoshaStatus) {
		this.miaoshaStatus = miaoshaStatus;
	}

	public int getRemainSeconds() {
		return remainSeconds;
	}

	public void setRemainSeconds(int remainSeconds) {
		this.remainSeconds = remainSeconds;
	}

	public GoodsVo getGoodsVo() {
		return goodsVo;
	}

	public void setGoodsVo(GoodsVo goodsVo) {
		this.goodsVo = goodsVo;
	}

	public MiaoshaUser getMiaoshaUser() {
		return miaoshaUser;
	}

	public void setMiaoshaUser(MiaoshaUser miaoshaUser) {
		this.miaoshaUser = miaoshaUser;
	}
	
	
	
	
}
