package com.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miaosha.dao.MiaoshaUserDao;
import com.miaosha.domain.MiaoshaUser;
import com.miaosha.result.CodeMsg;
import com.miaosha.util.MD5Util;
import com.miaosha.vo.LoginVo;

@Service
public class MiaoshaUserService {

	@Autowired
	private MiaoshaUserDao miaoshaDao;
	
	public MiaoshaUser getById(int id) {
		return miaoshaDao.getById(id);
	}

	public CodeMsg login(LoginVo lv) {
		if(lv == null)
			return CodeMsg.SERVER_ERROR;
		String mobile = lv.getMobile();
		String formPass = lv.getPassword();
		// 判断手机号是否存在
		MiaoshaUser user = getById(Integer.parseInt(mobile));
		if(user == null)
			return CodeMsg.MOBILE_NOT_EXIST;
		
		// 验证密码
		String dbPass = user.getPassword();
		String saltDB = user.getSalt();
		String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
		
		if(!calcPass.equals(dbPass))
			return CodeMsg.PASSWORD_ERROR;
		
		
		return CodeMsg.SUCCESS;
	}
}
