package com.xunxin.service.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

    public void updatePortrait(int userId, String extraInfo) {
        userSelfPortraitDao.updatePortrait(userId,extraInfo);
    }

    public void save(UserSelfPortraitVO vo) {
        userSelfPortraitDao.save(vo);
    }

    public void removeAll(int userId) {
        userSelfPortraitDao.removeAll(userId);
    }



}
