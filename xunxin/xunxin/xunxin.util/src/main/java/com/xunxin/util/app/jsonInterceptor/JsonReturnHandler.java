package com.xunxin.util.app.jsonInterceptor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


public class JsonReturnHandler implements HandlerMethodReturnValueHandler {

    List<ResponseBodyAdvice<Object>> advices = new ArrayList<>();
    //判断是否有JSON或JSON注解
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        boolean hasJSONAnno = returnType.getMethodAnnotation(JSON.class) != null || returnType.getMethodAnnotation(JSONS.class) != null;
        return hasJSONAnno;
    }
//有的话就会执行这个方法
    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {
        mavContainer.setRequestHandled(true);
        for (int i=0;i<advices.size();i++){
            ResponseBodyAdvice<Object> ad = advices.get(i);
            if (ad.supports(returnType, null)) {
                returnValue = ad.beforeBodyWrite(returnValue, returnType, MYMediaType.APPLICATION_JSON_UTF8, null,
                        new ServletServerHttpRequest(webRequest.getNativeRequest(HttpServletRequest.class)),
                        new ServletServerHttpResponse(webRequest.getNativeResponse(HttpServletResponse.class)));
            }
        }

        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        Annotation[] annos = returnType.getMethodAnnotations();
        //调用
        CustomerJsonSerializer jsonSerializer = new CustomerJsonSerializer();
        //如果是JSONS就循环调用
        Arrays.asList(annos).forEach(a -> {
            if (a instanceof JSON) {
                JSON json = (JSON) a;
                jsonSerializer.filter(json);
            } else if (a instanceof JSONS) {
                JSONS jsons = (JSONS) a;
                Arrays.asList(jsons.value()).forEach(json -> {
                    jsonSerializer.filter(json);
                });
            }
        });

        response.setContentType(MYMediaType.APPLICATION_JSON_UTF8_VALUE);
        //转换成json返回
        String json = jsonSerializer.toJson(returnValue);
        response.getWriter().write(json);
    }


}