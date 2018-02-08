package com.xunxin.vo.qa;

import org.mongodb.framework.pojo.GeneralBean;
/**
 * 用户置顶，保存于mongodb数据库
 * @author gyf
 *
 */
public class CommentTop extends GeneralBean{

	/** 问题id  */
	private String questionId;
	
	/**
	 * 被回复的评论ID
	 */
	private String commentId ;
	
	/** 评论用户id  */
	private Integer userId;
	
	/**
	 * 0 已点赞 1 未点赞
	 */
	private Integer isTop = 0;

	public String getQuestionId() {
		return questionId;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}
	
	
}
