package com.xunxin.service.app;

import java.io.File;
import java.util.List;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xunxin.service.app.IMRecordService;
import com.xunxin.service.app.UserFriendsService;
import com.xunxin.util.app.chat.ChatMessagesTest;
import com.xunxin.util.app.chat.uploadUtils;

@Repository
public class DelFileTask extends TimerTask{

	private static boolean isRunning = false;
	
	private ServletContext context = null;
	
	@Autowired 
	private IMRecordService iMRecordService;
	
	private static final Logger logger = Logger.getLogger(DelFileTask.class);
	public DelFileTask(){
		super();
	}
	public DelFileTask(ServletContext context){
		this.context = context;
	}
	
	@Override
	public void run() {
		logger.info("保存消息定时任务开启");
		ChatMessagesTest chatUrl = new ChatMessagesTest();
		String photoUrl = chatUrl.returnUrl();
        String[] split = photoUrl.split("\\?");
        String fileName = split[0].substring(split[0].lastIndexOf("/"));   
        String filePath = "E:\\develop\\chat";    
        File file = uploadUtils.saveUrlAs(photoUrl, filePath,"GET",fileName); 
        filePath = filePath+file;
		iMRecordService.saveImRecord(filePath);
		logger.info("保存消息定时任务完成");
	}
	

}
