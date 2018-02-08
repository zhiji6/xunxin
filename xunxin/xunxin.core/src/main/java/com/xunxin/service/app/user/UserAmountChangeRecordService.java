package com.xunxin.service.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xunxin.constants.AmountConstants;
import com.xunxin.constants.ExpConstants;
import com.xunxin.dao.impl.app.UserAmountChangeRecordDaoImpl;
import com.xunxin.service.app.AppUserService;
import com.xunxin.vo.account.UserAmountChangeRecord;
import com.xunxin.vo.account.UserExpChangeRecord;
import com.xunxin.vo.user.UserEntity;

/**
 * 
 * Copyright © 2017 Xunxin Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2018年1月6日 -- 下午4:46:43
 * @Version 1.0
 * @Description        用户交易明细 
 */
@Repository
public class UserAmountChangeRecordService extends UserAmountChangeRecordDaoImpl{

	@Autowired
	private AppUserService appUserService;
	//金额变动
	public void UserAmountChangeRecord(String fixedAnswer, Integer userId, String income, double integralNum) {
		Double num = appUserService.queryUserAmount(userId);
		if((income.equals(ExpConstants.EXPEND) && num == null) || (income.equals(ExpConstants.EXPEND) && num < integralNum)){
			throw new IllegalArgumentException("金额不足");   
		}
		if(num == null){
			num = 0.0;
		}
		Double endNum = ExpConstants.EXPEND.equals(income) ? num - integralNum : num + integralNum;
		appUserService.user_amount_change(userId,endNum);
		UserAmountChangeRecord record = new UserAmountChangeRecord();
	    record.setDirection(income);
	    record.setChangeType(fixedAnswer);
	    record.setTansferAmount(integralNum);
	    record.setTansferBefore(num);
	    record.setTansferEnd(endNum);
	    record.setUserId(userId);
	    save(record);
	}
		

}
