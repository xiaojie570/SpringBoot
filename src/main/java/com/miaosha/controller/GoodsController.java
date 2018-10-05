package com.miaosha.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import com.miaosha.domain.MiaoshaUser;
import com.miaosha.redis.GoodsKey;
import com.miaosha.redis.RedisService;
import com.miaosha.service.GoodsService;
import com.miaosha.service.MiaoshaUserService;
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
	
	@Autowired
	ThymeleafViewResolver thymeleafViewResolver; 
	
	@Autowired
	ApplicationContext applicationContext;
	
	@RequestMapping(value="/tolist", produces="text/html")
	@ResponseBody
	public String toList(HttpServletRequest request,HttpServletResponse response,Model model,MiaoshaUser user) {
		model.addAttribute("user", user);
		
		// 页面缓存时间比较短，缓存失效是自动失效的
		// 取缓存
		String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
		if(!StringUtils.isEmpty(html))
			return html;
		
		List<GoodsVo> goodsList = goodsService.listGoodsVo();
		model.addAttribute("goodsList", goodsList);
		//return "goods_list";
		
		// 手动渲染
		SpringWebContext ctx = new SpringWebContext(request, response, request.getServletContext(),request.getLocale(), model.asMap(),applicationContext);
		html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
		
		if(!StringUtils.isEmpty(html)) {
			redisService.set(GoodsKey.getGoodsList, "", html);
		}
		return html;
	}
	
	@RequestMapping(value = "/todetail/{goodsId}", produces="text/html")
	@ResponseBody
	public String toDetail(HttpServletRequest request,HttpServletResponse response,Model model,MiaoshaUser user,
			@PathVariable("goodsId")long goodsId) {
		// snowflake算法来代替自增长id
		model.addAttribute("user", user);
		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		model.addAttribute("goods", goods);
		
		// 取缓存
		String html = redisService.get(GoodsKey.getGoodsDetail, "" + goodsId, String.class);
		if(!StringUtils.isEmpty(html))
			return html;
		
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
		//return "goods_detail";
		
		// 手动渲染
		SpringWebContext ctx = new SpringWebContext(request, response, request.getServletContext(),request.getLocale(), model.asMap(),applicationContext);
		html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
		
		if(!StringUtils.isEmpty(html)) {
			redisService.set(GoodsKey.getGoodsDetail, ""  + goodsId, html);
		}
		return html;
	}
}
