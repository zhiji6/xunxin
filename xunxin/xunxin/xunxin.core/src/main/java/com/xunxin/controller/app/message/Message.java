package com.xunxin.controller.app.message;

import com.xunxin.dao.qa.XunxinUserDeviceTokenMapper;
import com.xunxin.service.app.AppUserService;
import com.xunxin.service.app.SysDictService;

public interface Message {
	   public void PushMessage(Integer userId,Integer friendId,String dynamicId,Integer type,SysDictService sysDictService,XunxinUserDeviceTokenMapper xunxinUserDeviceTokenMapper,AppUserService appUserService);
	}