package com.xunxin.dao.impl.app;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.qa.AnswerDao;
import com.xunxin.vo.qa.AnswerVO;

@Repository
public class AnswerDaoImpl extends GeneralDaoImpl<AnswerVO> implements AnswerDao{

	@Override
	protected Class<AnswerVO> getEntityClass() {
		return AnswerVO.class;
	}

}
