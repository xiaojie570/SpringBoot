package com.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miaosha.dao.UserDao;
import com.miaosha.domain.User;

@Service
public class UserService {
	@Autowired
	UserDao userDao;
	
	public User getById(int id) {
		return userDao.getById(id);
	}
	@Transactional
	public boolean tx() {
		User u3 = new User();
		u3.setId(3);
		u3.setName("333");
		userDao.insert(u3);
		
		
		return true;
	}
}
