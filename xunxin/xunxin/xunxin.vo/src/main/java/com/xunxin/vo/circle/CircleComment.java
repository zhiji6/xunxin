package com.xunxin.vo.circle;
import java.util.Date;
import org.mongodb.framework.pojo.GeneralBean;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * 共情圈评论，保存于mongodb数据库
 * @author gyf
 *
 */
public class CircleComment extends GeneralBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1291901592288677646L;



	/** 问题id  */
	private String empathyCircleId;
	
	/** 评论内容  */
	private String content;

	/** 评论用户id  */
	private Integer userId;
	
	/**
	 * 被回复人userId
	 */
	private Integer replyUserId;
	
	
	/** 跟新时间  */
	@JsonIgnore
	private Date updateTime;
	
	/** 排序  */
	@JsonIgnore 
	private String sort;
	
	/** 创建时间  */
	@JsonIgnore
	private String createDate;
	
	/** 用户昵称  */
	private String nickName;
	
	/** 被回复者昵称  */
	private String reployName;
	
	private String $in;
	
	public String get$in() {
		return $in;
	}

	public void set$in(String $in) {
		this.$in = $in;
	}

	public String getEmpathyCircleId() {
		return empathyCircleId;
	}

	public void setEmpathyCircleId(String empathyCircleId) {
		this.empathyCircleId = empathyCircleId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getReplyUserId() {
		return replyUserId;
	}

	public void setReplyUserId(Integer replyUserId) {
		this.replyUserId = replyUserId;
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
	
	

	
	
}
