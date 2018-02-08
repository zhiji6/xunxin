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
import org.bson.Document;
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
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.xunxin.dao.impl.CommentDaoImpl;
import com.xunxin.dao.qa.CommentDao;
import com.xunxin.service.app.qa.QuestionService;
import com.xunxin.service.app.user.MongoDBUtil;
import com.xunxin.util.CalcDemo;
import com.xunxin.util.page.PageInfo;
import com.xunxin.util.page.PaginationContext;
import com.xunxin.vo.qa.CommentVO;
import com.xunxin.vo.qa.QuestionVO;
import com.xunxin.vo.sys.Log;
import com.xunxin.vo.user.UserAuthentication;

@Repository
public class CommentService extends CommentDaoImpl{

	private static final Logger logger = Logger.getLogger(CommentService.class);
	
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private QuestionService questionService;


	public void saveComment(int userId, String questionId, String content, Integer replyUserId, String commentId) {
		CommentVO comment = new CommentVO();
		comment.setQuestionId(questionId);
		comment.setContent(content);
		comment.setUserId(userId);
		if(replyUserId != null){
			comment.setReplyUserId(replyUserId);
		}
		
		if(commentId != null && !"".equals(commentId)){
			comment.setStatus("N");
			comment.setCommentId(commentId);
		}
		comment.setCreateTime(new Date());
		comment.setUpdateTime(new Date());
		comment.setIsDelete(false);
		insert(comment);
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("id").is(commentId));
		Update update = Update.update("status", "Y");
		updateFirst(query, update);
	}



	public Query findAllLogbyQuery(boolean b, String questionId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("questionId").is(questionId));
		query.with(new Sort(new Order(Direction.DESC, "createTime")));  
		return query;
	}



	public List<CommentVO> findAllBy(String commentId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("commentId").is(commentId));
		query.with(new Sort(new Order(Direction.DESC, "createTime")));  
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

	public  List<CommentVO> queryTop(Integer num,String questionId,Double praiseNum){
		if(num == null || questionId == null || praiseNum == null){
			return new ArrayList<CommentVO>();
		}
		List<CommentVO> list= new ArrayList<CommentVO>(); 
		Double weight=1.5;
		for (int i = 1; i>-1; i++) {
			if(i>=num){
				double d = CalcDemo.exec(weight+"*"+praiseNum);
				Query query = new Query();
				query.addCriteria(Criteria.where("isDelete").is(false));
				query.addCriteria(Criteria.where("questionId").is(questionId));
				query.addCriteria(Criteria.where("identificationNum").gte(Integer.parseInt(new java.text.DecimalFormat("0").format(d))));
				query.with(new Sort(new Order(Direction.DESC, "createTime")));  
				CommentVO comment = findOneByQuery(query);
				
				if(comment!= null){
					list.add(comment);
				}else{
					break;
				}
				
				Query queryupdate = new Query();
				queryupdate.addCriteria(Criteria.where("isDelete").is(false));
				queryupdate.addCriteria(Criteria.where("id").is(comment.getId()));
				Update update = Update.update("isDelete",true);
				updateFirst(query, update);
				
			}
			weight=weight+0.5;
		}
		
		return list;
		
	}



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



	public Integer isFrequency(Integer userId) {
	    Calendar beforeTime = Calendar.getInstance();
	    beforeTime.add(Calendar.SECOND, -5);
	    Date beforeD = beforeTime.getTime();
	    MongoDatabase mdb=MongoDBUtil.getDatabase();
		MongoCollection<Document> document =mdb.getCollection("commentVO");
		Document key =new Document();
		key.append("id", 1);
		key.append("createTime", 1);
		BasicDBObject query = new BasicDBObject();
		query.put("createTime", new BasicDBObject("$gte", beforeD));
		query.put("userId",userId);
		FindIterable<Document> cursors = document.find(query);
		int count = 0;
		for (Document  cursor : cursors) {
			 count++;
			}
		return count;
	}



	public Pagination<CommentVO> findPageList(CommentVO commentVO) {
		PageHelper.startPage(PaginationContext.getPageNum(), PaginationContext.getPageSize());
		Query query = new Query();
		query.with(new Sort(new Order(Direction.DESC, "createDate")));
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
//		List<String> list = Arrays.asList(ids);
//		list.remove(list.size()-1);
		Query query = new Query();
		query.addCriteria(Criteria.where("id").in(ids));
		Update update = Update.update("isDelete",true);
		updateAllByQuery(query,update);
		
	}

}
