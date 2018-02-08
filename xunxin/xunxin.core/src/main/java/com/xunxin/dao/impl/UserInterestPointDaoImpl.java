package com.xunxin.dao.impl;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.app.user.UserInterestPointDao;
import com.xunxin.vo.user.UserInterestPoint;

@Repository
public class UserInterestPointDaoImpl extends GeneralDaoImpl<UserInterestPoint> implements UserInterestPointDao{

	@Override
	protected Class<UserInterestPoint> getEntityClass() {
		return UserInterestPoint.class;
	}

}
