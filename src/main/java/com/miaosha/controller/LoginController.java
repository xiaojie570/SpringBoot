package com.miaosha.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.miaosha.result.CodeMsg;
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
	public Result<Boolean> doLogin(LoginVo lv) {
		log.info(lv.toString());
		// 参数校验
		String passInput = lv.getPassword();
		String mobile = lv.getMobile();
		if(StringUtils.isEmpty(passInput))
			return Result.error(CodeMsg.PASSWORD_EMPTY);
		
		if(StringUtils.isEmpty(mobile))
			return Result.error(CodeMsg.MOBILE_EMPTY);
		
		// 登录
		CodeMsg cm = miaoshauserService.login(lv);
		if(cm.getCode() == 0)
			return Result.success(true);
		else 
			return Result.error(cm);	
	}
}
