package com.xunxin.vo.qa;


import java.util.Date;

import org.mongodb.framework.pojo.GeneralBean;
/**
 * 用户评论点赞，保存于mongodb数据库
 * @author gyf
 *
 */
public class GiveUpVo extends GeneralBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8089718435217014719L;

	/** 问题id  */
	private String questionId;
	
	/**
	 * 被回复的评论ID
	 */
	private String commentId ;
	
	/** 评论用户id  */
	private int userId;
	
	/**
	 * 0 已点赞 1 未点赞
	 */
	private Integer giveUP = 0;
   private Date updateTime;
	public String getQuestionId() {
		return questionId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Integer getGiveUP() {
		return giveUP;
	}

	public void setGiveUP(Integer giveUP) {
		this.giveUP = giveUP;
	}
	
	
}
