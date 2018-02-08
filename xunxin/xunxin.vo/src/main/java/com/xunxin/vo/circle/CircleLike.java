package com.xunxin.vo.circle;

import java.util.Date;

import org.mongodb.framework.pojo.GeneralBean;

/**
 * 共情圈用户点赞，保存于mongodb数据库
 * @author gyf
 *
 */
public class CircleLike extends GeneralBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8089718435217014719L;

	/** 问题id  */
	private String empathyCircleId;
	
	/** 评论用户id  */
	private Integer userId;
	
	/**
	 * 0 已点赞 1 未点赞
	 */
	private Integer giveUP = 0;
	
   private Date updateTime;



public String getEmpathyCircleId() {
	return empathyCircleId;
}

public void setEmpathyCircleId(String empathyCircleId) {
	this.empathyCircleId = empathyCircleId;
}

public Integer getUserId() {
	return userId;
}

public void setUserId(Integer userId) {
	this.userId = userId;
}

public Integer getGiveUP() {
	return giveUP;
}

public void setGiveUP(Integer giveUP) {
	this.giveUP = giveUP;
}

public Date getUpdateTime() {
	return updateTime;
}

public void setUpdateTime(Date updateTime) {
	this.updateTime = updateTime;
}
   
	
}	