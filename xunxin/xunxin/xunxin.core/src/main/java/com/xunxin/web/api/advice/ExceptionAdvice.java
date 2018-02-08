package com.xunxin.web.api.advice;

import java.util.List;
import java.util.Set;

import javax.xml.bind.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.xunxin.web.api.bean.Response;


/**
 * <p>浏览器异常的封装类</p>	
 * <p>User: Zhugong Team
 * <p>Date: 2016年12月20日 下午8:11:14
 * <p>Version: 1.0
 */
public class ExceptionAdvice {

	private static Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Response handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.error("缺少请求参数", e);
        e.printStackTrace();
        return new Response().failure("required_parameter_is_not_present");
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error("参数解析失败", e);
        e.printStackTrace();
        return new Response().failure("could_not_read_json");
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error("参数验证失败", e);
        e.printStackTrace();
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        if (error == null) {
            return new Response().failure(result.getObjectName() + "验证不通过");
        }
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        return new Response().failure(message);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public Response handleBindException(BindException e) {
        logger.error("参数绑定失败", e);
        e.printStackTrace();
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        if (error != null) {
            String field = error.getField();
            String code = error.getDefaultMessage();
            String message = String.format("%s:%s", field, code);
            return new Response().failure(message);
        } else {
            List<ObjectError> list = e.getAllErrors();
            if (list.size() < 1) {
                return new Response().failure("验证失败");
            }
            return new Response().failure(list.get(0).getDefaultMessage());
        }
    }

    /**
     * 400 - Bad Request
     */
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(ConstraintViolationException.class)
//    public Response handleServiceException(ConstraintViolationException e) {
//        logger.error("参数验证失败", e);
//        e.printStackTrace();
//        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
//        ConstraintViolation<?> violation = violations.iterator().next();
//        String message = violation.getMessage();
//        return new Response().failure("parameter:" + message);
//    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public Response handleValidationException(ValidationException e) {
        logger.error("参数验证失败", e);
        e.printStackTrace();
        return new Response().failure("validation_exception");
    }

    /**
     * 401 - Unauthorized
     */
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler(TokenException.class)
//    public Response handleTokenException(TokenException e) {
//        logger.info("令牌验证失败", e);
//        e.printStackTrace();
//        return new Response().failure("登录过期,系统将自动重新登录");
//    }

    /**
     * 404 - NOT_FOUND
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public Response NoFoundException(NoHandlerFoundException e) {
        logger.error("请求不存在", e);
        e.printStackTrace();
        return new Response().failure("请求不存在");
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error("不支持当前请求方法", e);
        e.printStackTrace();
        return new Response().failure("request_method_not_supported");
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Response handleHttpMediaTypeNotSupportedException(Exception e) {
        logger.error("不支持当前媒体类型", e);
        e.printStackTrace();
        return new Response().failure("content_type_not_supported");
    }

    /**
     * 500 - Internal Server Error
     */
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(ServiceException.class)
//    public Response handleServiceException(ServiceException e) {
//        logger.error("服务运行异常", e);
//        e.printStackTrace();
//        return new Response().failure(e.getMessage());
//    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        logger.error("通用异常", e);
        e.printStackTrace();
        return new Response().failure(e.getMessage());
    }
	
}
