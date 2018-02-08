package com.xunxin.web.api.exception;

public class FaultError extends RuntimeException {
    private static final long serialVersionUID = -5870857652081713118L;

    public FaultError() {
        super();
    }

    public FaultError(String errorMessage) {
        super(errorMessage);
    }

    public FaultError(String message, Throwable cause) {
        super(message, cause);
    }
}
