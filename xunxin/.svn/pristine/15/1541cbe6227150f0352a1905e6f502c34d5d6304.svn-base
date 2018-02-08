package com.xunxin.service.app;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.impl.GiveUPDaoImpl;
import com.xunxin.vo.qa.CommentVO;
import com.xunxin.vo.qa.GiveUpVo;
@Repository
public class GiveUpService extends GiveUPDaoImpl{
	
	private static final Logger logger = Logger.getLogger(GiveUpService.class);

	@Autowired 
	private CommentService commentService;
	
	public void setGiveUp(List<CommentVO> datas) {
		
		if(datas != null && datas.size() > 0){
			for (CommentVO commentVO : datas) {
				if(commentVO != null && (Integer)commentVO.getUserId() != null){
					Query query = new Query();
					query.addCriteria(Criteria.where("isDelete").is(false));
					query.addCriteria(Criteria.where("userId").is(commentVO.getUserId()));
					query.addCriteria(Criteria.where("commentId").is(commentVO.getId()));
					GiveUpVo oneGiveUp = findOneByQuery(query);
					
					if(oneGiveUp != null && oneGiveUp.getGiveUP() != null && oneGiveUp.getGiveUP().intValue() == 1){
						commentVO.setGiveUp(1);
					}
					
				}
			}
		}
	}

	public void saveOrupdate(String questionId, Integer userId, String commentId, Integer giveUp) {
		Query query = new Query();
		query.addCriteria(Criteria.where("commentId").is(commentId));
		commentService.giveUp(commentId,giveUp);
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("userId").is(userId));
		if(questionId != null && !"".equals(questionId)){
			query.addCriteria(Criteria.where("questionId").is(questionId));
		}
		
		GiveUpVo findOneByQuery = findOneByQuery(query);
		if(findOneByQuery != null){
			Update update = Update.update("giveUP", giveUp);
			update.set("updateTime", new Date());
			updateFirst(query, update);
		}else{
			GiveUpVo vo = new GiveUpVo();
			vo.setCommentId(commentId);
			vo.setCreateTime(new Date());
			vo.setUpdateTime(new Date());
			vo.setIsDelete(false);
			vo.setGiveUP(giveUp);
			vo.setUserId(userId);
			vo.setQuestionId(questionId);
			insert(vo);
		}
		
		
	}

}
