package com.xunxin.dao.impl;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.vo.square.TurnplateAwardRecord;

import com.xunxin.dao.square.TurnplateAwardRecordDao;

@Repository
public class TurnplateAwardRecordDaoImpl extends GeneralDaoImpl<TurnplateAwardRecord> implements TurnplateAwardRecordDao{

	@Override
	protected Class<TurnplateAwardRecord> getEntityClass() {
		return TurnplateAwardRecord.class;
	}

}
