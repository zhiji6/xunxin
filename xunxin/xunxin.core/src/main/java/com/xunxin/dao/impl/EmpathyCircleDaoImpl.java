package com.xunxin.dao.impl;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.circle.EmpathyCircleDao;
import com.xunxin.vo.circle.EmpathyCircle;
@Repository
public class EmpathyCircleDaoImpl extends GeneralDaoImpl<EmpathyCircle>  implements EmpathyCircleDao{

	@Override
	protected Class<EmpathyCircle> getEntityClass() {
		return EmpathyCircle.class;
	}

}
