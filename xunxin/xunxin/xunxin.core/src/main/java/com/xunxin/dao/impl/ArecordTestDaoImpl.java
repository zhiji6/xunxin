package com.xunxin.dao.impl;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.qa.ArecordTestDao;
import com.xunxin.vo.qa.ArecordTest;

@Repository
public class ArecordTestDaoImpl extends GeneralDaoImpl<ArecordTest>  implements ArecordTestDao {

	@Override
	protected Class<ArecordTest> getEntityClass() {
		return ArecordTest.class;
	}

}
