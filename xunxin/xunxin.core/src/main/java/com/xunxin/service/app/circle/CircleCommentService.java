package com.xunxin.service.app.circle;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.xunxin.constants.DynamicConstants;
import com.xunxin.controller.app.message.CircleCommentMessage;
import com.xunxin.controller.app.message.Context;
import com.xunxin.controller.app.message.LeaveMessage;
import com.xunxin.controller.app.message.MessTypeFactory;
import com.xunxin.dao.impl.CircleCommentDaoImpl;
import com.xunxin.service.app.AppUserService;
import com.xunxin.service.app.CommentService;
import com.xunxin.service.app.EmpathyCircleService;
import com.xunxin.service.app.IMNewsService;
import com.xunxin.service.app.user.DynamicThumbRecordService;
import com.xunxin.service.app.user.MongoDBUtil;
import com.xunxin.vo.circle.CircleComment;
import com.xunxin.vo.circle.CircleCommentRecord;
import com.xunxin.vo.circle.EmpathyCircle;
@Repository
public class CircleCommentService extends CircleCommentDaoImpl{

	private static final Logger logger = Logger.getLogger(CircleCommentService.class);
	
	
	@Autowired
	private CommentService commentService;
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private EmpathyCircleService empathyCircleService;
	@Autowired
	private CircleCommentRecordService circleCommentRecordService;
	@Autowired
	private IMNewsService iMNewsService;
	//保存共情圈评论
	public CircleComment saveCircleComment(Integer userId, String empathyCircleId, String content, Integer replyUserId) {
		
		CircleComment circle = new CircleComment();
		circle.setContent(content);
		circle.setUserId(userId);
		circle.setReplyUserId(replyUserId);
		circle.setCreateTime(new Date());
		circle.setUpdateTime(new Date());
		circle.setIsDelete(false);
		circle.setEmpathyCircleId(empathyCircleId);
		insert(circle);
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("id").is(empathyCircleId));
		EmpathyCircle findOneByQuery = empathyCircleService.findOneByQuery(query);
		
		if(circle.getUserId() != null){
			String userName = appUserService.findUserNameByUserId(circle.getUserId());
			circle.setNickName(userName);
		}
		 if(circle.getReplyUserId()!= null){
			String replyName = appUserService.findUserNameByUserId(circle.getReplyUserId());
			circle.setReployName(replyName);
		 }
		 //评论记录
		 CircleCommentRecord record = new CircleCommentRecord();
		 	record.setCircleCommentId(circle.getId());
		 	record.setEmpathyCircleId(empathyCircleId);
		 	record.setCreateTime(new Date());
		 	record.setIsDelete(false);
		 	if(replyUserId != null ){
		 		record.setUserId(replyUserId);
		 		iMNewsService.userBehaviorPushMessage(MessTypeFactory.CIRCLE_NUM,replyUserId,userId,findOneByQuery.getId());
		 	}else{
		 		if(findOneByQuery != null && findOneByQuery.getUserId() != null){
		 			record.setUserId(findOneByQuery.getUserId());
					logger.info("infoMsg:--- 推送开始");
					logger.info("infoMsg:--- "+findOneByQuery.getUserId());
					iMNewsService.userBehaviorPushMessage(MessTypeFactory.CIRCLE_NUM,findOneByQuery.getUserId(),userId,findOneByQuery.getId());
				}
		 	}
		 circleCommentRecordService.insert(record);
		 String name = null;
		 if(findOneByQuery!= null && findOneByQuery.getUserId() != null){
			 name = appUserService.findUserNameByUserId(findOneByQuery.getUserId());
		 }
		return circle;
	}

	public void deleteCircleComment(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("id").is(id));
		Update update = Update.update("isDelete", true);
		updateFirst(query, update);
	}
	//是否高频
	public boolean isFrequency(Integer userId) {
	    Calendar beforeTime = Calendar.getInstance();
	    beforeTime.add(Calendar.SECOND, -5);
	    Date beforeD = beforeTime.getTime();
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("userId").is(userId));
		query.addCriteria(Criteria.where("createTime").gte(beforeD));
		List<CircleComment> find = find(query);
		Integer listComment = commentService.isFrequency(userId);
		return find.size()+listComment > 5? true : false;
	}

	public void delete(String id) {
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(id));
			Update update = Update.update("isDelete",true);
			updateAllByQuery(query,update);
	}

}
