package com.xunxin.vo.user;

import org.mongodb.framework.pojo.GeneralBean;

/**
 * 
 * Copyright © 2017 Xunxin Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2017年12月17日 -- 下午4:48:55
 * @Version 1.0
 * @Description		用户动态记录
 */
public class UserDynamicRecordVO extends GeneralBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int 							thumbCount; 		//点赞数
	private String							dynamicUrls;		//图片地址
	private String 							content;			//动态内容
	private String 							dynamicSource;		//动态来源
	private int 							userId;				//用户
	
	public int getThumbCount() {
		return thumbCount;
	}
	public void setThumbCount(int thumbCount) {
		this.thumbCount = thumbCount;
	}
	public String getDynamicUrls() {
		return dynamicUrls;
	}
	public void setDynamicUrls(String dynamicUrls) {
		this.dynamicUrls = dynamicUrls;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getDynamicSource() {
		return dynamicSource;
	}
	public void setDynamicSource(String dynamicSource) {
		this.dynamicSource = dynamicSource;
	}
	
	
	
	

}
