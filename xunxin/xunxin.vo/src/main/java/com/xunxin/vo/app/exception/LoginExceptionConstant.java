package com.xunxin.vo.app.exception;

/**
 * 
 * @author: Libra
 * Date: 2017年1月16日 下午4:32:45
 * Version: 1.0
 * @Description:  注册失败的错误提示
 */
public interface LoginExceptionConstant {
	
	public static final String PASSWORD_ERROR = "您输入的密码有误";
	public static final String UNKNOW_USER = "用户不存在";
	public static final String PHONE_NUMBER_IS_WRONG = "您输入的手机号有误";
    public static final String VERIFY_CODE_ERROR = "密码格式有误";
    public static final String LOGINNAME_IS_NULL = "请输入用户名";
    public static final String PASSWORD_IS_NULL = "请输入密码";
    public static final String ID_CODE_ERROR = "请输入正确的身份证号码";
    
    public static final String CONTANT_CUSTOMER_SERVICE = "修改次数已达上限,请联系客服";
    public static final String SIX_MONTH_LATER = "请六个月后再来修改";
	



}
