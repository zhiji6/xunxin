package com.xunxin.service.app;

import static org.assertj.core.api.Assertions.useRepresentation;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.xunxin.dao.app.user.AppUserDao;
import com.xunxin.dao.app.user.UserInfoDataDao;


import com.xunxin.util.PeriodsUtil;
import com.xunxin.vo.condition.UserInfoCondition;
import com.xunxin.vo.sys.PageData;
import com.xunxin.vo.user.UserEntity;
import com.xunxin.vo.user.UserInfoData;

@Service("userInfoDataService")
public class UserInfoDataService {
	
	private static final Logger logger = Logger.getLogger(UserInfoDataService.class); 

	@Autowired
	private UserInfoDataDao userInfoDataDao;
	@Autowired
	private AppUserDao appUserDao;

	/**
	 * 保存第三方信息
	 * 
	 * @param openId
	 * @param openType
	 * @param uid
	 */
	public void saveInfo(String openId, String openType, int uid) {
		UserInfoCondition condition = new UserInfoCondition(openId, openType, UserEntity.UNUSUAL, uid);
		userInfoDataDao.saveInfo(condition);
	}

	/**
	 * 绑定第三方账号
	 * 
	 * @param userId
	 * @param openId
	 * @param openType
	 */
	public void bind_open_account(int userId, String openId, String openType) {
		UserEntity entity = appUserDao.findById(userId);
		String openRemark = entity.getNickName() + "绑定" + openType + "成功," + "时间:" + PeriodsUtil.getWholeTime(new Date());
		UserInfoData info = new UserInfoData(openId, openType, UserEntity.UNUSUAL, openRemark, new Date(), userId);
		userInfoDataDao.save(info);
	}

	public UserInfoData open_login(String openId, String openType) {
		UserInfoData info = userInfoDataDao.findBytoken(openId,openType);
		if(info != null) {
			return info;
		}
		return null;
	}

	public List<UserInfoData> findByUserId(int userId) {
		return userInfoDataDao.findByUserId(userId);
	}

	
	
}
