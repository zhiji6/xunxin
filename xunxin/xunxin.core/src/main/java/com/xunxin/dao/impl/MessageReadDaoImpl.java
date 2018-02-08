package com.xunxin.dao.impl;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.im.MessageReadDao;
import com.xunxin.vo.im.MessageRead;

@Repository
public class MessageReadDaoImpl extends GeneralDaoImpl<MessageRead>  implements MessageReadDao{

	@Override
	protected Class<MessageRead> getEntityClass() {
		return MessageRead.class;
	}
}
