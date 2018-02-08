package com.xunxin.controller.app.quartz;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.xunxin.controller.app.comment.CommentController;
import com.xunxin.service.app.user.UserOnlineRecordService;
import com.xunxin.service.app.user.UserWelfareService;


public class UserLevelJob implements Job{


	@Autowired
	private UserOnlineRecordService userOnlineRecordService;
	@Autowired
	private UserWelfareService userWelfareService;
	private static final Logger log = Logger.getLogger(UserLevelJob.class);
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("infoMsg:--- 用户在线时长设置开始");
		userOnlineRecordService.queryAverageOnlineLength();
		log.info("infoMsg:--- 用户在线时长设置结束");
		userWelfareService.updateUserGrade();
	}

}