package com.xunxin.dao.impl.web;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.qa.ExcelUploadRecordDao;
import com.xunxin.vo.qa.ExcelUploadRecordVO;

@Repository
public class ExcelUploadRecordDaoImpl extends GeneralDaoImpl<ExcelUploadRecordVO> implements ExcelUploadRecordDao{

	@Override
	protected Class<ExcelUploadRecordVO> getEntityClass() {
		// TODO Auto-generated method stub
		return ExcelUploadRecordVO.class;
	}

}
