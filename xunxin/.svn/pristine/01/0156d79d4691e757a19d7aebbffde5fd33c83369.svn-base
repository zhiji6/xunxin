package com.xunxin.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.impl.LogDaoImpl;
import com.xunxin.vo.sys.Log;	

@Repository
public class LogService  extends LogDaoImpl{

	
	/***
	 * 时间倒叙查询所有的日志信息
	 * @return
	 * @throws Exception
	 */
	public List<Log> findAllLog() throws Exception{
		Query query = new Query();
		query.with(new Sort(new Order(Direction.DESC, "createDate")));  
		
		List<Log> loglist=find(query);
		
		if(loglist==null)
			return null;
		else 
			return loglist;
	}

	
	public Query findAllLogbyQuery(String type){
		
		Query query = new Query();
		query.addCriteria(Criteria.where("type").is(type));
		query.with(new Sort(new Order(Direction.DESC, "createDate")));  
		return query;
	}
	
	
}
