package com.xunxin.service.app.qa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.impl.app.AnswerDaoImpl;
import com.xunxin.vo.qa.AnswerVO;
import com.xunxin.vo.qa.QuestionVO;

@Repository
public class AnswerService extends AnswerDaoImpl{


	//获取观点
	public  List<AnswerVO> findAnswerVOS(QuestionVO question) throws Exception {
		List<AnswerVO> list = new ArrayList<AnswerVO>();
		if(question != null&& question.getAnswers() != null && !"".equals(question.getAnswers())){
			String[] ids = question.getAnswers().replace("[","").replace("]","").split(",");
			for (String string : ids) {
				AnswerVO answerVO = findOneById(string.trim());
				list.add(answerVO);
			}
			return list;
		}else{
			return list;
		}
	}


}