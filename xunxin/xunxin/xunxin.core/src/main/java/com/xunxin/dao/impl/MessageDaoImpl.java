package com.xunxin.dao.impl;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.sys.MessageDao;
import com.xunxin.vo.sys.Message;

@Repository
public class MessageDaoImpl extends GeneralDaoImpl<Message> implements MessageDao{

	@Override
	protected Class<Message> getEntityClass() {
		return Message.class;
	}

	/**
	 * 查询注册时候的验证码
	 */
	public int findLastOneByPhone(String phone) {
		Query query = new Query();
		query.addCriteria(Criteria.where("phone").is(phone));
		query.addCriteria(Criteria.where("type").is(0));
		query.with(new Sort(new Order(Direction.DESC, "createTime")));
		query.limit(1);
		Message mes = getMongoTemplate().findOne(query, this.getEntityClass());
		return mes.getCode();
	}

	/**
	 * 查询注册时候的验证码
	 */
	public int findforgetLastOneByPhone(String phone) {
		Query query = new Query();
		query.addCriteria(Criteria.where("phone").is(phone));
		query.addCriteria(Criteria.where("type").is(1));
		query.with(new Sort(new Order(Direction.DESC, "createTime")));
		query.limit(1);
		Message mes = getMongoTemplate().findOne(query, this.getEntityClass());
		return mes.getCode();
	}
	
	
}
