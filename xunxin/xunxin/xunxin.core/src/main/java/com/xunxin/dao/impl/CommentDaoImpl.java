package com.xunxin.dao.impl;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.qa.CommentDao;
import com.xunxin.vo.qa.CommentVO;

@Repository
public class CommentDaoImpl extends GeneralDaoImpl<CommentVO>  implements CommentDao {

	@Override
	protected Class<CommentVO> getEntityClass() {
		return CommentVO.class;
	}

}