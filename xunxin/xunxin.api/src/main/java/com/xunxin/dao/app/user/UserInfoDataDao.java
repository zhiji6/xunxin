package com.xunxin.dao.app.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xunxin.vo.condition.UserInfoCondition;
import com.xunxin.vo.user.UserInfoData;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年10月27日 -- 下午3:43:00
 * @Version 1.0
 * @Description	用户第三方账户信息
 */
public interface UserInfoDataDao {

	UserInfoData findOne(String phone);
	void save(UserInfoData userInfo);
	void update(UserInfoData userInfo);
	void delete(UserInfoData userInfo);
	void saveInfo(UserInfoCondition condition);
	
	UserInfoData findBytoken(@Param("openId") String openId,@Param("openType") String openType);
	
	List<UserInfoData> findByUserId(Integer userId);
    void removeAll(Integer userId);
	
}
