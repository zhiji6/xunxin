package com.xunxin.controller.app.message;

import com.xunxin.dao.qa.XunxinUserDeviceTokenMapper;
import com.xunxin.service.app.AppUserService;
import com.xunxin.service.app.SysDictService;

public class Context {
	   private Message message;

	   public Context(Message message){
	      this.message = message;
	   }

	   public void executeStrategy(Integer userId, Integer friendId, String dynamicId,Integer type,SysDictService sysDictService,XunxinUserDeviceTokenMapper xunxinUserDeviceTokenMapper,AppUserService appUserService){
	      message.PushMessage(userId, friendId,  dynamicId, type,sysDictService,xunxinUserDeviceTokenMapper,appUserService);
	   }
	}