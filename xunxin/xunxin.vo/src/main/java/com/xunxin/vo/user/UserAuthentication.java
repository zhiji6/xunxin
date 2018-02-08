package com.xunxin.vo.user;

import java.util.Date;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月27日 -- 上午10:56:07
 * @Version 1.0
 * @Description	用户认证
 */
public class UserAuthentication {

	private int 						id;					//主键
	private String 						authInfo;			//认证信息
	private String 						authType;			//认证类型
	private Integer 					authState;			//认证状态       0:未认证|1:认证中|2:认证完成
	private String 						extendInfo;			//附加信息
	private String 						authRemark;			//备注
	private Date 						authTime;			//认证时间
	private int 						userId;				//用户
	private String                      nickName;           //昵称
	
	public UserAuthentication() {
		super();
	}

	public UserAuthentication(String authInfo, String authType, int authState, String extendInfo, String authRemark,
			Date authTime, int userId) {
		super();
		this.authInfo = authInfo;
		this.authType = authType;
		this.authState = authState;
		this.extendInfo = extendInfo;
		this.authRemark = authRemark;
		this.authTime = authTime;
		this.userId = userId;
	}
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAuthInfo() {
		return authInfo;
	}
	public void setAuthInfo(String authInfo) {
		this.authInfo = authInfo;
	}
	public String getAuthType() {
		return authType;
	}
	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public Integer getAuthState() {
		return authState;
	}

	public void setAuthState(Integer authState) {
		this.authState = authState;
	}

	public String getExtendInfo() {
		return extendInfo;
	}
	public void setExtendInfo(String extendInfo) {
		this.extendInfo = extendInfo;
	}
	public String getAuthRemark() {
		return authRemark;
	}
	public void setAuthRemark(String authRemark) {
		this.authRemark = authRemark;
	}
	public Date getAuthTime() {
		return authTime;
	}
	public void setAuthTime(Date authTime) {
		this.authTime = authTime;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	
	
	
	
	
	
	
	
}
