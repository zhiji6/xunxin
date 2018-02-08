package com.xunxin.controller.app.quartz;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.xunxin.config.LuceneEngineutil;

public class SearchJob implements Job{

	private static final Logger log = Logger.getLogger(SearchJob.class);
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("infoMsg:--- 搜索索引开始");
		LuceneEngineutil.creatIndex();
		log.info("infoMsg:--- 搜索索引结束");
	}

}