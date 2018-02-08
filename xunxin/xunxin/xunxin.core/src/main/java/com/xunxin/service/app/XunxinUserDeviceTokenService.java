package com.xunxin.service.app;


import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunxin.dao.qa.XunxinUserDeviceTokenMapper;
import com.xunxin.vo.qa.XunxinUserDeviceToken;

@Service("xunxinUserDeviceTokenService")
public class XunxinUserDeviceTokenService {

	private static final Logger logger = Logger.getLogger(XunxinUserDeviceTokenService.class);
	
	@Autowired
	private XunxinUserDeviceTokenMapper xunxinUserDeviceTokenMapper;

	public int save(Integer userId, String deviceToken, String type) {
		XunxinUserDeviceToken device = new XunxinUserDeviceToken();
		int insert = 0;
		XunxinUserDeviceToken userDeviceToken = xunxinUserDeviceTokenMapper.selectByUserId(userId);
		if(userDeviceToken != null){
			userDeviceToken.setUserId(userId);
			userDeviceToken.setDeviceToken(deviceToken);
			userDeviceToken.setType(type);
			userDeviceToken.setIsDelete("0");
			userDeviceToken.setUpdateTime(new Date());
			insert = xunxinUserDeviceTokenMapper.updateByPrimaryKeySelective(userDeviceToken);
		}else{
			device.setDeviceToken(deviceToken);
			device.setUserId(userId);
			device.setType(type);
			device.setCreateTime(new Date());
			device.setIsDelete("0");
			device.setUpdateTime(new Date());
			insert = xunxinUserDeviceTokenMapper.insert(device);
		}
		return insert;
	}
}
