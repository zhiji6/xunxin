package com.xunxin.dao.impl;

import org.mongodb.framework.dao.GeneralDaoImpl;

import org.springframework.stereotype.Repository;

import com.xunxin.dao.qa.QACollectionRecordDao;
import com.xunxin.vo.qa.QACollectionRecord;

@Repository
public class QACollectionRecordDaoImpl extends GeneralDaoImpl<QACollectionRecord> implements QACollectionRecordDao{

	@Override
	protected Class<QACollectionRecord> getEntityClass() {
		return QACollectionRecord.class;
	}

}
