package com.miaosha.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.miaosha.util.ValidatorUtil;



public class IsMobileValidator implements ConstraintValidator<IsMobile,String>{

	private boolean required = false;
	
	// 初始化方法，拿到注解
	public void initialize(IsMobile constraintAnnotation) {
		
		required = constraintAnnotation.required();
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(required) {
			return ValidatorUtil.isMobile(value);
		} else {
			if(StringUtils.isEmpty(value)) {
				return true;
			}else {
				return ValidatorUtil.isMobile(value);
			}
		}
		
	}
	
}
