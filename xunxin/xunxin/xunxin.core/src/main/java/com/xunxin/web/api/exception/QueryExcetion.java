package com.xunxin.web.api.exception;

public class QueryExcetion extends FaultError{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2524847737463756661L;

	public QueryExcetion() {
        super();
    }

    public QueryExcetion(String errorMessage) {
        super(errorMessage);
    }

    public QueryExcetion(String message, Throwable cause) {
        super(message, cause);
    }
	
}
