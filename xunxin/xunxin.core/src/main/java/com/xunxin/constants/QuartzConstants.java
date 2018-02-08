package com.xunxin.constants;

public class QuartzConstants {

	   public static final String JOB_NAME_HERAT = "herat_job";  //心有灵犀定时任务调度任务调度
	   public static final String TRIGGER_NAME_HERAT = "herat_trigger";  //心有灵犀触发器
	   public static final String JOB_GROUP_NAME_HERT = "heartConsonance_GROUP";  
	   public static final String TRIGGER_GROUP_NAME_HERT = "XLheartConsonance_GROUP";
	   
	   public static final String JOB_NAME_ONLINE = "onLine_job";  //在线时间更新定时任务调度任务调度
	   public static final String TRIGGER_NAME_ONLINE = "onLine_trigger";  //心有灵犀触发器
	   public static final String JOB_GROUP_NAME_ONLINE = "onLine_group_name";  
	   public static final String TRIGGER_GROUP_NAMEONLINE = "onLine_group_trigger";
	   public static final String TRIGGER_GROUP_NAMEONLINE_CRON = "0 0 0 1 /1 ? ";
	   
	   public static final String JOB_NAME_IMRECORD = "imRecord_job";  //在线时间更新定时任务调度任务调度
	   public static final String TRIGGER_NAME_IMRECORD = "imRecord_trigger";  //心有灵犀触发器
	   public static final String JOB_GROUP_NAME_IMRECORD = "imRecord_group";  
	   public static final String TRIGGER_GROUP_NAME_IMRECORD = "imRecord_group_trigger";
	   public static final String TRIGGER_GROUP_IMRECORD_CRON = "0 0 0/2 * * ? ";
	   
	   public static final String JOB_NAME_ONLINESTSTUS = "onLineStatus_job";  //在线时间更新定时任务调度任务调度
	   public static final String TRIGGER_NAME_ONLINESTSTUS = "onLineStatus_trigger";  //心有灵犀触发器
	   public static final String JOB_GROUP_NAME_ONLINESTSTUS = "onLineStatus_group_name";  
	   public static final String TRIGGER_GROUP_ONLINESTSTUS = "onLineStatus_group_trigger";
	   public static final String TRIGGER_GROUP_ONLINESTSTUS_CRON = "0 3/5 * * * ? ";
	   
	   public static final String JOB_NAME_SEARCH = "search_job";  //搜索
	   public static final String TRIGGER_NAME_SEARCH = "search_trigger";  //心有灵犀触发器
	   public static final String JOB_GROUP_NAME_SEARCH = "search_group_name";  
	   public static final String TRIGGER_GROUP_SEARCH = "search_group_trigger";
	   public static final String TRIGGER_GROUP_SEARCH_CRON = "0 0/30 * * * ? ";
	   
	   public static final String JOB_NAME_PASS = "pass_job";  //擦肩而过
	   public static final String TRIGGER_NAME_PASS = "pass_trigger";  //心有灵犀触发器
	   public static final String JOB_GROUP_NAME_PASS = "pass_group_name";  
	   public static final String TRIGGER_GROUP_PASS = "pass_group_trigger";
	   public static final String TRIGGER_GROUP_PASS_CRON = "0/10 * * * * ? ";
}
