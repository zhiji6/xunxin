package com.xunxin.service.app;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.mongodb.framework.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageHelper;
import com.xunxin.controller.app.message.MessTypeFactory;
import com.xunxin.dao.impl.CommentDaoImpl;
import com.xunxin.dao.qa.CommentDao;
import com.xunxin.dao.qa.CommentServiceInter;
import com.xunxin.service.app.qa.CommentTopService;
import com.xunxin.service.app.qa.QuestionService;
import com.xunxin.util.page.PaginationContext;
import com.xunxin.vo.qa.CommentVO;
import com.xunxin.vo.qa.GiveUpVo;
import com.xunxin.vo.qa.QuestionVO;

@Repository

public class CommentService extends CommentDaoImpl implements CommentServiceInter{

	private static final Logger logger = Logger.getLogger(CommentService.class);
	
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private GiveUpService giveUpService;
	@Autowired
	private CommentTopService commentTopService;
	@Autowired
	private IMNewsService iMNewsService;
	//保存评论
	public CommentVO saveComment(int userId, String questionId, String content, Integer replyUserId, String commentId) {
		CommentVO comment = new CommentVO();
		comment.setQuestionId(questionId);
		comment.setContent(content);
		comment.setUserId(userId);
		if(replyUserId != null){
			comment.setReplyUserId(replyUserId);
		}
		if(commentId != null && !"".equals(commentId)){
			comment.setCommentId(commentId);
			comment.setType("reply");
			
		}else{
			comment.setType("comment");
		}
		comment.setCreateTime(new Date());
		comment.setUpdateTime(new Date());
		comment.setIsDelete(false);
		insert(comment);
		if(commentId != null && commentId.trim() !=""){
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(commentId));
			CommentVO byQuery = findOneByQuery(query);
			//消息推送
			if(replyUserId != null){
				iMNewsService.userBehaviorPushMessage(MessTypeFactory.REPLY_COMMENT,replyUserId,userId,commentId);
			}else{
				if(byQuery!= null &&(Integer)byQuery.getUserId() != null){
					iMNewsService.userBehaviorPushMessage(MessTypeFactory.MESSAGE_ANSWER,byQuery.getUserId(),userId,commentId);
				}
			}
			dataFromt(byQuery,userId);
			return byQuery;
		}else{
			dataFromt(comment,userId);
			return comment;
		}
		
	}


	//评论整理数据
	private void dataFromt(CommentVO comment,Integer userId) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//最近回复
		findselfComment(comment.getId(),comment,userId);
		//是否置顶
		if(comment != null && userId != null){
			commentTopService.isTop(comment,userId);
		}
		//置顶数
		commentTopService.topNum(comment);
		if(comment != null && (Integer)comment.getUserId() !=null){
			comment.setNickName(appUserService.findUserNameByUserId(comment.getUserId()));
			comment.setGender(appUserService.findUserGenderByUserId(comment.getUserId()));
		}
		if(comment.getCreateTime() != null){
			
			Date dateOne = UserFriendsService.getCurrentTime(60);
			Date dateTwo = UserFriendsService.getCurrentTime(60*2);
			if(comment.getCreateTime().getTime()>dateOne.getTime()){
				comment.setCreateDate("一小时前");
			}
			else if(comment.getCreateTime().getTime()>dateTwo.getTime()){
				comment.setCreateDate("两小时前");
			}
			else{
				comment.setCreateDate(formatter.format(comment.getCreateTime()));
			}
		}
		
	}



	public Query findAllLogbyQuery(boolean b, String questionId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("questionId").is(questionId));
		query.addCriteria(Criteria.where("type").is("comment"));
		query.with(new Sort(new Order(Direction.DESC, "createTime")));  
		return query;
	}


	//评论回复model
	public List<CommentVO> findAllBy(String commentId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("commentId").is(commentId));
		query.with(new Sort(new Order(Direction.DESC, "createTime")));  
		query.with(new Sort(new Order(Direction.DESC, "topNum")));  
		return find(query);
	}



	public List<CommentVO> findAllcommunication(int userId, int replyUserId, String questionId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("questionId").is(questionId));
		query.addCriteria(Criteria.where("userId").is(userId));
		query.addCriteria(Criteria.where("replyUserId").is(replyUserId));
		query.with(new Sort(new Order(Direction.DESC, "createTime")));  
		List<CommentVO> listUser =  find(query);
		
		Query queryreply = new Query();
		queryreply.addCriteria(Criteria.where("isDelete").is(false));
		queryreply.addCriteria(Criteria.where("questionId").is(questionId));
		queryreply.addCriteria(Criteria.where("userId").is(replyUserId));
		queryreply.addCriteria(Criteria.where("replyUserId").is(userId));
		queryreply.with(new Sort(new Order(Direction.DESC, "createTime")));  
		List<CommentVO> listReply =  find(queryreply);
		
		List<CommentVO> list = null;
		
		list = listUser;
		 
		list.removeAll(listReply);
	 
		list.addAll(listReply);
		
		Collections.sort(list, new Comparator<CommentVO>() {
	           public int compare(CommentVO o1, CommentVO o2) {
	               return o1.getCreateTime().compareTo(o2.getCreateTime());
	           }
	       });
		return list;
	}




	//点赞
	public void giveUp(String commentId, Integer giveUp) {
		Query queryupdate = new Query();
		queryupdate.addCriteria(Criteria.where("isDelete").is(false));
		queryupdate.addCriteria(Criteria.where("id").is(commentId));
		CommentVO findOneByQuery = findOneByQuery(queryupdate);
		if(findOneByQuery != null && findOneByQuery.getIdentificationNum() != null && giveUp.equals(0)){
			Update update = Update.update("identificationNum",findOneByQuery.getIdentificationNum()+1);
			updateFirst(queryupdate, update);
		}
		
		if(findOneByQuery != null && findOneByQuery.getIdentificationNum() != null && giveUp.equals(1)){
			if(findOneByQuery.getIdentificationNum().intValue()<1){
				Update update = Update.update("identificationNum",Integer.valueOf(0));
				updateFirst(queryupdate, update);
			}else{
				Update update = Update.update("identificationNum",findOneByQuery.getIdentificationNum()-1);
				updateFirst(queryupdate, update);
			}
			
		}
	}


	//评论高频
	public Integer isFrequency(Integer userId) {
	    Calendar beforeTime = Calendar.getInstance();
	    beforeTime.add(Calendar.SECOND, -5);
	    Date beforeD = beforeTime.getTime();
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("userId").is(userId));
		query.addCriteria(Criteria.where("createTime").gte(beforeD));
		List<CommentVO> find = find(query);
		return find.size();
	}


	//查询评论分页
	public Pagination<CommentVO> findPageList(CommentVO commentVO) {
		PageHelper.startPage(PaginationContext.getPageNum(), PaginationContext.getPageSize());
		Query query = new Query();
		query.with(new Sort(new Order(Direction.DESC, "createDate")));
		if(commentVO !=null && commentVO.getIsDelete() != null){
			query.addCriteria(Criteria.where("isDelete").is(commentVO.getIsDelete()));
		}
		Pagination<CommentVO> pagination=findPaginationByQuery(query, PaginationContext.getPageNum(),PaginationContext.getPageSize());
		for (CommentVO comment : pagination.getDatas()) {
			if(comment != null && Integer.valueOf(comment.getUserId()) != null){
				comment.setNickName(appUserService.findUserNameByUserId(comment.getUserId()));
			}
			if(comment != null && comment.getReplyUserId() != null){
				comment.setReployName((appUserService.findUserNameByUserId(comment.getReplyUserId())));
			}
			if(comment != null && comment.getQuestionId() != null){
				Query queryTwo = new Query();
				queryTwo.addCriteria(Criteria.where("id").is(comment.getQuestionId()));
				QuestionVO question = questionService.findOneByQuery(queryTwo);
				if(question != null && question.getName() != null){
					comment.setQuestionName(question.getName());
				}
			}
		}
		return pagination;
	}



	public void deleteByIds(String iDS) {
		String[] ids = iDS.split(",");
		Query query = new Query();
		query.addCriteria(Criteria.where("id").in(ids));
		Update update = Update.update("isDelete",true);
		updateAllByQuery(query,update);
		
	}



	public void findselfComment(String id, CommentVO comment, Integer userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("commentId").is(id));
		query.addCriteria(Criteria.where("isDelete").is(false));
//		query.addCriteria(Criteria.where("userId").ne(userId));
		query.with(new Sort(new Order(Direction.DESC, "createTime")));
		List<CommentVO> list = find(query);
		if(list != null && list.size() > 0){
			comment.setCommentVO(list.get(0));
			comment.setReplyNum(list.size());
			if(list.get(0).getUserId() > 0){
				list.get(0).setNickName(appUserService.findUserNameByUserId(list.get(0).getUserId()));
			}
			if(list.get(0).getReplyUserId() != null){
				list.get(0).setReployName((appUserService.findUserNameByUserId(list.get(0).getReplyUserId())));
			}
			
		}
	}


	//是否点赞
	public void isGiveUP(CommentVO comment, Integer userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("commentId").is(comment.getId()));
		query.addCriteria(Criteria.where("isDelete").is(false));
		
			query.addCriteria(Criteria.where("userId").is(userId));
			query.with(new Sort(new Order(Direction.DESC, "createTime")));
			GiveUpVo giveup = giveUpService.findOneByQuery(query);
			if(giveup != null && giveup.getGiveUP() != null){
				comment.setGiveUp(giveup.getGiveUP());
			}else{
				comment.setGiveUp(1);
			}
	}

	//查看评论列表
	public Pagination<CommentVO> findCommentLis(String questionId, Integer userId, Integer pageNo, Integer pageSize) {
		Pagination<CommentVO> byQuery = findPaginationByQuery(findAllLogbyQuery(false,questionId), pageNo, pageSize);
		for (CommentVO comment : byQuery.getDatas()) {
			dataFromt(comment,userId);
		}
		return byQuery;
	}

	//查看回复列表
	public List<CommentVO> findReplyList(String commentId, Integer userId) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<CommentVO> commentList = findAllBy(commentId);
		for (CommentVO comment : commentList) {
			if(comment != null && (Integer)comment.getUserId() !=null){
				comment.setNickName(appUserService.findUserNameByUserId(comment.getUserId()));
				comment.setGender(appUserService.findUserGenderByUserId(comment.getUserId()));
			}
			isGiveUP(comment,userId);
			if(comment != null && (Integer)comment.getReplyUserId() !=null){
				comment.setReployName((appUserService.findUserNameByUserId(comment.getReplyUserId())));
			}
			if(comment.getCreateTime() != null){
				Date dateOne = UserFriendsService.getCurrentTime(60);
				Date dateTwo = UserFriendsService.getCurrentTime(60*2);
				if(comment.getCreateTime().getTime()>dateOne.getTime()){
					comment.setCreateDate("一小时前");
				}
				else if(comment.getCreateTime().getTime()>dateTwo.getTime()){
					comment.setCreateDate("两小时前");
				}
				else{
					comment.setCreateDate(formatter.format(comment.getCreateTime()));
				}
			}
		}
		return commentList;
	}



	//查看评论详情
	public CommentVO queryComment(String commentId, Integer userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(commentId));
		List<CommentVO> list = find(query);
		if(list != null && list.size()>0){
			dataFromt(list.get(0),userId);
			return list.get(0);
		}
		return null;
	}
}
