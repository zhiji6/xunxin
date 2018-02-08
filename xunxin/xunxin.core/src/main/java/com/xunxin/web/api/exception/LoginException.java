package com.xunxin.web.api.exception;

/**
 * 登录异常
 */
public class LoginException extends FaultError {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2524847737463756661L;

	public LoginException() {
        super();
    }

    public LoginException(String errorMessage) {
        super(errorMessage);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
