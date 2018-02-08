package com.xunxin.vo.qa;

import java.util.Date;

import org.mongodb.framework.pojo.GeneralBean;

/**
 * 题库审核记录，系统动态监控审核流程，每10分钟监控一次
 * @author rambo
 *
 */
public class AuditRecordVO extends GeneralBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3321637542951932127L;
	
	/** 记录id */
	private String id;

	/** 问题id  */
	private String questionID;

	/** 审核状态（0：审核通过，1：审核失败） */
	private short status;

	/** 审核不通过原因 */
	private String failReson;

	/** 审核时间  */
	private Date auditTime;

	/** 审核人角色（0：志愿者，1：后台编辑）  */
	private short role;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQuestionID() {
		return questionID;
	}
	public void setQuestionID(String questionID) {
		this.questionID = questionID;
	}
	public short getStatus() {
		return status;
	}
	public void setStatus(short status) {
		this.status = status;
	}
	public String getFailReson() {
		return failReson;
	}
	public void setFailReson(String failReson) {
		this.failReson = failReson;
	}

	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public short getRole() {
		return role;
	}
	public void setRole(short role) {
		this.role = role;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

}
