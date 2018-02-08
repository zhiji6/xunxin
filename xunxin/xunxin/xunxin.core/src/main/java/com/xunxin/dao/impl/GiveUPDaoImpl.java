package com.xunxin.dao.impl;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.qa.GiveUpDao;
import com.xunxin.vo.qa.GiveUpVo;

@Repository
public class GiveUPDaoImpl extends GeneralDaoImpl<GiveUpVo>  implements GiveUpDao {

	@Override
	protected Class<GiveUpVo> getEntityClass() {
		return GiveUpVo.class;
	}

}