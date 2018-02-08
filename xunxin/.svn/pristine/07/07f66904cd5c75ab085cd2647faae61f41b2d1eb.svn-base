package com.xunxin.vo.im;

import java.util.Date;

import org.mongodb.framework.pojo.GeneralBean;

/**
 * 统计消息是否已读，保存于mongodb数据库
 * @author gyf
 *
 */
public class MessageRead extends GeneralBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8089718435217014719L;
	
	/** 评论用户id  */
	private Integer userId;
	
	/**
	 * 0未读 1已读
	 */
	private Integer isRead = 0;
	/**
	 * 消息类型 1-心有灵犀 2 官方消息 3-互动消息
	 */
	private Integer tag;
	/**
	 * 跟新时间
	 */
   private Date updateTime;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	public Integer getTag() {
		return tag;
	}
	public void setTag(Integer tag) {
		this.tag = tag;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
