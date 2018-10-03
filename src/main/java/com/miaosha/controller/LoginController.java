package com.miaosha.controller;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.miaosha.result.Result;
import com.miaosha.service.MiaoshaUserService;
import com.miaosha.vo.LoginVo;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	private static Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private MiaoshaUserService miaoshauserService;
	
	@RequestMapping("/tologin")
	public String toLogin() {
		return "login";
	}
	
	@RequestMapping("/dologin")
	@ResponseBody
	public Result<Boolean> doLogin(@Valid LoginVo lv) {
		log.info(lv.toString());
		// 登录
		miaoshauserService.login(lv);
		return Result.success(true);
	}
}
