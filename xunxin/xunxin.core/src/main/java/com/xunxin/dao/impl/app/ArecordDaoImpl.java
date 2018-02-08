package com.xunxin.dao.impl.app;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.qa.ArecordDao;
import com.xunxin.vo.qa.ArecordVO;

@Repository
public class ArecordDaoImpl extends GeneralDaoImpl<ArecordVO>  implements ArecordDao {

	@Override
	protected Class<ArecordVO> getEntityClass() {
		return ArecordVO.class;
	}

}