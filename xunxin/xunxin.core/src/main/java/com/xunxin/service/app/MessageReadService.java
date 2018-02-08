package com.xunxin.service.app;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.impl.MessageReadDaoImpl;
import com.xunxin.vo.im.MessageRead;


@Repository
public class MessageReadService extends MessageReadDaoImpl{

	private static final Logger logger = Logger.getLogger(MessageReadService.class);
	
	//阅读心有灵犀
	public void findMessageReadHert(Integer userId) {
		Query query = new Query();
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("userId").is(userId));
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("tag").is(0));
		Update update = Update.update("isRead", 1);
		updateAllByQuery(query, update);
		
	}
	//阅读官方消息
	public void findMessageRead(String id) {
		Query query = new Query();
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("id").is(id));
		Update update = Update.update("isRead", 1);
		updateAllByQuery(query, update);
		
	}
	//消息列表
	public  List<MessageRead> findMessageByUserId(Integer userId, Integer tag) {
		Query query = new Query();
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("userId").is(userId));
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("tag").is(tag));
		query.with(new Sort(new Order(Direction.DESC, "createTime")));
		List<MessageRead> list = find(query);
		//阅读举报消息
		if(tag!=null && tag.equals(2)){
			readReport(userId);
		}
		return list;
	}
	//阅读举报消息
	private void readReport(Integer userId) {
		Query query = new Query();
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("userId").is(userId));
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("type").is(2));
		Update update = Update.update("isRead", 1);
		updateAllByQuery(query, update);
		
	}
	//删除消息
	public void deleteMessage(Integer userId, Integer tag) {
		Query query = new Query();
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("userId").is(userId));
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("tag").is(tag));
		Update update = Update.update("isRead", 1);
		updateAllByQuery(query, update);
	}
}
