package com.xunxin.dao.impl;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.im.NearLocationDao;
import com.xunxin.vo.im.NearLocation;
@Repository
public class NearLocationDaoImpl extends GeneralDaoImpl<NearLocation>  implements NearLocationDao{

	@Override
	protected Class<NearLocation> getEntityClass() {
		return NearLocation.class;
	}
}
