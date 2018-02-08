package com.xunxin.service.app;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.impl.QASectionDaoImpl;
import com.xunxin.vo.qa.QASection;

@Repository
public class QASectionService extends QASectionDaoImpl{


	/*@Autowired
	private QASectionDao qaSectionDao;*/

	public List<QASection> getAll() throws Exception {
		Query query = new Query();
		query.with(new Sort(new Order(Direction.ASC, "sectionType")));  
		List<QASection> qslist= find(query);
		
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
