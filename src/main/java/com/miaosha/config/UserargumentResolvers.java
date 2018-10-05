package com.miaosha.config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import org.apache.commons.lang3.StringUtils;

import com.miaosha.domain.MiaoshaUser;
import com.miaosha.service.MiaoshaUserService;

@Service
public class UserargumentResolvers implements HandlerMethodArgumentResolver{

	@Autowired
	MiaoshaUserService userService;
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> clazz = parameter.getParameterType();
		
		return clazz == MiaoshaUser.class;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
		
		
		String paramToken = request.getParameter(MiaoshaUserService.COOK1_NAME_TOKEN);
		String cookieToken = getCookieValue(request,MiaoshaUserService.COOK1_NAME_TOKEN);
		if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
			return "login";
		}
		String token = StringUtils.isEmpty(paramToken) ?cookieToken:paramToken;
		MiaoshaUser user = userService.getByToken(response,token);
		return user;
	}

	private String getCookieValue(HttpServletRequest request, String cook1NameToken) {
		Cookie[] cookies = request.getCookies();
		if(cookies == null || cookies.length <= 0)
			return null;
		for(Cookie cookie: cookies) {
			if(cookie.getName().equals(cook1NameToken)) {
				return cookie.getValue();
			}
		}
		return null;
	}

}
