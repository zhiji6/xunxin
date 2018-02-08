package com.xunxin.dao.impl;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.qa.QuestionDao;
import com.xunxin.vo.qa.QuestionVO;

@Repository
public class QuestionDaoImpl extends GeneralDaoImpl<QuestionVO> implements QuestionDao{

	@Override
	protected Class<QuestionVO> getEntityClass() {
		return QuestionVO.class;
	}

}
