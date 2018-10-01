package com.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.miaosha.domain.User;
import com.miaosha.redis.RedisService;
import com.miaosha.redis.UserKey;
import com.miaosha.result.CodeMsg;
import com.miaosha.result.Result;

@Controller
@RequestMapping("/demo")
public class DemoController {
	
	@Autowired
	RedisService redisService;
	
	@RequestMapping("/hello")
	@ResponseBody
	public Result<String> hello() {
		return Result.success("hello fj");
	}
	
	@RequestMapping("/helloError")
	@ResponseBody
	public Result<String> sad() {
		return Result.error(CodeMsg.SERVER_ERROR);
	}
	
	@RequestMapping("/thymeleaf")
	@ResponseBody
	public String thymeleaf(Model model) {
		model.addAttribute("name","fj");
		return "hello";
	}
	
	@RequestMapping("/redis/get")
	@ResponseBody
	public Result<User> redisGet() {
		User user = redisService.get(UserKey.getById,""+1,User.class);
		
		return Result.success(user);
	}
	
	@RequestMapping("/redis/set")
	@ResponseBody
	public Result<Boolean> redisSet() {
		User user = new User();
		user.setId(1);
		user.setName("1111");
		boolean ret = redisService.set(UserKey.getById,""+1,user);
		//String r = redisService.get("key5",String.class);
		return Result.success(ret);
	}
}
