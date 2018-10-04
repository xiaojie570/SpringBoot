package com.miaosha.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
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
	
	@RequestMapping("/todetail")
	public String toDetail(HttpServletResponse response,Model model,@CookieValue(value=MiaoshaUserService.COOK1_NAME_TOKEN,required=false) String cookieToken
		,@RequestParam(value = MiaoshaUserService.COOK1_NAME_TOKEN,required=false)String paramToken	) {

		return "goods_list";
	}
}
