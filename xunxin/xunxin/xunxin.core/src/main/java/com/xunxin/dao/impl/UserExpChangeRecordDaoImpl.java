package com.xunxin.dao.impl;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.vo.account.UserExpChangeRecord;

import com.xunxin.dao.app.user.UserExpChangeRecordDao;

@Repository
public class UserExpChangeRecordDaoImpl extends GeneralDaoImpl<UserExpChangeRecord> implements UserExpChangeRecordDao{

	@Override
	protected Class<UserExpChangeRecord> getEntityClass() {
		// TODO Auto-generated method stub
		return UserExpChangeRecord.class;
	}

}
