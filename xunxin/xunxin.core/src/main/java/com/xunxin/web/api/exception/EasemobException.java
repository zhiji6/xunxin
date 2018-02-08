package com.xunxin.web.api.exception;

/**
 * 
 * Copyright © 2017 noseparte(Libra) © Like the wind, like rain
 * @Author Noseparte
 * @Compile 2017年11月1日 -- 上午10:36:24
 * @Version 1.0
 * @Description	环信服务器异常
 */
public class EasemobException extends FaultError {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5402145851901373133L;

	public EasemobException(){
		super();
	}
	
	public EasemobException(String errorMessage){
		super(errorMessage);
	}
	
	public EasemobException(String message, Throwable cause){
		super(message,cause);
	}
	
}
