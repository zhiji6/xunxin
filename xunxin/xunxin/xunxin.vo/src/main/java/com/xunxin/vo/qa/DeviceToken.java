package com.xunxin.vo.qa;

import java.sql.Timestamp;
import java.util.Date;

public class DeviceToken {
	private int id;
	
	private int userId;//用户id
	
	private String type;//手机类型
	
	private String deviceToken;//推送唯一标示
	
	private String isDelete="0";  //0 存在 1 删除
	
	private Date createTime = new Date();//创建时间
	
	private Timestamp updateTime = new Timestamp(System.currentTimeMillis());//更新时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "DeviceToken [id=" + id + ", userId=" + userId + ", type=" + type + ", deviceToken=" + deviceToken
				+ ", isDelete=" + isDelete + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
	
	
	

}
