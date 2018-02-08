package com.xunxin.dao.impl;

import java.util.List;

import org.mongodb.framework.dao.GeneralDaoImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.qa.QASectionDao;
import com.xunxin.vo.qa.QASection;

@Repository
public class QASectionDaoImpl extends GeneralDaoImpl<QASection> implements QASectionDao  {

	@Override
	protected Class<QASection> getEntityClass() {
		return QASection.class;
	}

	public List<QASection> getAll() throws Exception {
		Query query = new Query();
		query.with(new Sort(new Order(Direction.DESC, "createDate")));  
		List<QASection> qslist= getMongoTemplate().find(query,QASection.class);
		if(qslist==null)
			return null;
		else 
			return qslist;
	}

	public boolean addSectiont(String section_name, short section_type) {
		Query query = new Query();
		query.addCriteria(new Criteria());
		return false;
	}
	
}
