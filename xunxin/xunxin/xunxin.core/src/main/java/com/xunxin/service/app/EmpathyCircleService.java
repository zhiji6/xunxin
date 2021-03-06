package com.xunxin.service.app;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.junit.Test;
import org.mongodb.framework.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.xunxin.dao.circle.EmpathyCircleDao;
import com.xunxin.dao.im.HeartConsonancMapper;
import com.xunxin.dao.impl.EmpathyCircleDaoImpl;
import com.xunxin.service.ArecordService;
import com.xunxin.service.app.circle.CircleCommentService;
import com.xunxin.service.app.user.MongoDBUtil;
import com.xunxin.vo.circle.CircleComment;
import com.xunxin.vo.circle.EmpathyCircle;
import com.xunxin.vo.im.HeartConsonanc;
import com.xunxin.vo.im.SelfPortrait;
import com.xunxin.vo.sys.Log;
import com.xunxin.vo.user.UserEntity;

@Repository
public class EmpathyCircleService extends EmpathyCircleDaoImpl{

	private static final Logger logger = Logger.getLogger(EmpathyCircleService.class);
	
	@Autowired
	private AppUserService appUserService;
	
	@Autowired
	private HeartConsonancMapper heartConsonancMapper;
	
	@Autowired
	private CircleCommentService circleCommentService;
	@Autowired
	private SelfPortraitService selfPortraitService;
	@Autowired
	private UserFriendsService userFriendsService;
	@Autowired
	private ArecordService arecordService;
	@Autowired
	private ArecordTestService arecordTestService;
	public void updateLikeNum(String empathyCircleId, Integer giveUp) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(empathyCircleId));
		query.addCriteria(Criteria.where("isDelete").is(false));
		EmpathyCircle findOneByQuery = findOneByQuery(query);
		if(findOneByQuery != null && giveUp != null && giveUp.equals(0) && findOneByQuery.getLikeNum()!=null){
			Update update = Update.update("likeNum", findOneByQuery.getLikeNum()+1);
			update.set("updateDate", new Date());
			updateFirst(query, update);
		}
		else if(findOneByQuery!= null && giveUp != null && giveUp.equals(1) && findOneByQuery.getLikeNum()!=null){
			if(findOneByQuery.getLikeNum().intValue()<1){
				Update update = Update.update("likeNum", 0);
				update.set("updateDate", new Date());
				updateFirst(query, update);
			}else{
				Update update = Update.update("likeNum", findOneByQuery.getLikeNum()-1);
				update.set("updateDate", new Date());
				updateFirst(query, update);
			}
			
		}else{
			Update update = Update.update("likeNum", 0);
			update.set("updateDate", new Date());
			updateFirst(query, update);
		}
		
		
	}

	public Pagination<EmpathyCircle> queryMyCircle(Integer userId,Integer pageNo,Integer pageSize) {
		UserEntity userEntry = appUserService.findById(userId);
		if(userEntry== null){
			return new Pagination<EmpathyCircle>();
		}
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("userId").is(userId));
		query.with(new Sort(new Order(Direction.DESC, "createTime")));
		Pagination<EmpathyCircle> pagination=this.findPaginationByQuery(query, pageNo, pageSize);
		pagination = circleDataFormat(pagination,userEntry);
		return pagination;
//		if(pagination != null && pagination.getDatas() != null){
//			for (EmpathyCircle empathyCircle : pagination.getDatas()) {
//				Query queryReply = new Query();
//				queryReply.addCriteria(Criteria.where("isDelete").is(false));
//				queryReply.addCriteria(Criteria.where("empathyCircleId").is(empathyCircle.getId()));
//				List<CircleComment> listComment = circleCommentService.find(queryReply);
//				for (CircleComment circleComment : listComment) {
//					String userName;
//					String replyName;
//					if(circleComment.getUserId() != null){
//						userName = appUserService.findUserNameByUserId(circleComment.getUserId());
//						circleComment.setNickName(userName);
//					}
//					 if(circleComment.getReplyUserId()!= null){
//						replyName = appUserService.findUserNameByUserId(circleComment.getReplyUserId());
//						circleComment.setReployName(replyName);
//					 }
//					
//				}
//				empathyCircle.setCircleComment(listComment);
//				
//				SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
//				if(empathyCircle.getCreateTime() != null){
//					empathyCircle.setTime(sdf.format(empathyCircle.getCreateTime()));
//				}
//				if(userEntry.getGender() != null){
//					empathyCircle.setGender(userEntry.getGender());
//				}
//				if(userEntry.getNickName() != null){
//					empathyCircle.setNickName(userEntry.getNickName());
//				}
//				if(userEntry.getSexualOrientation() != null){
//					empathyCircle.setSexualOrientation(userEntry.getSexualOrientation());;
//				}
//			}
//		}
//		return pagination;
	}

	public Pagination<EmpathyCircle> queryCircles(Integer userId,Integer pageNo,Integer pageSize) {
		List<HeartConsonanc> heartConsonanc = heartConsonancMapper.findHertConsonanceByUserId(userId);
		List<Integer> list = new ArrayList<>();
		for (HeartConsonanc hert : heartConsonanc) {
			if(hert.getUserId()!= null){
				list.add(hert.getConsonanceId());
			}
		}
		List<EmpathyCircle> record =new ArrayList<EmpathyCircle>();
		MongoDatabase mdb=MongoDBUtil.getDatabase();
		MongoCollection<Document> document =mdb.getCollection("empathyCircle");
		Document key =new Document();//指定只返回record字段信息  1表示需要显示，0表示不需要显示
		key.append("_id", 1);
		key.append("createTime", 1);
		key.append("content", 1);
		key.append("photos", 1);
		key.append("userId", 1);
		key.append("address", 1);
		key.append("isVague", 1);
		BasicDBObject query = new BasicDBObject();
		query.put("userId", new BasicDBObject("$in", list));
		BasicDBObject sort = new BasicDBObject();
        sort.put("createTime",1);
		FindIterable<Document> cursors = document.find(query).sort(sort).skip(pageNo).limit(pageSize).projection(key);
		int count = 0;
		FindIterable<Document> find = document.find(query);
		for (Document  cursor : find) {
			 count++;
			}
		for (Document cursor : cursors) {
			EmpathyCircle circle = new EmpathyCircle();
			ArrayList object = (ArrayList) cursor.get("photos");
			if(object != null){
				circle.setPhotos((String[])object.toArray(new String[0]));
			}
			circle.setUserId(cursor.getInteger("userId"));
			circle.setContent(cursor.getString("content"));
			circle.setAddress(cursor.getString("address"));
			circle.setIsVague(cursor.getInteger("isVague"));
			circle.setId(cursor.get("_id").toString());
			circle.setCreateTime(cursor.get("createTime", Date.class));
			SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
			if(circle.getCreateTime() != null){
				circle.setTime(sdf.format(circle.getCreateTime()));
			}
			Query queryReply = new Query();
			queryReply.addCriteria(Criteria.where("isDelete").is(false));
			queryReply.addCriteria(Criteria.where("empathyCircleId").is(circle.getId()));
			List<CircleComment> listComment = circleCommentService.find(queryReply);
			for (CircleComment circleComment : listComment) {
				String userName;
				String replyName;
				if(circleComment.getUserId() != null){
					userName = appUserService.findUserNameByUserId(circleComment.getUserId());
					circleComment.setNickName(userName);
				}
				 if(circleComment.getReplyUserId()!= null){
					replyName = appUserService.findUserNameByUserId(circleComment.getReplyUserId());
					circleComment.setReployName(replyName);
				 }
			}
			circle.setCircleComment(listComment);
			record.add(circle);
		}
		Pagination<EmpathyCircle> pagination=new Pagination<EmpathyCircle>();
		pagination.setDatas(record);
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setTotalCount(count);
		return pagination;
	}

	public void insertSelfPortrait(Integer userId) {
		SelfPortrait self = new SelfPortrait();
		self.setUserId(userId);
		self.setType("cicle");
		self.setIsRead(0);
		self.setIsDelete(false);
		self.setCreateTime(new Date());
		self.setUpdateTime(new Date());
		selfPortraitService.insert(self);
		
		
	}
	//查看共情圈权限
	public Boolean queryJurisdiction(Integer userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("type").is("cicle"));
		query.addCriteria(Criteria.where("userId").is(userId));
		Date date = userFriendsService.getMorningHours(new Date());
		query.addCriteria(Criteria.where("createTime").gt(date));
		List<SelfPortrait> list = selfPortraitService.find(query);
		Integer grade = appUserService.findUserGradeByUserId(userId);
		if(grade != null && grade.equals(1)){
			if(list.size()<11){
				return true;
			}else{
				return false;
			}
		}
		else if(grade != null && grade.equals(2)){
			if(list.size()<21){
				return true;
			}else{
				return false;
			}
		}
		else if(grade != null && grade.equals(3)){
			if(list.size()<31){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	//按兴趣查看共情圈
	public Pagination<EmpathyCircle> queryCirclesPlanB(Integer userId, Integer pageNo, Integer pageSize) {
		List<Integer> users = arecordTestService.findUsersByModuleHeat(userId,3);
		UserEntity userEntry = appUserService.findById(userId);
		if(userEntry== null){
			return new Pagination<EmpathyCircle>();
		}
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("userId").in(users));
		query.with(new Sort(new Order(Direction.DESC, "createTime")));
		Pagination<EmpathyCircle> pagination=this.findPaginationByQuery(query, pageNo, pageSize);
		pagination = circleDataFormat(pagination,userEntry);
		return pagination;
	}
	//共情圈数据格式化
	private Pagination<EmpathyCircle> circleDataFormat(Pagination<EmpathyCircle> pagination, UserEntity userEntry) {
		if(pagination != null && pagination.getDatas() != null){
			for (EmpathyCircle empathyCircle : pagination.getDatas()) {
				Query queryReply = new Query();
				queryReply.addCriteria(Criteria.where("isDelete").is(false));
				queryReply.addCriteria(Criteria.where("empathyCircleId").is(empathyCircle.getId()));
				List<CircleComment> listComment = circleCommentService.find(queryReply);
				for (CircleComment circleComment : listComment) {
					String userName;
					String replyName;
					if(circleComment.getUserId() != null){
						userName = appUserService.findUserNameByUserId(circleComment.getUserId());
						circleComment.setNickName(userName);
					}
					 if(circleComment.getReplyUserId()!= null){
						replyName = appUserService.findUserNameByUserId(circleComment.getReplyUserId());
						circleComment.setReployName(replyName);
					 }
					
				}
				empathyCircle.setCircleComment(listComment);
				
				SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
				if(empathyCircle.getCreateTime() != null){
					empathyCircle.setTime(sdf.format(empathyCircle.getCreateTime()));
				}
				if(userEntry.getGender() != null){
					empathyCircle.setGender(userEntry.getGender());
				}
				if(userEntry.getNickName() != null){
					empathyCircle.setNickName(userEntry.getNickName());
				}
				if(userEntry.getSexualOrientation() != null){
					empathyCircle.setSexualOrientation(userEntry.getSexualOrientation());;
				}
			}
		}
		return pagination;
	}
}


