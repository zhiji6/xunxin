package com.xunxin.vo.user;

import java.util.Date;

/**
 * 
 * Copyright © 2017 Xunxin Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2017年12月15日 -- 下午2:31:47
 * @Version 1.0
 * @Description		用户信息修改记录
 */
public class UserChangeInformationRecord {

	private int 						id;
	private String 						fieldName;					//修改的字段名
	private String 						fieldValue;					//修改后的值
	private String 						changeRemark;				//备注
	private Date						changeTime;					//修改时间
	private int 						userId;						//用户
	
	
	public UserChangeInformationRecord() {
		super();
	}
	public UserChangeInformationRecord(String fieldName, String fieldValue, String changeRemark,
			Date changeTime, int userId) {
		super();
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.changeRemark = changeRemark;
		this.changeTime = changeTime;
		this.userId = userId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public String getChangeRemark() {
		return changeRemark;
	}
	public void setChangeRemark(String changeRemark) {
		this.changeRemark = changeRemark;
	}
	public Date getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	
}
