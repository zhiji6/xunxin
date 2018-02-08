package com.xunxin.service.app;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunxin.dao.qa.XunxinAuditInformationRecordMapper;
import com.xunxin.vo.qa.XunxinAuditInformationRecord;

@Service("xunxinAuditInformationRecordService")
public class XunxinAuditInformationRecordService {
	
	private static final Logger logger = Logger.getLogger(XunxinAuditInformationRecordService.class);

	@Autowired
	private XunxinAuditInformationRecordMapper xunxinAuditInformationRecordMapper;

	public boolean volunteerAdopt(Integer userId, String questionId, Integer type, Integer isAdopt,String content) {
		
		XunxinAuditInformationRecord xunxinAuditInformationRecord = new XunxinAuditInformationRecord();
		
		xunxinAuditInformationRecord.setIsAdopt(isAdopt);
		xunxinAuditInformationRecord.setIsDelete("0");
		xunxinAuditInformationRecord.setQustionId(questionId);
		xunxinAuditInformationRecord.setUserId(userId);
		xunxinAuditInformationRecord.setReasonsForFailure(content);
		xunxinAuditInformationRecord.setCreateTime(new Date());
		xunxinAuditInformationRecord.setUpdateTime(new Date());
		Integer insert = xunxinAuditInformationRecordMapper.insert(xunxinAuditInformationRecord);
		if(insert>0){
			return true;
		}else{
			return false;
		}
	}
}
