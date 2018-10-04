package com.miaosha.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.miaosha.domain.MiaoshaUser;
import com.miaosha.redis.RedisService;
import com.miaosha.service.GoodsService;
import com.miaosha.service.MiaoshaUserService;
import com.miaosha.service.UserService;
import com.miaosha.vo.GoodsVo;

@Controller
@RequestMapping("/goods")
public class GoodsController {
	@Autowired
	RedisService redisService;
	
	@Autowired
	MiaoshaUserService userService;
	
	@Autowired 
	GoodsService goodsService;
	
	@RequestMapping("/tolist")
	public String toList(HttpServletResponse response,Model model,MiaoshaUser user) {
		List<GoodsVo> goodsList = goodsService.listGoodsVo();
		model.addAttribute("goodsList", goodsList);
		return "goods_list";
	}
	
	@RequestMapping("/todetail/{goodsId}")
	public String toDetail(HttpServletResponse response,Model model,MiaoshaUser user,
			@PathVariable("goodsId")long goodsId) {
		// snowflake算法来代替自增长id
		model.addAttribute("user", user);
		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		model.addAttribute("goods", goods);
		
		Long startAt = goods.getStartDate().getTime();
		Long endAt = goods.getEndDate().getTime();
		Long now = System.currentTimeMillis();
		
		int miaoshaStatus = 0;
		int remainSeconds = 0;
		if(now < startAt) { // 秒杀还没开始，倒计时
			miaoshaStatus = 0;
			remainSeconds = (int)((startAt - now )/1000);
		}else if(now > endAt) { // 秒杀已经结束
			miaoshaStatus = 2;
			remainSeconds = -1;
		}else { // 秒杀进行时
			miaoshaStatus = 1;
			remainSeconds = 0;
		}
		
		model.addAttribute("miaoshaStatus", miaoshaStatus);
		model.addAttribute("remainSeconds", remainSeconds);
		return "goods_detail";
	}
}
