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
import com.xunxin.controller.app.message.CircleCommentMessage;
import com.xunxin.controller.app.message.Context;
import com.xunxin.controller.app.message.LeaveMessage;
import com.xunxin.dao.impl.CircleCommentDaoImpl;
import com.xunxin.service.app.CommentService;
import com.xunxin.service.app.user.MongoDBUtil;
import com.xunxin.vo.circle.CircleComment;
@Repository
public class CircleCommentService extends CircleCommentDaoImpl{

	private static final Logger logger = Logger.getLogger(CircleCommentService.class);
	
	
	@Autowired
	private CommentService commentService;

	public void saveCircleComment(Integer userId, String empathyCircleId, String content, Integer replyUserId) {
		
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
		CircleComment findOneByQuery = findOneByQuery(query);
		Context context = new Context(new CircleCommentMessage());        
	     context.executeStrategy(findOneByQuery.getUserId(), userId, empathyCircleId, null,null,null,null);
	}

	public void deleteCircleComment(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("id").is(id));
		Update update = Update.update("isDelete", true);
		updateFirst(query, update);
	}

	public boolean isFrequency(Integer userId) {
//		Query query = new Query();
//		query.addCriteria(Criteria.where("userId").is(userId));
	    Calendar beforeTime = Calendar.getInstance();
	    beforeTime.add(Calendar.SECOND, -5);
	    Date beforeD = beforeTime.getTime();
	    MongoDatabase mdb=MongoDBUtil.getDatabase();
		MongoCollection<Document> document =mdb.getCollection("circleComment");
		Document key =new Document();
		key.append("_id", 1);
		key.append("createTime", 1);
		BasicDBObject query = new BasicDBObject();
		query.put("createTime", new BasicDBObject("$gte", beforeD));
		query.put("userId", userId);
		FindIterable<Document> cursors = document.find(query);
		Integer listComment = commentService.isFrequency(userId);
		int count = 0;
		for (Document  cursor : cursors) {
			 count++;
			}
		if(count+ listComment> 5){
			return true;
		}
		return false;
	}



}
