package com.xunxin.dao.impl;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.qa.UserAccessLinkedRecordDao;
import com.xunxin.vo.qa.UserAccessLinkedRecordVO;

@Repository
public class UserAccessLinkedRecordDaoImpl extends GeneralDaoImpl<UserAccessLinkedRecordVO> implements UserAccessLinkedRecordDao{

	@Override
	protected Class<UserAccessLinkedRecordVO> getEntityClass() {
		return UserAccessLinkedRecordVO.class;
	}

}
