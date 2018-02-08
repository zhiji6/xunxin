package com.xunxin.controller.app.quartz;

import java.io.File;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.xunxin.service.app.IMRecordService;
import com.xunxin.util.app.chat.ChatMessagesTest;
import com.xunxin.util.app.chat.uploadUtils;

public class IMRecordJob implements Job{


	@Autowired
	private IMRecordService iMRecordService;
	private static final Logger log = Logger.getLogger(IMRecordJob.class);
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("infoMsg:--- 保存消息定时任务开始");
		ChatMessagesTest chatUrl = new ChatMessagesTest();
		String photoUrl = chatUrl.returnUrl();
		log.info("infoMsg:--- 下载消息地址"+photoUrl);
		if(photoUrl != null && photoUrl.length() > 0){
	        String[] split = photoUrl.split("\\?");
	        String fileName = split[0].substring(split[0].lastIndexOf("/"));
	        log.info("infoMsg:--- fileName"+fileName);
	        String filePath = "/home/xunxin/chatdata"; 
	        log.info("infoMsg:--- filePath"+filePath);
	        File file = uploadUtils.saveUrlAs(photoUrl, filePath,"GET",fileName); 
	        log.info("infoMsg:--- filePath"+file.toString());
			iMRecordService.saveImRecord(file.toString()+fileName);
		}
		log.info("保存消息定时任务完成");
	}

}