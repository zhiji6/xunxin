package com.xunxin.vo.square;

import org.mongodb.framework.pojo.GeneralBean;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年12月8日 -- 下午3:03:27
 * @Version 1.0
 * @Description		曝光栏评论记录  
 */
public class ExposureCommentRecord extends GeneralBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String         					exposureId;			//曝光记录ID
	private String         					content;			//曝光栏评论记录ID
	private int 							thumbCount; 		//点赞数
	private int         					userId;				//评论人
	
	public String getExposureId() {
		return exposureId;
	}
	public void setExposureId(String exposureId) {
		this.exposureId = exposureId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getThumbCount() {
		return thumbCount;
	}
	public void setThumbCount(int thumbCount) {
		this.thumbCount = thumbCount;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
