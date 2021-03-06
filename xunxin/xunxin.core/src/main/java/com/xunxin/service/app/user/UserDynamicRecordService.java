package com.xunxin.service.app.user;

import java.util.Date;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.xunxin.dao.impl.UserDynamicRecordDaoImpl;
import com.xunxin.vo.qa.QuestionVO;
import com.xunxin.vo.user.UserDynamicRecordVO;

/**
 * 
 * Copyright © 2017 Xunxin Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2017年12月17日 -- 下午4:52:15
 * @Version 1.0
 * @Description			用户动态记录
 */	
@Repository
public class UserDynamicRecordService extends UserDynamicRecordDaoImpl{

	public void saveUserDynamic(Integer userId, String publishCircle, String content, String img) {
		UserDynamicRecordVO vo = new UserDynamicRecordVO();
		vo.setDynamicUrls(img);
		vo.setDynamicSource(publishCircle);
		vo.setContent(content);
		vo.setUserId(userId);
		vo.setThumbCount(0);
		insert(vo);
	}

	public void saveUserDynamicQA(Integer userId, String publishCircle, String content, Integer num) {
		UserDynamicRecordVO vo = new UserDynamicRecordVO();
		vo.setDynamicSource(publishCircle);
		vo.setContent(content);
		vo.setUserId(userId);
		vo.setThumbCount(0);
		vo.setDynamicExtend(num.toString());
		insert(vo);
		
	}
}
