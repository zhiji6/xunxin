package com.xunxin.dao.impl;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.square.ShakeOffAnswerRecordDao;
import com.xunxin.vo.square.ShakeOffAnswerRecord;


@Repository
public class ShakeOffAnswerRecordDaoImpl extends GeneralDaoImpl<ShakeOffAnswerRecord> implements ShakeOffAnswerRecordDao{

	@Override
	protected Class<ShakeOffAnswerRecord> getEntityClass() {
		return ShakeOffAnswerRecord.class;
	}

}
