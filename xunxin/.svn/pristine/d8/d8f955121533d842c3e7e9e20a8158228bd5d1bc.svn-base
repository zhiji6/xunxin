package com.xunxin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.xunxin.dao.qa.XunxinAuditInformationRecordMapper;
import com.xunxin.dao.qa.XunxinUserAuditMapper;
import com.xunxin.dao.qa.XunxinUserDeviceTokenMapper;
import com.xunxin.service.app.QASectionService;
import com.xunxin.service.app.qa.QuestionVOManagerService;
import com.xunxin.vo.qa.QuestionVO;
import com.xunxin.vo.qa.XunxinAuditInformationRecord;
import com.xunxin.vo.qa.XunxinUserAudit;
import com.xunxin.vo.qa.XunxinUserDeviceToken;





public class AuditimerTask extends TimerTask {

	private String questionId = null;
	private Integer auditNum = null;
	private Timer timer;
	private Date start;
	private Integer type;
	
	private XunxinAuditInformationRecordMapper xunxinAuditInformationRecordMapper;
	XunxinUserAuditMapper xunxinUserAuditMapper;
	private XunxinUserDeviceTokenMapper xunxinUserDeviceTokenMapper;
	private QASectionService QASectionService;
	QuestionVOManagerService questionVOManagerService;

	  public AuditimerTask(String questionId,Integer auditNum,Timer timer,Date date,Integer type,
			  XunxinAuditInformationRecordMapper xunxinAuditInformationRecordMapper,XunxinUserAuditMapper xunxinUserAuditMapper,
			  XunxinUserDeviceTokenMapper xunxinUserDeviceTokenMapper,QASectionService QASectionService,
			  QuestionVOManagerService questionVOManagerService){
		  this.questionId = questionId;
		  this.auditNum = auditNum;
		  this.timer = timer;
		  this.start = date;
		  this.type = type;
		  this.xunxinAuditInformationRecordMapper = xunxinAuditInformationRecordMapper;
		  this.xunxinUserAuditMapper =xunxinUserAuditMapper;
		  this.xunxinUserDeviceTokenMapper = xunxinUserDeviceTokenMapper;
		  this.QASectionService = QASectionService;
		  this.questionVOManagerService = questionVOManagerService;
	  }
	
	  private static final Logger log = Logger.getLogger(AuditimerTask.class);
    @Override
    public void run() {
    	log.info("infoMsg:--- 审核定时任务开始");
    	Date now = new Date();
    	long cha = now.getTime() - start.getTime(); 
        double result = cha * 1.0 / (1000 * 60 * 60);
        
        if(result<=24){ 
        	List<XunxinAuditInformationRecord> auditRecords = xunxinAuditInformationRecordMapper.findListByQuestionId(questionId);
            
        	if(auditRecords != null && auditRecords.size()<auditNum+1){
        		List userIds =  new ArrayList<Integer>(); 
        		for (XunxinAuditInformationRecord record : auditRecords) {
        			userIds.add(record.getUserId());
				}
        		
        		Map map = new HashMap<String,Object>();
        		map.put("type", type);
        		map.put("auditNum", auditNum-auditRecords.size());
        		map.put("userIds", userIds);
        		List<XunxinUserAudit> users = xunxinUserAuditMapper.findAuditTimerTaskUserLogin(map);
        		
        		for (XunxinUserAudit userEntity : users) {
        			
        			XunxinUserDeviceToken user = xunxinUserDeviceTokenMapper.selectByPrimaryKey(userEntity.getUserId());
        			if(users != null && user.getDeviceToken() != null){
        				QuestionVO questionVO = new QuestionVO();
        				questionVO.setId("adsfsgshsthy");
        				questionVO.setName("审核测试");
    					try {
							/*UpushUtils.sendIOSUnicast(UpushConstants.APP_appkey,UpushConstants.IOS_appMasterSecret,
									
									user.getDeviceToken(), "测试", 0, "测试", "gvf", JacksonUtil.Builder().obj2Json(questionVO));*/
    						log.info("infoMsg:--- 推送审核任务");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    					}
				}
        		
        		
        	}else{
        		if(auditRecords == null){
        			timer.cancel(); 
        		}
        		Integer count = null;
        		for (XunxinAuditInformationRecord xunxinAuditInformationRecord : auditRecords) {
					if(xunxinAuditInformationRecord != null && xunxinAuditInformationRecord.getIsAdopt().equals(1)){
						count++;
					}
				}
        		if(count>2){
    				Query queryupdate = new Query();
    				queryupdate.addCriteria(Criteria.where("isDelete").is(false));
    				queryupdate.addCriteria(Criteria.where("_id").is(questionId));
    				QuestionVO questionVO = questionVOManagerService.findOneById(questionId);
    				
    				Update update = Update.update("status",2);
    				update.update("examineNum", questionVO.getExamineNum()+1);
        			questionVOManagerService.updateFirst(queryupdate, update);
        			System.out.println("sssssssssssss审核通过通过通过通过通过通过通过sssssssssssssssssssssssssssssssssssss");
                    timer.cancel(); 
        		}else{
        			Query queryupdate = new Query();
    				queryupdate.addCriteria(Criteria.where("isDelete").is(false));
    				queryupdate.addCriteria(Criteria.where("_id").is(questionId));
    				Update update = Update.update("status",1);
    				
        			questionVOManagerService.updateFirst(queryupdate, update);
        			System.out.println("sssssssssssss审核不通过sssssssssssssssssssssssssssssssssssss");
                    timer.cancel(); 
        		}
        	}
       }else{ 
            timer.cancel();
       } 

        log.info("infoMsg:--- 审核定时任务结束");
    }

}