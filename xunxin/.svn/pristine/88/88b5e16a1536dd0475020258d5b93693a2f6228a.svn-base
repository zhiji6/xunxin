package com.xunxin.service.app.qa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.impl.XTagLibraryDaoImpl;
import com.xunxin.vo.qa.AnswerVO;
import com.xunxin.vo.qa.QuestionVO;
import com.xunxin.vo.qa.XTagLibrary;

/**
 * 
 * Copyright © 2017 Xunxin Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2017年12月20日 -- 下午4:21:43
 * @Version 1.0
 * @Description		Q&A标签库
 */
@Repository
public class XTagLibraryService extends XTagLibraryDaoImpl{

	public List<XTagLibrary> findTags(QuestionVO question) {
		List<XTagLibrary> list = new ArrayList<XTagLibrary>();
		if(question != null&& question.getTags() != null && !"".equals(question.getTags())){
			String ans = question.getTags();
			String substring = ans.substring(1);
			String subs = substring.substring(0,substring.length()-1);
			String[] ids = subs.split(",");
			for (String string : ids) {
				Query query = new Query();
				query.addCriteria(Criteria.where("id").is(string));
				XTagLibrary byQuery = findOneByQuery(query);
				list.add(byQuery);
			}
			return list;
		}else{
			return list;
		}
	}
	
	

}
