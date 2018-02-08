package com.xunxin.service.app;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.impl.MessageReadDaoImpl;


@Repository
public class MessageReadService extends MessageReadDaoImpl{

	private static final Logger logger = Logger.getLogger(MessageReadService.class);
	

	public void findMessageReadHert(Integer userId) {
		Query query = new Query();
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("userId").is(userId));
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("tag").is(1));
		Update update = Update.update("isRead", 1);
		updateAllByQuery(query, update);
		
	}

	public void findMessageRead(String id) {
		Query query = new Query();
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("id").is(id));
		Update update = Update.update("isRead", 1);
		updateAllByQuery(query, update);
		
	}
}
