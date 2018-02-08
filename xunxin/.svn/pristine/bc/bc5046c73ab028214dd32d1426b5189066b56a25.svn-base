package com.xunxin.service.app;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunxin.controller.app.message.MessTypeFactory;
import com.xunxin.dao.im.UserReportsMapper;
import com.xunxin.vo.im.UserReports;
import com.xunxin.vo.im.UserReportsExample;
import com.xunxin.vo.im.UserShields;
import com.xunxin.vo.im.UserShieldsExample;
import com.xunxin.vo.im.UserReportsExample.Criteria;
import com.xunxin.vo.qa.XunxinUserDeviceToken;



@Service("userReportsService")
public class UserReportsService {

	@Autowired
	private UserReportsMapper userReportsMapper;
	@Autowired
	private IMNewsService iMNewsService;
	private static final Logger logger = Logger.getLogger(UserReportsService.class);

	public List<UserReports> findShields(Integer userId) {
		List<UserReports> list = userReportsMapper.findReport(userId);
		return list;
	}
	//保存举报的人的人
	public Integer save(Integer userId, Integer anotherId, Integer isdelete, String reportObjectId, String content, String reportType, Integer value) {
		int insert = 0;
		if(anotherId != null && anotherId > 0){
			saveShield(userId, anotherId, isdelete);
		}
			UserReports userReports = new UserReports();
			userReports.setUserId(userId);
			userReports.setReportId(anotherId);
			userReports.setIsDelete(isdelete);
			userReports.setContent(content);
			userReports.setReportObjectId(reportObjectId);
			userReports.setReportType(reportType);
			userReports.setValue(value);
			userReports.setCreatedate(new Date());
			userReports.setUpdatedate(new Date());
			userReports.setType(UserReports.REPORT);
			insert = userReportsMapper.insert(userReports);
			if(anotherId !=null){
				iMNewsService.userBehaviorPushMessage(MessTypeFactory.MESSAGE_REPORT,anotherId,userId,reportObjectId);
			}
		return insert;
	}

	public void deleteReport(Integer userId, Integer id) {
		userReportsMapper.deleteReport(userId, id);
		
	}
	//保存或更新不感兴趣的人
	public  void saveNoInterest(Integer userId, Integer anotherId, Integer isdelete) {
		UserReports report= userReportsMapper.queryNoInterest(userId,anotherId);
		if(report != null){
			userReportsMapper.updateNoInterest(userId,anotherId,isdelete);
		}else{
			UserReports userReports = new UserReports();
			userReports.setUserId(userId);
			userReports.setReportId(anotherId);
			userReports.setIsDelete(isdelete);
			userReports.setType(UserReports.NOINTEREST);
			userReports.setCreatedate(new Date());
			userReports.setUpdatedate(new Date());
			userReportsMapper.insert(userReports);
		}
		
	}
	//保存或更新屏蔽的人
	public void saveShield(Integer userId, Integer anotherId, Integer isdelete) {
		UserReports report= userReportsMapper.queryShield(userId,anotherId);
		if(report != null){
			userReportsMapper.updateShield(userId,anotherId,isdelete);
		}else{
			UserReports userReports = new UserReports();
			userReports.setUserId(userId);
			userReports.setReportId(anotherId);
			userReports.setIsDelete(isdelete);
			userReports.setType(UserReports.SHIELD);
			userReports.setCreatedate(new Date());
			userReports.setUpdatedate(new Date());
			userReportsMapper.insert(userReports);
		}
	}
	//查找不感兴趣的人
	public List<Integer> findNotInterest(Integer userId) {
		return userReportsMapper.findNotInterest(userId);
	}

}
