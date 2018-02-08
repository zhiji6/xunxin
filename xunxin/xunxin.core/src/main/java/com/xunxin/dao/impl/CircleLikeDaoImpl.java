package com.xunxin.dao.impl;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.circle.CircleLikeDao;
import com.xunxin.vo.circle.CircleLike;

public class CircleLikeDaoImpl extends GeneralDaoImpl<CircleLike>  implements CircleLikeDao {

	@Override
	protected Class<CircleLike> getEntityClass() {
		return CircleLike.class;
	}

}