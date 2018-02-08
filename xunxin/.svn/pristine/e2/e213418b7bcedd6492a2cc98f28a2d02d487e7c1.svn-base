package com.xunxin.service.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunxin.dao.app.user.UserSelfPortraitDao;
import com.xunxin.vo.user.UserSelfPortraitVO;

@Service("userSelfPortraitService")
public class UserSelfPortraitService {

	@Autowired
	private UserSelfPortraitDao userSelfPortraitDao;
	
	public UserSelfPortraitVO findById(int userId) {
		return userSelfPortraitDao.findById(userId);
	}

}
