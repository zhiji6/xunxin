package com.xunxin.service.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xunxin.constants.ExpConstants;
import com.xunxin.dao.impl.UserExpChangeRecordDaoImpl;
import com.xunxin.service.app.AppUserService;
import com.xunxin.vo.account.UserExpChangeRecord;

@Repository
public class UserExpChangeRecordService extends UserExpChangeRecordDaoImpl{

	@Autowired
	private AppUserService appUserService;
	//积分变动
	public void insertUserExp(String fixedAnswer, Integer userId, String income, int integralNum) {
		
		Integer num = appUserService.queryUserExp(userId);
		if((income.equals(ExpConstants.EXPEND) && num == null) || (income.equals(ExpConstants.EXPEND) && num < integralNum)){
			throw new IllegalArgumentException("积分不足"); 
		}
		if(num == null){
			num = 0;
		}
		Integer endNum = ExpConstants.EXPEND.equals(income) ? num - integralNum : num + integralNum;
		appUserService.user_exp_change(userId,endNum);
		UserExpChangeRecord exp = new UserExpChangeRecord(fixedAnswer, income, num, integralNum, endNum, userId);
		insert(exp);
	}

}
