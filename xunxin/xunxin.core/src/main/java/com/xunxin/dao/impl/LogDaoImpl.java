package com.xunxin.dao.impl;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.sys.LogDao;
import com.xunxin.vo.sys.Log;

@Repository
public class LogDaoImpl extends GeneralDaoImpl<Log>  implements LogDao {

	@Override
	protected Class<Log> getEntityClass() {
		return Log.class;
	}

}
