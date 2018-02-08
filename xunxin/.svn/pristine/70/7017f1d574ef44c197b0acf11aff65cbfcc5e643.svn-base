package com.xunxin.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xunxin.dao.pay.ThirdPayDao;
import com.xunxin.vo.pay.ThirdPayBean;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年10月8日 -- 下午4:30:11
 * @Version 1.0
 * @Description
 */
@Service("thirdPayService")
public class ThirdPayService {

	@Resource
	private ThirdPayDao thirdPayDao; 
	
	public ThirdPayBean findByPayId(int bank_id) {
		return thirdPayDao.findByPayId(bank_id);
	}

}
