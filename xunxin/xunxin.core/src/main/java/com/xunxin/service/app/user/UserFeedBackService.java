package com.xunxin.service.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunxin.dao.app.user.UserFeedBackDao;
import com.xunxin.vo.user.UserFeedBackVO;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月28日 -- 上午10:30:58
 * @Version 1.0
 * @Description
 */
@Service("userFeedBackService")
public class UserFeedBackService {

	@Autowired
	private UserFeedBackDao userFeedBackDao;
	
	public void save(UserFeedBackVO feed) {
		userFeedBackDao.save(feed);
	}

    public void removeAll(int userId) {
        userFeedBackDao.removeAll(userId);
    }

	
	
	
}
