package com.xunxin.service.app;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.xunxin.constants.ExpConstants;
import com.xunxin.dao.impl.SelfPortraitDaoImpl;
import com.xunxin.service.app.user.UserAmountChangeRecordService;
import com.xunxin.service.app.user.UserAuthenticationService;
import com.xunxin.service.app.user.UserExpChangeRecordService;
import com.xunxin.vo.im.SelfPortrait;
import com.xunxin.vo.user.UserAuthentication;

@Repository
public class SelfPortraitService extends SelfPortraitDaoImpl{

	private static final Logger logger = Logger.getLogger(SelfPortraitService.class);
	@Autowired
	private UserFriendsService userFriendsService;
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private UserExpChangeRecordService userExpChangeRecordService;
	@Autowired
	private UserAmountChangeRecordService userAmountChangeRecord;
	@Autowired
	private UserAuthenticationService userAuthenticationService;
	//发送自画像权限
	public Boolean queryJurisdiction(int userId, Integer anotherId,String type) {
		Integer grade = appUserService.findUserGradeByUserId(userId);
		Integer setting = appUserService.findUserSelfPortraitUserId(anotherId);
		if(grade != null && grade.equals(0)){
			return false;
		}
		if(setting != null && setting.equals(1) && grade!=null && grade.intValue() <2){
			return false;
		}
		Query query = new Query();
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("type").is(type));
		query.addCriteria(Criteria.where("userId").is(userId));
		Date date = userFriendsService.getMorningHours(new Date());
		query.addCriteria(Criteria.where("createTime").gt(date));
		List<SelfPortrait> list = find(query);
		if(grade != null && grade.equals(1)){
			return list.size() < 11 ? true :false;
		}
		else if(grade != null && grade.equals(2)){
			return list.size() < 21 ? true :false;
		}
		else if(grade != null && grade.equals(3)){
			return list.size() < 31 ? true :false;
		}
		return false;
	}
	//自画像保存
	public void insertSelfPortrait(int userId, Integer anotherId) {
		SelfPortrait self = new SelfPortrait();
		self.setUserId(userId);
		self.setType("selfPortrait");
		self.setIsRead(0);
		self.setAnotherId(anotherId);
		self.setIsDelete(false);
		self.setCreateTime(new Date());
		self.setUpdateTime(new Date());
		insert(self);
	}
	//发送自画像
	public boolean SelfPortrait(Integer userId, Integer anotherId) {
		Boolean flag = queryJurisdiction(userId,anotherId,"selfPortrait");
		if(!flag){
			Integer num = appUserService.queryUserExp(userId);
			if( num == null ||num < 50){
				Double amount = appUserService.queryUserAmount(userId);
				if(amount == null || amount <0.1){
					return false;
				}
				userAmountChangeRecord.UserAmountChangeRecord(ExpConstants.SEND_SELF_PORTRAIT, userId, ExpConstants.EXPEND, 0.1);   
			}else{
				userExpChangeRecordService.insertUserExp(ExpConstants.SEND_SELF_PORTRAIT, userId, ExpConstants.EXPEND, 50);
			}
		}
		return true;
	}
	//是否可以发送自画像
	public Boolean sendPortrait(Integer userId, Integer friendId) {
		Integer grade = appUserService.findUserGradeByUserId(userId);
		Integer setting = appUserService.findUserSelfPortraitUserId(friendId);
		if(setting != null && setting.equals(1) && grade!=null && grade.intValue() <2){
			return false;
		}
		return true;
	}
	//是否可以发送消息personal_authentication
	public Boolean sendMessage(Integer userId, Integer friendId) {
		Integer setting = appUserService.findUserMessageUserId(friendId);
		if(setting == null||setting.equals(0)){
			return true;
		}else {
			List<UserAuthentication> authentList = userAuthenticationService.getAll(userId);
			if(authentList== null || authentList.size()<5){
				return false;
			}
			for (UserAuthentication userAuthentication : authentList) {
				if(userAuthentication.getAuthState() == null ||!userAuthentication.getAuthState().equals(2)){
					return false;
				}
			}
			return true;
		}
	}
}
