package com.xunxin.service.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunxin.dao.qa.XunxinAuditInformationRecordMapper;
import com.xunxin.vo.qa.XunxinAuditInformationRecord;
import com.xunxin.vo.qa.XunxinAuditInformationRecordExample;
import com.xunxin.vo.qa.XunxinAuditInformationRecordExample.Criteria;

@Service("xunxinAuditInformationRecordService")
public class XunxinAuditInformationRecordService {
	
	private static final Logger logger = Logger.getLogger(XunxinAuditInformationRecordService.class);

	@Autowired
	private XunxinAuditInformationRecordMapper xunxinAuditInformationRecordMapper;

	public boolean volunteerAdopt(Integer userId, String questionId, Integer type, Integer isAdopt,String content,String auditType) {
		
		XunxinAuditInformationRecord xunxinAuditInformationRecord = new XunxinAuditInformationRecord();
		
		xunxinAuditInformationRecord.setIsAdopt(isAdopt);
		xunxinAuditInformationRecord.setIsDelete("0");
		xunxinAuditInformationRecord.setQustionId(questionId);
		xunxinAuditInformationRecord.setUserId(userId);
		xunxinAuditInformationRecord.setReasonsForFailure(content);
		xunxinAuditInformationRecord.setAuditType(auditType);
		xunxinAuditInformationRecord.setCreateTime(new Date());
		xunxinAuditInformationRecord.setUpdateTime(new Date());
		Integer insert = xunxinAuditInformationRecordMapper.insert(xunxinAuditInformationRecord);
		if(insert>0){
			return true;
		}else{
			return false;
		}
	}

	//审核失败的原因
	public List<String> queryCause(String id) {
		List<String> returnList = new ArrayList<String>();
		XunxinAuditInformationRecordExample example = new XunxinAuditInformationRecordExample();
		Criteria  criteria= example.createCriteria();
		criteria.andQustionIdEqualTo(id);
		criteria.andIsDeleteEqualTo("0");
		criteria.andIsAdoptEqualTo(2);
		List<XunxinAuditInformationRecord> list = xunxinAuditInformationRecordMapper.selectByExample(example);
		for (XunxinAuditInformationRecord xunxinAuditInformationRecord : list) {
			if(xunxinAuditInformationRecord.getReasonsForFailure() != null && xunxinAuditInformationRecord.getReasonsForFailure().length() >0 && !returnList.contains(xunxinAuditInformationRecord.getReasonsForFailure())){
				returnList.add(xunxinAuditInformationRecord.getReasonsForFailure());
			}
		}
		return returnList;
	}

	public void updateDeleteReason(String id) {
		XunxinAuditInformationRecordExample example = new XunxinAuditInformationRecordExample();
		Criteria  criteria= example.createCriteria();
		criteria.andQustionIdEqualTo(id);
		criteria.andIsDeleteEqualTo("0");
		criteria.andIsAdoptNotEqualTo(1);
		XunxinAuditInformationRecord record= new XunxinAuditInformationRecord();
		record.setIsDelete("1");
		xunxinAuditInformationRecordMapper.updateByExampleSelective(record, example);
	}
}
