package com.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.miaosha.domain.MiaoshaOrder;
import com.miaosha.domain.MiaoshaUser;
import com.miaosha.domain.OrderInfo;
import com.miaosha.result.CodeMsg;
import com.miaosha.service.GoodsService;
import com.miaosha.service.MiaoshaService;
import com.miaosha.service.OrderService;
import com.miaosha.vo.GoodsVo;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	MiaoshaService miaoshaService;
	
	// 开始做秒杀
	@RequestMapping("/do_miaosha")
	public String doMiaosha(Model model, MiaoshaUser user,
			@RequestParam("goodsId")long goodsId) {
		model.addAttribute("user", user);
		if(user == null)
			return "login";
		
		// 判断商品是否有库存
		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		int stock = goods.getStockCount();
		if(stock <= 0) {
			model.addAttribute("errmsg", CodeMsg.MIAOSHA_OVER);
			return "miaoshao_fail";
		}
		
		// 判断是否已经秒杀到了,防止重复秒杀
		MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
		if(order != null) {
			model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA);
			return "miaosha_fail";
		}
		// 减库存，下订单 写入秒杀订单
		OrderInfo orderInfo = miaoshaService.miaosha(user,goods);
		model.addAttribute("orderInfo", orderInfo);
		model.addAttribute("goods", goods);
		return "order_detail";
	}
} 
