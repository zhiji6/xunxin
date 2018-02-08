package com.xunxin.dao.impl;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.qa.XTagLibraryDao;
import com.xunxin.vo.qa.XTagLibrary;

@Repository
public class XTagLibraryDaoImpl extends GeneralDaoImpl<XTagLibrary> implements XTagLibraryDao{

	@Override
	protected Class<XTagLibrary> getEntityClass() {
		return XTagLibrary.class;
	}

}
