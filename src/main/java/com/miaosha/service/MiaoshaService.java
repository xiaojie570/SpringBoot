package com.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miaosha.domain.Goods;
import com.miaosha.domain.MiaoshaUser;
import com.miaosha.domain.OrderInfo;
import com.miaosha.vo.GoodsVo;

@Service
public class MiaoshaService {
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	OrderService orderService;
	
	@Transactional
	public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
		// 减少库存 下订单 写入秒杀订单
		goodsService.reduceStock(goods);
		
		// 生成订单
		return orderService.createOrder(user,goods);
	}

}
