package com.xunxin.dao.impl.app;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.square.ExposureCommentThumbRecordDao;
import com.xunxin.vo.square.ExposureCommentThumbRecord;

@Repository
public class ExposureCommentThumbRecordDaoImpl extends GeneralDaoImpl<ExposureCommentThumbRecord> implements ExposureCommentThumbRecordDao{

	@Override
	protected Class<ExposureCommentThumbRecord> getEntityClass() {
		// TODO Auto-generated method stub
		return ExposureCommentThumbRecord.class;
	}

}
