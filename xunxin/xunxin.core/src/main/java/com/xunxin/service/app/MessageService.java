package com.xunxin.service.app;

import java.util.List;

import org.mongodb.framework.service.GeneralServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.impl.MessageDaoImpl;
import com.xunxin.dao.sys.MessageDao;
import com.xunxin.vo.sys.Message;

@Repository
public class MessageService extends MessageDaoImpl{

	@Autowired
	private MessageDao messageDao;
	
	/***
	 * 时间倒叙查询所有的日志信息
	 * @return
	 * @throws Exception
	 */
	public List<Message> findAllMessage() throws Exception{
		Query query = new Query();
		query.with(new Sort(new Order(Direction.DESC, "createDate")));  
		
		List<Message> meslist=this.messageDao.find(query);
		
		if(meslist==null)
			return null;
		else 
			return meslist;
	}
	
}
