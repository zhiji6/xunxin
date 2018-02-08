package com.xunxin.dao.impl.app;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.app.user.UserWelfareDao;
import com.xunxin.vo.user.UserWelfare;

@Repository
public class UserWelfareDaoImpl extends GeneralDaoImpl<UserWelfare> implements UserWelfareDao{

	@Override
	protected Class<UserWelfare> getEntityClass() {
		return UserWelfare.class;
	}

}