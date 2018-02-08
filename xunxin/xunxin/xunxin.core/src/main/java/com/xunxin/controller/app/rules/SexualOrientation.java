package com.xunxin.controller.app.rules;

import java.math.BigDecimal;

import com.xunxin.service.ArecordService;
import com.xunxin.service.app.AppUserService;
import com.xunxin.vo.user.UserEntity;

public class SexualOrientation implements Rules{

	@Override
	public BigDecimal getScore(Integer userId, Integer friendId,ArecordService arecordService,AppUserService appUserService) {
		
		UserEntity user = appUserService.findById(userId);
		UserEntity friend = appUserService.findById(friendId);
		if(user != null && user.getGender() != null && user.getSexualOrientation() != null && friend != null && friend.getGender() != null && friend.getSexualOrientation()!= null){
			if(user.getGender().equals(friend.getSexualOrientation()) && friend.getGender().equals(user.getSexualOrientation())){
				return BigDecimal.valueOf(1); 
			}
		}
		return null;
	}

}
