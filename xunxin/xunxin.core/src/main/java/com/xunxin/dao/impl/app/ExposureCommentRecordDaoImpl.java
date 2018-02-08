package com.xunxin.dao.impl.app;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.square.ExposureCommentRecordDao;
import com.xunxin.vo.square.ExposureCommentRecord;

@Repository
public class ExposureCommentRecordDaoImpl extends GeneralDaoImpl<ExposureCommentRecord> implements ExposureCommentRecordDao{

	@Override
	protected Class<ExposureCommentRecord> getEntityClass() {
		return ExposureCommentRecord.class;
	}

}
