package com.xunxin.dao.impl;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.square.ShakeItOffRecordDao;
import com.xunxin.vo.square.ShakeItOffRecord;

@Repository
public class ShakeItOffRecordDaoImpl extends GeneralDaoImpl<ShakeItOffRecord> implements ShakeItOffRecordDao{

	@Override
	protected Class<ShakeItOffRecord> getEntityClass() {
		return ShakeItOffRecord.class;
	}

}
