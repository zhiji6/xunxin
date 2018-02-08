package com.xunxin.dao.impl;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.circle.CircleCommentDao;
import com.xunxin.vo.circle.CircleComment;
@Repository
public class CircleCommentDaoImpl extends GeneralDaoImpl<CircleComment>  implements CircleCommentDao{

	@Override
	protected Class<CircleComment> getEntityClass() {
		return CircleComment.class;
	}

}