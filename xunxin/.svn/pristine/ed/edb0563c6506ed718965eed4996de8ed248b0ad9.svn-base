package com.xunxin.service.app.qa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.xunxin.constants.DynamicConstants;
import com.xunxin.constants.ExpConstants;
import com.xunxin.controller.app.message.MessTypeFactory;
import com.xunxin.dao.impl.CommentTopDaoImpl;
import com.xunxin.service.ArecordService;
import com.xunxin.service.app.AppUserService;
import com.xunxin.service.app.CommentService;
import com.xunxin.service.app.IMNewsService;
import com.xunxin.service.app.user.DynamicThumbRecordService;
import com.xunxin.service.app.user.UserDynamicRecordService;
import com.xunxin.service.app.user.UserExpChangeRecordService;
import com.xunxin.util.CalcDemo;
import com.xunxin.vo.account.UserExpChangeRecord;
import com.xunxin.vo.qa.AnswerVO;
import com.xunxin.vo.qa.CommentTop;
import com.xunxin.vo.qa.CommentVO;
import com.xunxin.vo.qa.GiveUpVo;
import com.xunxin.vo.qa.QuestionVO;
import com.xunxin.vo.user.UserDynamicRecordVO;


@Repository
public class CommentTopService extends CommentTopDaoImpl{

	private static final Logger logger = Logger.getLogger(CommentTopService.class);

	@Autowired
	private QuestionService questionService;
	@Autowired
	private ArecordService arecordService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private AnswerService answerService;
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private UserExpChangeRecordService userExpChangeRecordService;
	@Autowired
	private UserDynamicRecordService userDynamicRecordService;
	@Autowired
	private IMNewsService iMNewsService;
	//置顶
	public void saveOrupdate(String questionId, Integer userId, String commentId, Integer isTop) {
		//获取积分 
		Integer num = appUserService.queryUserExp(userId);
		if(num == null || num<20){
			throw new IllegalArgumentException("积分不足");   
		}
		appUserService.user_exp_change(userId,num-20);
		UserExpChangeRecord exp = new UserExpChangeRecord("评论置顶", "expend", num, 20, num-20, userId);
		userExpChangeRecordService.insert(exp);
		Query query = new Query();
		query.addCriteria(Criteria.where("commentId").is(commentId));
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("userId").is(userId));
		if(questionId != null && !"".equals(questionId)){
			query.addCriteria(Criteria.where("questionId").is(questionId));
		}
		
		CommentTop findOneByQuery = findOneByQuery(query);
		if(findOneByQuery != null){
			Update update = Update.update("giveUP", isTop);
			update.set("updateTime", new Date());
			updateFirst(query, update);
		}else{
			CommentTop vo = new CommentTop();
			vo.setCommentId(commentId);
			vo.setCreateTime(new Date());
			vo.setIsDelete(false);
			vo.setIsTop(isTop);
			vo.setUserId(userId);
			vo.setQuestionId(questionId);
			insert(vo);
		}
		
		//置顶为观点
		queryTop(questionId,commentId);
	}

		
	//是否置顶
	public void isTop(CommentVO comment, Integer userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("commentId").is(comment.getId()));
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("userId").is(userId));
		query.addCriteria(Criteria.where("isTop").is(0));
		CommentTop byQuery = findOneByQuery(query);
		if(byQuery != null){
			comment.setStatus("Y");
		}else{
			comment.setStatus("N");
		}
	}
	//查询置顶数量
	public void topNum(CommentVO comment) {
		Query query = new Query();
		query.addCriteria(Criteria.where("commentId").is(comment.getId()));
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("isTop").is(0));
		Integer count = findCountByQuery(query);
		comment.setTopNum(count);
	}
	//置顶评论为观点
	private void queryTop(String questionId, String commentId) {
		if(questionId == null || commentId == null){
			return ;
		}
		QuestionVO question = questionService.findOneById(questionId);
		Double weight=1.5;
		if(question != null && question.getTopNum() != null){
			weight=weight+0.5*question.getTopNum();
		}
		String[] strings = question.getAnswers().replace("[","").replace("]","").split(",");
		//查询有效观点数
		Integer numAnswer = arecordService.effectiveView(questionId);
		if(numAnswer<1){
			return;
		}
		Integer num = arecordService.queryNum(questionId);
		if(num <1){
			return;
		}
		Query query = new Query();
		query.addCriteria(Criteria.where("commentId").is(commentId));
		query.addCriteria(Criteria.where("isDelete").is(false));
		Integer countByQuery = findCountByQuery(query);
		CommentVO comment = commentService.findOneById(commentId);
		if(comment == null || comment.getQuestionId() == null || comment.getQuestionId().length() ==0){
			return ;
		}
		if(comment.getQuestionId() != null && comment.getQuestionId().length() > 0){
			iMNewsService.userBehaviorPushMessage(MessTypeFactory.COMMENT_TOP,comment.getUserId(),null,comment.getQuestionId());
		}
		if(countByQuery != null &&countByQuery > num/numAnswer*weight){
			
			AnswerVO answerVO = new AnswerVO();
			if(comment == null || comment.getContent() ==null || comment.getContent().length() == 0){
				return;
			}
			answerVO.setAnswer(comment.getContent());
			answerService.insert(answerVO);
			String ans = question.getAnswers().substring(0,question.getAnswers().length()-1);
			String anser = ans+","+answerVO.getId()+"]";
			questionService.updateAnswer(questionId,anser);
			//删除评论
			commentService.remove(comment);
			//生成动态
		
			if(comment != null && comment.getUserId() >0){
				userDynamicRecordService.saveUserDynamic(comment.getUserId(),DynamicConstants.FIXED_ANSWER+"*"+comment.getQuestionId(),"我的评论被选为Q&A的固定观点。",null);
				//置顶积分
				userExpChangeRecordService.insertUserExp(ExpConstants.FIXED_ANSWER,comment.getUserId(),ExpConstants.INCOME,50);
				//发送推送
				if(comment.getQuestionId() != null && comment.getQuestionId().length() > 0){
					iMNewsService.userBehaviorPushMessage(MessTypeFactory.MESSAGE_LEAVE,comment.getUserId(),null,comment.getQuestionId());
				}
				
			}

		}

	}
}
