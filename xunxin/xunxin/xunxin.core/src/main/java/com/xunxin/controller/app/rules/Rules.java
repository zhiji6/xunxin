package com.xunxin.controller.app.rules;

import java.math.BigDecimal;

import com.xunxin.service.ArecordService;
import com.xunxin.service.app.AppUserService;


public interface Rules {

	
	 public BigDecimal getScore(Integer userId, Integer friendId,ArecordService arecordService,AppUserService appUserService);
}
