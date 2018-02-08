package com.xunxin.service.app;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.impl.SelfPortraitDaoImpl;
import com.xunxin.vo.im.SelfPortrait;

@Repository
public class SelfPortraitService extends SelfPortraitDaoImpl{

	private static final Logger logger = Logger.getLogger(SelfPortraitService.class);
	@Autowired
	private UserFriendsService userFriendsService;
	@Autowired
	private AppUserService appUserService;
	public Boolean queryJurisdiction(int userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("type").is("selfPortrait"));
		query.addCriteria(Criteria.where("userId").is(userId));
		Date date = userFriendsService.getMorningHours(new Date());
		query.addCriteria(Criteria.where("createTime").gt(date));
		List<SelfPortrait> list = find(query);
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

	public void insertSelfPortrait(int userId) {
		SelfPortrait self = new SelfPortrait();
		self.setUserId(userId);
		self.setType("selfPortrait");
		self.setIsRead(0);
		self.setIsDelete(false);
		self.setCreateTime(new Date());
		self.setUpdateTime(new Date());
		insert(self);
		
	}
}
