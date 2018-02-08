package com.xunxin.vo.app.model;

/**
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年10月11日 -- 下午4:39:16
 * @Version 1.0
 * @Description	APP somebody new registered.
 */
public class RegisterModel {

/**##################  #################### */	
	
	private String phone;   	//用户手机号
	private String password; 	//密码
	private int verifyCode;  //验证码
	
/**##################  #################### */	
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(int verifyCode) {
		this.verifyCode = verifyCode;
	}
		
}
