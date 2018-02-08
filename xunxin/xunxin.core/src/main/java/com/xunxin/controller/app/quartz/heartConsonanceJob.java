package com.xunxin.controller.app.quartz;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.xunxin.service.app.IMNewsService;


public class heartConsonanceJob implements Job{

	
	@Autowired
	private IMNewsService iMNewsService;
	

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		iMNewsService.evenTheHeart();
		
	}

}
