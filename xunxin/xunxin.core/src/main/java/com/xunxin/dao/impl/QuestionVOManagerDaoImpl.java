package com.xunxin.dao.impl;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.qa.QuestionVOManagerDao;
import com.xunxin.vo.qa.QuestionVO;

@Repository
public class QuestionVOManagerDaoImpl extends GeneralDaoImpl<QuestionVO> implements QuestionVOManagerDao{

	@Override
	protected Class<QuestionVO> getEntityClass() {
		return QuestionVO.class;
	}

}
