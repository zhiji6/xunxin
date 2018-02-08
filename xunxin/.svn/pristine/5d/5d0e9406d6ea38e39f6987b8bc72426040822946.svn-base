package com.xunxin.service.app.user;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.xunxin.constants.ExpConstants;
import com.xunxin.dao.impl.app.UserSignDaoImpl;
import com.xunxin.service.app.AppUserService;
import com.xunxin.service.app.SysDictService;
import com.xunxin.service.app.UserFriendsService;
import com.xunxin.vo.account.UserExpChangeRecord;
import com.xunxin.vo.qa.SysDict;
import com.xunxin.vo.user.UserSign;
import com.xunxin.vo.user.UserWelfare;

@Repository
public class UserSignService extends UserSignDaoImpl{

	@Autowired
	private UserFriendsService userFriendsService;
	@Autowired
	private SysDictService sysDictService;
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private UserExpChangeRecordService userExpChangeRecordService;
	//用户签到
	public boolean saveSgn(Integer userId) {
		Date date = new Date();
		boolean flag = isSign(userId);
		if(flag){
			return false;
		}
		boolean flagYes = isSignYesterday(userId);
		UserSign byQuery=new UserSign();
		Integer integral=1;
		Integer num = appUserService.queryUserExp(userId);
		UserSign sign = new UserSign();
		if(flagYes){
			Query query = new Query();
			query.addCriteria(Criteria.where("userId").is(userId));
			query.addCriteria(Criteria.where("isDelete").is(false));
			query.with(new Sort(new Order(Direction.DESC, "createTime")));
			byQuery = findOneByQuery(query);
			if(byQuery != null && byQuery.getSignNum() != null){
				sign.setSignNum(byQuery.getSignNum()+1);
			}
			if(byQuery.getSignNum()>31){
				integral=31;
			}else{
				integral+=byQuery.getSignNum();
			}
		}else{
			num+=integral;
		}
		
		appUserService.user_exp_change(userId,num);
		UserExpChangeRecord exp = new UserExpChangeRecord(ExpConstants.ADD_SIGN, ExpConstants.INCOME, num, integral,num+integral, userId);
		userExpChangeRecordService.insert(exp);
		
		sign.setCreateTime(date);
		sign.setUserId(userId);
		sign.setIsDelete(false);
		insert(sign);
		return true;
	}
	//我的福利
	public UserWelfare queryMyWelfare(Integer userId) {
		UserWelfare welfare = new UserWelfare();
		boolean flag = isSign(userId);
//		List<SysDict> listPortrait = sysDictService.findDics("portraitWelfare");
//		List<SysDict> circleWelfare = sysDictService.findDics("portraitWelfare");
		if(flag){
			welfare.setIsSign(1);
		}else{
			welfare.setIsSign(0);
		}
//		welfare.setCircleList(circleWelfare);
//		welfare.setSelfPortraitList(listPortrait);
		return welfare;
	}
	//今天是否签到
	public boolean isSign(Integer userId){
		Date date = new Date();
		Date morningHours = userFriendsService.getMorningHours(date);
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId));
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("createTime").gte(morningHours));
		List<UserSign> list = find(query);
		return list!=null && list.size()>0? true : false;
	}
	//昨天是否签到
	public boolean isSignYesterday(Integer userId){
		Date date = new Date();
		Date yesterday = new Date(date.getTime() - 86400000L);
		Date morningHours = userFriendsService.getMorningHours(yesterday);
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId));
		query.addCriteria(Criteria.where("isDelete").is(false));
		query.addCriteria(Criteria.where("createTime").gte(morningHours));
		List<UserSign> list = find(query);
		return list==null || list.size()<1? false : true;
	}

}

