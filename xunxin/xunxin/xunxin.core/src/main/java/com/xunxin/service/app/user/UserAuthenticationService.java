package com.xunxin.service.app.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xunxin.dao.app.user.UserAuthenticationDao;
import com.xunxin.util.page.PageInfo;
import com.xunxin.util.page.PaginationContext;
import com.xunxin.vo.sys.PageData;
import com.xunxin.vo.user.UserAuthentication;

@Service("userAuthenticationService")
public class UserAuthenticationService {

	@Autowired
	private UserAuthenticationDao userAuthenticationDao;
	
	public void save(UserAuthentication auth) {
		userAuthenticationDao.save(auth);
	}

	public boolean isAuthentication(PageData pd) {
		UserAuthentication auth = userAuthenticationDao.isAuthentication(pd);
		if(null != auth) {
			if(auth.getAuthState() == 1) {
				return true;
			}else {
				return false;
			}
		}
		return false;
	}

	public List<UserAuthentication> getAll(int userId) {
		return userAuthenticationDao.getAll(userId);
	}

	public PageInfo<UserAuthentication> findPageList(UserAuthentication userAuthentication) {
        PageHelper.startPage(PaginationContext.getPageNum(), PaginationContext.getPageSize());
        List<UserAuthentication> list = userAuthenticationDao.findPageList(userAuthentication);
        return new PageInfo<UserAuthentication>(list);
	}

	public UserAuthentication findOneById(Integer id) {
		return userAuthenticationDao.findOneById(id);
	}

	public void authenticationManagerExamine(UserAuthentication userAuthentication) {
		userAuthenticationDao.authenticationManagerExamine(userAuthentication);
		
	}

	public UserAuthentication model(int userId, String type) {
		return userAuthenticationDao.model(userId,type);
	}

	
	
	
	
}
