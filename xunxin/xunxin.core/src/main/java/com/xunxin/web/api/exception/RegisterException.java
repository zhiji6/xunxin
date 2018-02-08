package com.xunxin.web.api.exception;

/**
 * 
 * @author: Libra
 * Date: 2017年1月16日 下午4:15:12
 * Version: 1.0
 * @Description: 注册异常
 */
public class RegisterException extends FaultError {

	/**
	 * 
	 */
	private static final long serialVersionUID = 284337707721691378L;

	public RegisterException(){
		super();
	}
	
	public RegisterException(String errorMessage){
		super(errorMessage);
	}
	
	public RegisterException(String message, Throwable cause){
		super(message,cause);
	}
	
	
	
	
}
