package com.xunxin.dao.impl;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.vo.sys.XDocumentAPI;

import com.xunxin.dao.sys.XDocumentAPIDao;

@Repository
public class XDocumentAPIDaoImpl extends GeneralDaoImpl<XDocumentAPI> implements XDocumentAPIDao{

	@Override
	protected Class<XDocumentAPI> getEntityClass() {
		return XDocumentAPI.class;
	}

}
