package com.xunxin.dao.impl.app;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.square.XunXinExposureDao;
import com.xunxin.vo.square.XunXinExposure;

@Repository
public class ExposureDaoImpl extends GeneralDaoImpl<XunXinExposure> implements XunXinExposureDao{

	@Override
	protected Class<XunXinExposure> getEntityClass() {
		// TODO Auto-generated method stub
		return XunXinExposure.class;
	}

}
