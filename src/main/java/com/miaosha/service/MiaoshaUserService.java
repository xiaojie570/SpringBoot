package com.miaosha.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miaosha.dao.MiaoshaUserDao;
import com.miaosha.domain.MiaoshaUser;
import com.miaosha.exception.GlobalException;
import com.miaosha.redis.KeyPrefix;
import com.miaosha.redis.MiaoshaUserKey;
import com.miaosha.redis.RedisService;
import com.miaosha.result.CodeMsg;
import com.miaosha.util.MD5Util;
import com.miaosha.util.UUIDUtil;
import com.miaosha.vo.LoginVo;

@Service
public class MiaoshaUserService {

	public static final String COOK1_NAME_TOKEN = "token";
	@Autowired
	private MiaoshaUserDao miaoshaDao;
	
	@Autowired
	RedisService redisService;
	
	public MiaoshaUser getById(int id) {
		return miaoshaDao.getById(id);
	}

	
	public MiaoshaUser getByToken(HttpServletResponse response,String token) {
		if(StringUtils.isEmpty(token))
			return null;
		
		// 延长有效期
		// 重新将缓存中的值取出来，然后新生成一个cookie写出来就ok了
		MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
		if(user != null) {
			addCookie(response,token,user);
		}
		return user;
	}
	
	public Boolean login(HttpServletResponse response, LoginVo lv) {
		if(lv == null) {
			throw new GlobalException(CodeMsg.SERVER_ERROR);
			
		}
		String mobile = lv.getMobile();
		String formPass = lv.getPassword();
		// 判断手机号是否存在
		MiaoshaUser user = getById(Integer.parseInt(mobile));
		if(user == null)
			throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);;
		
		// 验证密码
		String dbPass = user.getPassword();
		String saltDB = user.getSalt();
		String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
		if(!calcPass.equals(dbPass))
			throw new GlobalException(CodeMsg.PASSWORD_ERROR);;
			
		// 生成Cookie
		String token = UUIDUtil.uuid();
		addCookie(response, token, user);
		return true;
	}

	private void addCookie(HttpServletResponse response,String token, MiaoshaUser user) {
		//生成Cookie
		
		// 标志token是哪个用户的
		redisService.set(MiaoshaUserKey.token, token, user);
		Cookie cookie = new Cookie(COOK1_NAME_TOKEN,token);
				
		// 过期时间
		cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	

}
