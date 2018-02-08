                                 package com.xunxin.controller.app.quartz;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xunxin.constants.QuartzConstants;
import com.xunxin.service.app.IMNewsService;
import com.xunxin.service.app.quartz.QuartzManagerService;
import com.xunxin.util.SpringContextUtil;

@Configuration
@ComponentScan
public class ContextListenerHert implements ServletContextListener {
	
    public ContextListenerHert() {    
    }    

	public void contextDestroyed(ServletContextEvent arg0) {
		
	}
	public void contextInitialized(ServletContextEvent arg0) {
		//心有灵犀定时任务
//		IMNewsService configService = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext()).getBean(IMNewsService.class);
//	        configService.heartConsonanceInitJob();
	    //在线时间更新
		onLineTimeUpdate();
		
		//消息记录保存
		imRecord();                                                                                   
		//Online在线状态更新
		onLineStatus();
		                                                       
		//搜索索引更新
		searchIndex();
		
		//擦肩而过
		passBy();
		
	    
	}
	//擦肩而过
	private void passBy() {
		try {
			 SchedulerFactoryBean MyScheduler = SpringContextUtil.getBean(SchedulerFactoryBean.class);
			QuartzManagerService QuartzManager = (QuartzManagerService) SpringContextUtil.getBean("quartzManagerService");
		    JobDetail jobDetail= JobBuilder.newJob(BrushAgainstJob.class).withIdentity(QuartzConstants.JOB_NAME_PASS, QuartzConstants.JOB_GROUP_NAME_PASS).build();
	        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
	        triggerBuilder.withIdentity(QuartzConstants.JOB_NAME_PASS,  QuartzConstants.JOB_GROUP_NAME_PASS);
	        triggerBuilder.startNow();
	        triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(QuartzConstants.TRIGGER_GROUP_PASS_CRON));
	        CronTrigger trigger = (CronTrigger) triggerBuilder.build();
      	Scheduler sched = MyScheduler.getScheduler(); 
      	sched.scheduleJob(jobDetail, trigger);
      	QuartzManager.modifyJobTime(QuartzConstants.JOB_NAME_PASS, QuartzConstants.JOB_GROUP_NAME_PASS, QuartzConstants.TRIGGER_NAME_PASS, QuartzConstants.TRIGGER_GROUP_PASS,QuartzConstants.TRIGGER_GROUP_PASS_CRON,MyScheduler); 
		} catch (SchedulerException e) {
			e.printStackTrace();
		} 
		
	}

	//搜索索引
	private void searchIndex() {
		try {
			 SchedulerFactoryBean MyScheduler = SpringContextUtil.getBean(SchedulerFactoryBean.class);
			QuartzManagerService QuartzManager = (QuartzManagerService) SpringContextUtil.getBean("quartzManagerService");
		    JobDetail jobDetail= JobBuilder.newJob(SearchJob.class).withIdentity(QuartzConstants.JOB_NAME_SEARCH, QuartzConstants.JOB_GROUP_NAME_SEARCH).build();
	        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
	        triggerBuilder.withIdentity(QuartzConstants.JOB_NAME_SEARCH,  QuartzConstants.JOB_GROUP_NAME_SEARCH);
	        triggerBuilder.startNow();
	        triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(QuartzConstants.TRIGGER_GROUP_SEARCH_CRON));
	        CronTrigger trigger = (CronTrigger) triggerBuilder.build();
       	Scheduler sched = MyScheduler.getScheduler(); 
       	sched.scheduleJob(jobDetail, trigger);
       	QuartzManager.modifyJobTime(QuartzConstants.JOB_NAME_SEARCH, QuartzConstants.JOB_GROUP_NAME_SEARCH, QuartzConstants.TRIGGER_NAME_SEARCH, QuartzConstants.TRIGGER_GROUP_SEARCH,QuartzConstants.TRIGGER_GROUP_SEARCH_CRON,MyScheduler); 
		} catch (SchedulerException e) {
			e.printStackTrace();
		} 
		
	}

	//消息记录保存
	private void imRecord() {
		 try {
			 SchedulerFactoryBean MyScheduler = SpringContextUtil.getBean(SchedulerFactoryBean.class);
			QuartzManagerService QuartzManager = (QuartzManagerService) SpringContextUtil.getBean("quartzManagerService");
		    JobDetail jobDetail= JobBuilder.newJob(IMRecordJob.class).withIdentity(QuartzConstants.JOB_NAME_IMRECORD, QuartzConstants.JOB_GROUP_NAME_IMRECORD).build();
	        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
	        triggerBuilder.withIdentity(QuartzConstants.JOB_NAME_IMRECORD,  QuartzConstants.JOB_GROUP_NAME_IMRECORD);
	        triggerBuilder.startNow();
	        triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(QuartzConstants.TRIGGER_GROUP_IMRECORD_CRON));
	        CronTrigger trigger = (CronTrigger) triggerBuilder.build();
        	Scheduler sched = MyScheduler.getScheduler(); 
        	sched.scheduleJob(jobDetail, trigger);
        	QuartzManager.modifyJobTime(QuartzConstants.JOB_NAME_IMRECORD, QuartzConstants.JOB_GROUP_NAME_IMRECORD, QuartzConstants.TRIGGER_NAME_IMRECORD, QuartzConstants.TRIGGER_GROUP_NAME_IMRECORD,QuartzConstants.TRIGGER_GROUP_IMRECORD_CRON,MyScheduler); 
		} catch (SchedulerException e) {
			e.printStackTrace();
		}  
		
	}

	//在线时间更新
	private void onLineTimeUpdate() {
		 try {
			 SchedulerFactoryBean MyScheduler = SpringContextUtil.getBean(SchedulerFactoryBean.class);
			QuartzManagerService QuartzManager = (QuartzManagerService) SpringContextUtil.getBean("quartzManagerService");
		    JobDetail jobDetail= JobBuilder.newJob(UserLevelJob.class).withIdentity(QuartzConstants.JOB_NAME_ONLINE, QuartzConstants.JOB_GROUP_NAME_ONLINE).build();
	        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
	        triggerBuilder.withIdentity(QuartzConstants.JOB_NAME_ONLINE,  QuartzConstants.JOB_GROUP_NAME_ONLINE);
	        triggerBuilder.startNow();
	        triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(QuartzConstants.TRIGGER_GROUP_NAMEONLINE_CRON));
	        CronTrigger trigger = (CronTrigger) triggerBuilder.build();
        	Scheduler sched = MyScheduler.getScheduler(); 
        	sched.scheduleJob(jobDetail, trigger);
        	QuartzManager.modifyJobTime(QuartzConstants.JOB_NAME_ONLINE, QuartzConstants.JOB_GROUP_NAME_ONLINE, QuartzConstants.TRIGGER_NAME_ONLINE, QuartzConstants.TRIGGER_GROUP_NAMEONLINE,QuartzConstants.TRIGGER_GROUP_NAMEONLINE_CRON,MyScheduler); 
		} catch (SchedulerException e) {
			e.printStackTrace();
		}  
	     
	}
	
	//Online在线状态更新
	private void onLineStatus() {
		 try {
			 SchedulerFactoryBean MyScheduler = SpringContextUtil.getBean(SchedulerFactoryBean.class);
			QuartzManagerService QuartzManager = (QuartzManagerService) SpringContextUtil.getBean("quartzManagerService");
		    JobDetail jobDetail= JobBuilder.newJob(UserOnlineJob.class).withIdentity(QuartzConstants.JOB_NAME_ONLINESTSTUS, QuartzConstants.JOB_GROUP_NAME_ONLINESTSTUS).build();
	        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
	        triggerBuilder.withIdentity(QuartzConstants.JOB_NAME_ONLINESTSTUS,  QuartzConstants.JOB_GROUP_NAME_ONLINESTSTUS);
	        triggerBuilder.startNow();
	        triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(QuartzConstants.TRIGGER_GROUP_ONLINESTSTUS_CRON));
	        CronTrigger trigger = (CronTrigger) triggerBuilder.build();
       	Scheduler sched = MyScheduler.getScheduler(); 
       	sched.scheduleJob(jobDetail, trigger);
       	QuartzManager.modifyJobTime(QuartzConstants.JOB_NAME_ONLINESTSTUS, QuartzConstants.JOB_GROUP_NAME_ONLINESTSTUS, QuartzConstants.TRIGGER_NAME_ONLINESTSTUS, QuartzConstants.TRIGGER_GROUP_ONLINESTSTUS,QuartzConstants.TRIGGER_GROUP_ONLINESTSTUS_CRON,MyScheduler); 
		} catch (SchedulerException e) {
			e.printStackTrace();
		}  
	}
}
