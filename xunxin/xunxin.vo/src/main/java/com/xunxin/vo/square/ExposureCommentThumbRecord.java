package com.xunxin.vo.square;

import org.mongodb.framework.pojo.GeneralBean;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年12月8日 -- 下午2:43:51
 * @Version 1.0
 * @Description	  曝光栏评论点赞记录	
 */
public class ExposureCommentThumbRecord extends GeneralBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String        					exposureId;			//曝光记录id
	private String        					commentId;			//曝光记录评论id
	private int        						thumbState;			//已赞/未赞
	private int        						userId;				//用户id
	
	public String getExposureId() {
		return exposureId;
	}
	public void setExposureId(String exposureId) {
		this.exposureId = exposureId;
	}
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	
	public int getThumbState() {
		return thumbState;
	}
	public void setThumbState(int thumbState) {
		this.thumbState = thumbState;
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
