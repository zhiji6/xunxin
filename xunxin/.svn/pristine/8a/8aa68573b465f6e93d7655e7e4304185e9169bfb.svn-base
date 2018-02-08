package com.xunxin.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunxin.controller.app.user.AppUserController;
import com.xunxin.dao.app.user.AppUserDao;
import com.xunxin.dao.app.user.UserDao;
import com.xunxin.vo.user.UserEntity;

@Service
public class UserService{

    @Autowired
    private AppUserDao appUserDao;


	public UserEntity findUserById(int uid) {
		return appUserDao.findById(uid);
	}



	



}
