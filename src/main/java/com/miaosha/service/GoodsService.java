package com.miaosha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miaosha.dao.GoodsDao;
import com.miaosha.vo.GoodsVo;

@Service
public class GoodsService {

	@Autowired
	GoodsDao goodsDao;
	
	public List<GoodsVo> listGoodsVo() {
		return goodsDao.listGoodsVo();
	}
}
