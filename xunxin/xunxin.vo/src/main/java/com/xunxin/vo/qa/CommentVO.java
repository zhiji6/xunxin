package com.xunxin.vo.qa;

import java.util.Date;

import org.mongodb.framework.pojo.GeneralBean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * 用户评论，保存于mongodb数据库
 * @author gyf
 *
 */
public class CommentVO extends GeneralBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1291901592288677646L;



	/** 问题id  */
	private String questionId;
	
	/** 评论内容  */
	private String content;

	/** 评论用户id  */
	private Integer userId;
	
	/**
	 * 评论状态（N 没置顶，Y置顶）
	 */
	private String status = "N";
	
	/**
	 * 被回复人userId
	 */
	private Integer replyUserId;
	
	/**
	 * 被回复的评论ID
	 */
	private String commentId ;
	
	/** 点赞  */
	private Integer identificationNum = 0;

	/** 踩数  */
	private Integer stepNum;
	
	/** 跟新时间  */
	private Date updateTime;
	
	/** 排序  */
	private String sort;
	
	/** 创建时间  */
	private String createDate;
	
	/** 用户昵称  */
	private String nickName;
	
	/** 被回复者昵称  */
	private String reployName;
	
	/** 是否赞过  1没点赞 0已点赞*/
	private Integer giveUp = 1;
	
	private CommentVO commentVO;
	//回复数量
	private Integer replyNum;
	//置顶数
	private Integer topNum;
	
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private Integer $type;
	/** 问题名字  */
	private String questionName;
	private String $ne;
	private String $gte;
	
	public String get$gte() {
		return $gte;
	}

	public void set$gte(String $gte) {
		this.$gte = $gte;
	}

	public String get$ne() {
		return $ne;
	}

	public void set$ne(String $ne) {
		this.$ne = $ne;
	}

	public Integer get$type() {
		return $type;
	}

	public void set$type(Integer $type) {
		this.$type = $type;
	}

	public Integer getTopNum() {
		return topNum;
	}

	public void setTopNum(Integer topNum) {
		this.topNum = topNum;
	}

	private String $in;
	
	public String get$in() {
		return $in;
	}

	public void set$in(String $in) {
		this.$in = $in;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	private String gender;
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}



	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}


	public Integer getReplyUserId() {
		return replyUserId;
	}

	public void setReplyUserId(Integer replyUserId) {
		this.replyUserId = replyUserId;
	}

	public Integer getIdentificationNum() {
		return identificationNum;
	}

	public void setIdentificationNum(Integer identificationNum) {
		this.identificationNum = identificationNum;
	}

	public Integer getStepNum() {
		return stepNum;
	}

	public void setStepNum(Integer stepNum) {
		this.stepNum = stepNum;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

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

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getReployName() {
		return reployName;
	}

	public void setReployName(String reployName) {
		this.reployName = reployName;
	}

	public Integer getGiveUp() {
		return giveUp;
	}

	public void setGiveUp(Integer giveUp) {
		this.giveUp = giveUp;
	}

	public CommentVO getCommentVO() {
		return commentVO;
	}

	public void setCommentVO(CommentVO commentVO) {
		this.commentVO = commentVO;
	}

	public Integer getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(Integer replyNum) {
		this.replyNum = replyNum;
	}
	
}
