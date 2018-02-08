package com.xunxin.dao.impl;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.qa.QAImageUrlRecordDao;
import com.xunxin.vo.qa.QAImageUrlRecord;

@Repository
public class QAImageUrlRecordDaoImpl extends GeneralDaoImpl<QAImageUrlRecord> implements QAImageUrlRecordDao{

	@Override
	protected Class<QAImageUrlRecord> getEntityClass() {
		return QAImageUrlRecord.class;
	}

}
